# Stage 1: Cache dependencies
FROM gradle:latest AS cache
WORKDIR /home/gradle/app

# Copy file konfigurasi yang jarang berubah dulu
COPY build.gradle settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Download dependencies dan cache
RUN gradle build -x test --parallel --no-daemon

# Stage 2: Build aplikasi
FROM gradle:latest AS build
WORKDIR /home/gradle/app

# Copy cache gradle dari stage sebelumnya
COPY --from=cache /home/gradle/.gradle /home/gradle/.gradle

# Copy seluruh source code
COPY . .

# Build aplikasi
RUN gradle build -x test --parallel --no-daemon

# Stage 3: Final image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy hasil build dari stage build
COPY --from=build /home/gradle/app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
