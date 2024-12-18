# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
# Update the JAR name to match the correct name and location
COPY target/ebankify-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (update if your app uses a different port)
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
