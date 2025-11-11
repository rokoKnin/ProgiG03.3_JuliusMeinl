FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Kopiraj Maven wrapper i .mvn folder
COPY mvnw .
COPY .mvn .mvn

# Kopiraj projektne fajlove
COPY pom.xml .
COPY src ./src

# Daj mvnw prava izvršavanja
RUN chmod +x mvnw

# Build projekta
RUN ./mvnw clean package -DskipTests

# Drugi stage za runtime
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Kopiraj samo JAR iz build stage-a
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Postavi env varijable
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV BACKEND_URL=${BACKEND_URL}
ENV FRONTEND_URL=${FRONTEND_URL}
ENV PASSWORD=${PASSWORD}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
