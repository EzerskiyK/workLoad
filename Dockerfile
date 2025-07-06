FROM eclipse-temurin:19-jdk AS builder
WORKDIR /app

COPY mvnw mvnw.cmd .
COPY .mvn .mvn


COPY pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:19-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]