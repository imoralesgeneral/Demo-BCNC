# Use an official OpenJDK runtime as a parent image
FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
