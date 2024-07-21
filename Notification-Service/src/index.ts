import app from "./app";
require("dotenv").config();
import {registerWithEureka } from "./discover/eureka-helper";

const port = 8088;

app.listen(port, () => {
  console.log(`server is listening on ${port}`);
});

registerWithEureka('notification-service', port);