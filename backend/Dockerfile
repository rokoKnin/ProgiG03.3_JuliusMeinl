FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Kopiraj Maven wrapper i .mvn folder
# Koristi se 'backend/' putanja jer je Docker kontekst u korijenu repoa
COPY backend/mvnw .
COPY backend/.mvn .mvn

# Kopiraj projektne fajlove
COPY backend/pom.xml .
COPY backend/src ./src

# Daj mvnw prava izvršavanja
RUN chmod +x mvnw

# Build projekta
RUN ./mvnw clean package -DskipTests

# Drugi stage za runtime
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Kopiraj samo JAR iz build stage-a
# Ovdje putanja ostaje ista jer se kopira IZ containera 'build', a ne iz diska
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