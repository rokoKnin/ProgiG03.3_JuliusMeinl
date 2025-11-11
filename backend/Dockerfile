
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app


COPY pom.xml .
COPY src ./src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:23-jdk

WORKDIR /app


COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar


ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV BACKEND_URL=${BACKEND_URL}
ENV FRONTEND_URL=${FRONTEND_URL}
ENV PASSWORD=${PASSWORD}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
