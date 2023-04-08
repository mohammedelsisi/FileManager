docker-compose up -d --build --force-recreate
timeout /t 10
start http://localhost:8080/api/swagger-ui/index.html