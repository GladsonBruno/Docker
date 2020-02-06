# Compilando o binário
FROM arkhi/maven-3.6.3:latest as BUILD_BINARIO

COPY ./ /home/codigo
WORKDIR /home/codigo

RUN mvn clean compile package

# Criando imagem final
FROM openjdk:8-jdk-alpine

RUN apk add tzdata
RUN cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

WORKDIR /home/binarios

COPY --from=BUILD_BINARIO /home/codigo/target/api-produtos-*.jar /home/binarios
COPY --from=BUILD_BINARIO /home/codigo/src/main/resources/application.yml /home/binarios

RUN mkdir logs

CMD ["sh", "-c", "java -Dserver.port=5555 -Dspring.config.location=./application.yml -jar ./api-produtos-*.jar"]