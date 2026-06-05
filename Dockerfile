FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre-jammy
EXPOSE 8080
COPY --from=build /home/app/build/libs/productjpa-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]