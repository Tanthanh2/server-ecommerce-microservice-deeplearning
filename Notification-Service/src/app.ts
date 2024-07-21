import express from "express";
import routes from "./routes";
import * as middlewares from "./middlewares";
const app = express();



app.use("/api/v1/notifications", routes);
app.use(middlewares.notFound);
app.use(middlewares.errorHandler);

export default app;