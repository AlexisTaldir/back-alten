# Utilise une image Java 21 officielle comme base
FROM openjdk:21-jdk

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copie le jar généré par Maven dans le conteneur
COPY target/*.jar app.jar

# Expose le port utilisé par Spring Boot (ici 80)
EXPOSE 80

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
