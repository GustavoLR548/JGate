# Use a base image with Oracle JDK installed
FROM sgrio/java-oracle
MAINTAINER Glr

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set working directory
WORKDIR /usr/local/service

# Copy the pom.xml and src files
COPY jgate/pom.xml /usr/local/service/pom.xml
COPY jgate/src /usr/local/service/src

# Package the application using Maven
RUN mvn package

# Expose the port for the application
EXPOSE 8000

# Run the application
CMD ["java", "-cp", "target/jgate-1.0-SNAPSHOT.jar", "com.gustavolr.Application"]