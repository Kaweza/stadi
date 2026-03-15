FROM eclipse-temurin:21-jdk
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080
CMD ["java","-jar","target/stadi-0.0.1-SNAPSHOT.jar"]