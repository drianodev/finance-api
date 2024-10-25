# Use uma imagem base do JDK 21
FROM openjdk:21-jdk

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR para o container
COPY ./target/finance-api-0.0.1-SNAPSHOT.jar /app/finance-api.jar

# Defina variáveis de ambiente para o Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://<render_database_host>:5432/finance_db_gnw3
ENV SPRING_DATASOURCE_USERNAME=<render_database_user>
ENV SPRING_DATASOURCE_PASSWORD=<render_database_password>

# Exponha a porta 8080 para acessar a aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "/app/finance-api.jar"]
