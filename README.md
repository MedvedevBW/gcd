## Deploying
Before deploying application, you need already running RabbitMq server.

1. Download project from gitHub
2. Configure application properties in [config file](src/main/resources/application.yml)
3. Execute command
```bash
    ./mvnw install dockerfile:build
```
4. Run generated image 
```bash
   docker run -p 8080:8080 {image name}
```
Note: Use --net="host" option if you use "localhost" in yours application properties