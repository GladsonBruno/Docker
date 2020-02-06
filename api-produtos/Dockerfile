# Compilando o binário em umcontainer intermediario
# Obtendo imagem do Maven como imagem base
FROM arkhi/maven-3.6.3:latest as BUILD_BINARIO

# Copiando o código de nossa aplicação para dentro do container
COPY ./ /home/codigo

# Definindo o diretório /home/codigo como nosso diretório de trabalho
WORKDIR /home/codigo

# Executando o comando de build dentro de nosso container intermediário Maven
RUN mvn clean compile package

# Criando imagem final
FROM openjdk:8-jdk-alpine

# Adicionando configurações de Timezone em nossa imagem
RUN apk add tzdata
RUN cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

# Definindo o diretório /home/binarios nosso diretório de trabalho
WORKDIR /home/binarios

# Copiando arquivos de nossa primeira etapa de build para a etapa final de build
COPY --from=BUILD_BINARIO /home/codigo/target/api-produtos-*.jar /home/binarios
COPY --from=BUILD_BINARIO /home/codigo/src/main/resources/application.yml /home/binarios

# Definindo comando a ser executado quando nosso container for iniciado
CMD ["sh", "-c", "java -Dspring.config.location=./application.yml -jar ./api-produtos-*.jar"]