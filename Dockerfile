# Étape 1 : construire l'application
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copier les fichiers Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Copier le code source
COPY src/ src/

# Construire l'application
RUN chmod +x mvnw && ./mvnw -B clean package

# Étape 2 : exécuter l'application
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]