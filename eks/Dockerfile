# Utilizar la imagen base de Maven con OpenJDK 21
FROM maven:3.9.9-amazoncorretto-21-alpine

# Crear directorio para la aplicación y establecerlo como el directorio de trabajo
RUN mkdir -p /app
WORKDIR /app

# Copiar el archivo pom.xml y otros archivos necesarios al contenedor
COPY ../pom.xml /app
COPY ../src/main/resources/bootstrap.yml /app
COPY ../src /app/src

# Construir el proyecto Maven
RUN mvn -f pom.xml clean package

# Copiar el archivo JAR generado a la raíz del contenedor
RUN cp target/*.jar app.jar

# Exponer el puerto que usa la aplicación
EXPOSE 8080

# Configurar el comando de entrada para ejecutar la aplicación con el archivo bootstrap.yml
ENTRYPOINT ["java","-jar","app.jar","--spring.config.location=classpath:/bootstrap.yml"]