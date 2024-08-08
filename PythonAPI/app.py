from flask import Flask, request, jsonify
from flaskext.mysql import MySQL
from py_eureka_client import eureka_client
from flask_cors import CORS
import pickle
import requests
from io import BytesIO
from tensorflow.keras.preprocessing import image
from tensorflow.keras.applications.vgg16 import VGG16, preprocess_input
from tensorflow.keras.models import Model
from PIL import Image
import numpy as np
import math
import matplotlib.pyplot as plt
from tensorflow.keras.preprocessing.image import img_to_array
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
    

mysql = MySQL()
app = Flask(__name__)
CORS(app)

app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = 'Abc@123456789'
app.config['MYSQL_DATABASE_DB'] = 'productdb'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'


rest_port = 8086
eureka_client.init(eureka_server="http://localhost:8761/eureka",
                   app_name="data-aggregation-service",
                   instance_port=rest_port)

mysql.init_app(app)


# Hàm tạo model
def get_extract_model():
    vgg16_model = VGG16(weights="imagenet")
    extract_model = Model(inputs=vgg16_model.inputs, outputs=vgg16_model.get_layer("fc1").output)
    return extract_model

# Hàm tiền xử lý, chuyển đổi hình ảnh thành tensor
def image_preprocess(img):
    img = img.resize((224, 224))
    img = img.convert("RGB")
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)
    return x

# Hàm trích xuất đặc trưng từ ảnh
def extract_vector(model, image_path):
    try:
        response = requests.get(image_path)
        img = Image.open(BytesIO(response.content))
        img_tensor = image_preprocess(img)

        # Trích đặc trưng
        vector = model.predict(img_tensor)[0]
        # Chuẩn hóa vector bằng cách chia cho L2 norm
        vector = vector / np.linalg.norm(vector)
        return vector
    except Exception as e:
        print(f"Không thể xử lý ảnh {image_path}: {e}")
        return None

@app.route('/api/v1/aggreations/')
def hello_world():
    return 'Hello, World!'

@app.route('/api/v1/aggreations/products', methods=['POST'])
def get_products():

    # Lấy dữ liệu từ request body
    data = request.get_json()
    search_image = data.get('image')

    if not search_image:
        return jsonify({"error": "No image link provided"}), 400
    
    conn = mysql.connect()
    cursor = conn.cursor()
    
    query = "SELECT id, image FROM product"
    cursor.execute(query)
    
    data = cursor.fetchall()
    
    image_data = []
    for row in data:
        image_data.append({'id': row[0], 'image': row[1]})
    
    cursor.close()
    conn.close()

    nearest_images = handLe(model, image_data, search_image)


    return jsonify(nearest_images)


def handLe(model,image_data, search_image):

    # Trích xuất đặc trưng từ tất cả ảnh
    vectors = []
    valid_ids = []
    for item  in image_data:
        vector = extract_vector(model, item['image'])
        if vector is not None:
            vectors.append(vector)
            valid_ids.append(item['id'])

    # Chuyển đổi danh sách vectors thành numpy array
    vectors = np.array(vectors)


    # Trích đặc trưng ảnh search
    search_vector = extract_vector(model, search_image)
    # Tính khoảng cách từ search_vector đến tất cả các vector
    distances = np.linalg.norm(vectors - search_vector, axis=1)
    # Sắp xếp và lấy ra K vector có khoảng cách ngắn nhất
    K = 5
    ids_sorted = np.argsort(distances)[:K]

    # # Tạo output
    # nearest_images = [{"id": valid_ids[id], "link_url_picture": image_data[id]['image']} for id in ids_sorted]
    concatenated_ids = "_".join(str(valid_ids[id]) for id in ids_sorted)

    return concatenated_ids



if __name__ == '__main__':
    model = get_extract_model()
    app.run(debug=True, port=rest_port)