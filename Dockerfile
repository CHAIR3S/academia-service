# Etapa de construcción
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia solo los archivos de configuración inicialmente para aprovechar la caché de Docker
COPY pom.xml .

# Descarga las dependencias del proyecto sin compilar nada
RUN mvn dependency:go-offline

# Copia el resto de los archivos del proyecto
COPY src ./src

# Compila el proyecto y construye el WAR
RUN mvn clean package -DskipTests

# Etapa final
FROM tomcat:10.1-jdk17-corretto-alpine

# Copia el WAR del directorio target del contenedor de construcción al directorio webapps de Tomcat
COPY --from=build /app/target/academia-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expone el puerto 8080
EXPOSE 8080

# Configura Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT && \
    mv /usr/local/tomcat/webapps/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Inicia Tomcat
CMD ["catalina.sh", "run"]
