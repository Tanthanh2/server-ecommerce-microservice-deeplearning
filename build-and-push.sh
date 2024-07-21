#!/bin/bash

# Define the services and their directories
declare -A services
services=(
  ["discovery-server"]="Discovery-Server"
  ["api-gateway"]="Gateway"
  ["user-service"]="User-Service"
  ["product-service"]="Product-Service"
  ["promotion-service"]="Promotion-Service"
  ["cart-service"]="Cart-Service"
  ["payment-service"]="Payment-Service"
  ["order-service"]="Order-Service"
  ["shipping-service"]="Shipping-Service"

)

# Docker Hub username
username="nguyentanthanh0709"

# Loop through each service
for service in "${!services[@]}"
do
  # Navigate to the service directory
  cd "${services[$service]}"
  
  # Build the Docker image
  echo "Building $service..."
  docker build -t $username/$service:latest .

  # Push the Docker image
  echo "Pushing $service..."
  docker push $username/$service:latest

  # Navigate back to the root directory
  cd ..
done
