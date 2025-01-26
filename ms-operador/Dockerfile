# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar solo el archivo de configuración Maven
COPY pom.xml ./

# Descargar dependencias para caché eficiente
RUN mvn dependency:go-offline -B

# Copiar el código fuente de la aplicación
COPY src ./src

# Compilar el proyecto y empaquetar
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app

# Copiar el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto necesario
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
