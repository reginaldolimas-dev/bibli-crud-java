FROM eclipse-temurin:21-jre

WORKDIR /app

# o JAR será montado pelo docker-compose
EXPOSE 9090

ENTRYPOINT ["java","-jar","/app/app.jar"]
