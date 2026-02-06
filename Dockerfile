# ===== BUILD (tem Maven + JDK 21) =====
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package

# ===== RUNTIME (só Java pra rodar) =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 9090
ENTRYPOINT ["java","-jar","/app/app.jar"]
