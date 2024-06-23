# Etapa de construcción
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos de configuración y dependencias para aprovechar la caché de Docker
COPY pom.xml .
COPY src ./src

# Compila el proyecto y construye el JAR
RUN mvn clean package -DskipTests

# Etapa final
FROM eclipse-temurin:17-jdk-alpine

# Expone el puerto 8090
EXPOSE 8090

# Copia el JAR del directorio target del contenedor de construcción al contenedor final
COPY --from=build /app/target/*.jar app.jar

# Punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]