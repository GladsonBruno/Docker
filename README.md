# Introdução

Antes de começarmos a falar de Docker vamos voltar um pouco no tempo para falar de um problema recorrente que muitas empresas de software sofriam e ainda sofrem.

Considere o seguinte cenário: <br/>
Você possui uma aplicação desenvolvida em Java(ou em qualquer outra linguagem) que depende de uma série de dependências para seu funcionamento.<br/>
Quando esta aplicação foi enviada para o servidor de Homologação ocorreu um problema e o pessoal da área responsável pela implantação enviou um e-mail gigante dizendo que a aplicação não funciona, neste momento seu telefone toca com seu gerente furioso dizendo que a aplicação não está funcionando e você simplesmente utiliza a boa e velha desculpa: "Ah, mas na minha máquina funciona!"<br/>
Depois de muitas horas gastas e várias garrafas de café consumidas você finalmente descobre os problemas: 
- O servidor não possuía a biblioteca X
- O servidor não possuía a versão Y de determinada coisa
- O servidor não possuía a variável de ambiente Z.
- A aplicação precisa da versão 11 do Java porém o servidor hospeda outra aplicação que precisa exatamente da versão 8 do Java e os problemas só vão aumentando.

Este cenário é o que chamamos tipicamente de Dependency Hell, e é um dos grandes problemas que os Containers Docker buscam resolver.

# Mas enfim, o que é Docker?

O Docker é um solução para containerização(que é uma alternativa para as tradicionais máquinas virtuais) criada com o objetivo de facilitar o desenvolvimento, distribuição, implantação, execução de aplicações em ambientes isolados.


# Containers vs Máquinas virtuais

Enquanto as máquinas virtuais tem um sistema operacional completo e vários softwares e bibliotecas para executar as aplicações, os containers possuem apenas a aplicação e as bibliotecas e dependências da mesma, compartilhando uma mesma engine. 
Diferente de uma máquina virtual os conteiners são bem menores e podem ser facilmente compartilhados e replicados.

<img src="https://static.wixstatic.com/media/e61508_85e417916cd042f39181763c00ae94e7~mv2.jpg"/>

# Quais sistemas Operacionais suportam?

O Docker possui suporte para Windows, Linux e Mac


# Alguns conceitos chave para se trabalhar com Docker

### Container

É um método de virtualização em nível de sistema operacional que permite executar uma aplicação e suas dependências como processos e com recursos isolados que simulam uma máquina virtual.<br/>
Os containers são isolados a nível de disco, memória, processamento e rede. Essa separação permite grande flexibilidade, onde ambientes distintos podem coexistir no mesmo host, sem causar qualquer problema. <br/>
Um container do Docker pode ser criado através de Uma imagem.

### Imagem

Uma imagem nada nada mais é que um pacote de software leve, autônomo e executável que inclui tudo o que é necessário para
executar uma aplicação como código, ferramentas e bibliotecas do sistema, configurações e etc.

### Dockerfile

O Dockerfile é um arquivo totalmente declarativo que possui um formato que lembra uma receita de bolo, nele especificamos todas as nossas configurações e dependências necessárias para gerarmos nossa imagem Docker.<br/>
Quando nosso Dockerfile estiver pronto podemos gerar a imagem e distribuí-la com tudo aquilo que adicionamos em sua construção.

### Volumes
Os volumes são um mecanismo no qual podemos persistir os dados gerados em nosso container Docker, podemos também através deles mapear arquivos de configuração local para que os mesmos sejam refletidos dentro do container.


# Executando seu primeiro container

Primeiro temos que obter a imagem do Dockerhub, que nada mais é do que um repositório onde imagens Docker são armazenadas para poderem ser compartilhadas.<br/>
Podemos realizar o download de imagem com o seguinte comando: 
```
docker pull image-name:version
```

Para executarmos um container docker é bem simples, basta utilizarmos o comando:
```
docker run [OPTIONS] image-name:version
```

Utilizaremos como exemplo a imagem **hello-world** para testes, podemos baixá-la utilizando o seguinte comando:
```
docker pull hello-world
```

Para criarmos um container e executarmos a imagem **hello-world** podemos utilizar o seguinte comando: 
```
docker run --rm hello-world
```

Este comando irá criar um container da imagem **hello-world**, a opção **--rm** informa ao docker para deletar o container assim que a execução do mesmo for finalizada.


# Alguns comandos úteis

- **docker ps**: Este comando lista todos os containers em funcionamento, caso seja utilizado com a opção **-a** ou **--all** são retornados todos os containers do host, incluindo os containers que não estão em execução.
- **docker images**: Este comando retorna todas as images Docker que estão disponíveis em sua máquina.
- **docker logs**: Permite verificar os logs de execução de um container, se utilizado em conjunto com a opção **-f** os logs do container serão mostrados em tempo real no console.
- **docker build**: Este comando é utilizado para realizar o build e uma imagem Docker através de um Dockerfile
- **docker push**: Comando utilizado para enviar uma imagem para o Dockerhub
- **docker start**: Comando utilizando para startar um container que não estiver em execução.
- **docker stop**: Comando utilizado para parar um container que estiver em execução
- **docker exec**: Comando utilizado para executar um comando dentro do container Docker.
- **docker network**: Comando utilizado para manipular e criar redes dentro do Docker.


# Apresentando aplicação de exemplo

Nossa aplicação de exemplo é uma API de Produtos criada utilizando Java 8 e o framework Spring Boot, a mesma se conecta a uma base de dados MySql para consultar e disponibilizar os dados em seu endpoint de produtos.<br/>
Para acessar a documentação da mesma com as rotas de API disponíveis podemos abrir o seguinte link no browser: ``http://localhost:porta/swagger-ui.html``

O código fonte da aplicação pode ser obtida neste repositório https://github.com/GladsonBruno/Docker <br/>
Após clonar o repositório em sua máquina local navegue até dentro dele com seu terminal.

# Criando nossa rede no Docker
O Docker nos possibilita criar um isolamento de rede, com isso podemos fazer com que nossos container trabalhem em redes isoladas ou conjuntas, iremos criar agora uma rede conjunta para nossa aplicação e nossa base de dados, para isso utilizaremos o seguinte comando:
```
docker network create rede-api-produtos
```
Com o comando acima criamos uma rede chamada **rede-api-produtos**.<br/>
Para listar todas as redes gerenciadas pelo Docker utilize o comando ``docker network ls``.<br/>

# Criando nosso container de Banco de dados.

Primeiro precisaremos de uma base de dados MySql, por sorte já existe uma imagem criada no Dockerhub que pode ser encontrada no seguinte link assim como sua documentação: https://hub.docker.com/_/mysql

Parar criarmos nosso container MySql utilizaremos o seguinte comando na pasta raiz de nosso repositório clonado:
```
# Comando para Linux
docker run --name MySqlDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tdc -v /$("pwd")/db/:/docker-entrypoint-initdb.d --network=rede-api-produtos -d mysql

# Comando para Windows
docker run --name MySqlDB -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tdc -v /c/Users/glads/OneDrive/Documentos/TDC/db/:/docker-entrypoint-initdb.d --network=rede-api-produtos -d mysql
```

Podemos notar alguns parâmetros novos em nosso comando docker run, sendo eles:<br/>
- **--name**: Define o nome do container
- **-e**: Define as variáveis de ambiente de nosso container.
- **-v**: Define um mapeamento de volume no qual estamos passando para o container My SQL nosso script de criação de nossa base de dados. A diferença entre o comando para Windows e Linux é que no Windows é necessário informar o Path absoluto no mapemaneto de volume, se estiver utilizando Windows realize a correção do Path de acordo com o Path de sua máquina.
- **--network=rede-api-produtos**: Com esta opção especificamos que nosso container faz parte da **rede-api-produtos**, como não fizemos a exposição da porta de nosso container o mesmo poderá ser acessado apenas dentro de nossa rede interna.
- **-d**: Define que nosso container será executado em segundo plano, se não especificarmos essa opção nossa aba do terminal ficará por conta da execução do container.


Após a criação de nosso container podemos executar o seguinte comando para verificar os logs de nosso container: <br/>
```
docker logs -f MySqlDB
``` 
Para sair da visualização de logs basta pressionar ``CTRL + C``


# Criando a imagem Docker de nossa aplicação

Conforme explicando anteriormente, o Dockerfile é basicamente uma receita de bolo no qual podemos montar um ambiente para que nossa aplicação possa executar e ser distribuída.<br/>
O conteúdo de nosso Dockerfile será o seguinte:
```
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
```

O Dockerfile deve ser criado na pasta raiz de nosso projeto ``api-produtos``.<br/>
Após isso iremos executar o seguinte comando para criar nossa imagem Docker:
```
docker build --network=rede-api-produtos -t api-produtos:v1 .
```

Com este comando estamos realizando o build de nossa imagem Docker seguindo os passos especificados em nosso Dockerfile.<br/>
Possuímos algumas opções em nosso comando sendo elas:
- **-t**: Nos permite definir o nome da imagem.
- **--network=host**: Informa ao Docker para utilizar a rede de nosso host durante o build de nossa aplicação.
- **.**:  Define que o diretório atual será utilizado para buscar nosso Dockerfile e nosso código fonte.

Caso o Dockerfile esteja com outro nome você pode utilizar a opção **-f** para definir o nome do Dockerfile.

Quando este comando finalizar teremos a imagem ``api-produtos:v1`` criada, podemos verificar se a mesma foi criada usando o comando ``docker images``.

# Executando nossa imagem Docker

Para utilizar nossa imagem Docker criada navegue até o diretório raiz do projeto clonado, após isso podemos usar o seguinte comando:
```
# Comando para Linux
docker run --name ApiProdutos -v /$(pwd)/api-produtos/src/main/resources/application.yml:/home/binarios/application.yml --network=rede-api-produtos -p 8080:8080 -d api-produtos:v1

# Comando para Windows
docker run --name ApiProdutos -v /c/Users/glads/OneDrive/Documentos/TDC/api-produtos/src/main/resources/application.yml:/home/binarios/application.yml --network=rede-api-produtos -p 8080:8080 -d api-produtos:v1
```

Observe que agora estamos utilizando a opção **-p** em nosso comando, esta opção define um mapeamento de porta para que possamos acessar a porta 8080 de nosso container na porta 8080 de nosso host.<br/>
A diferença entre o comando para Windows e Linux é que no Windows é necessário informar o Path absoluto no mapeamento de volume, se estiver utilizando Windows realize a correção do Path de acordo com o Path de sua máquina.

# Testando nossa aplicação

Após nosso container entrar em execução podemos acessar o seguinte endereço para testar se nossa API está funcionando: ``http://localhost:porta/swagger-ui.html``


# Subindo nossa imagem para o Dockerhub

O Dockerhub é o repositório de imagens disponibilizado pelo Docker, nele podemos disponibilizar imagens públicas e também privadas porém com um limite de imagens privadas por tipo de plano de conta.<br/>
Por padrão a conta gratuita disponibiliza a opção de uma imagem privada apenas.

Antes de subir nossa imagem precisaremos primeiro fazer login em nossa conta do Dockerhub no terminal, para isso execute o seguinte comando:<br/>
```
docker login
```

Será pedido que seja informado usuário e senha, após informa-los iremos para o próximo passo.

Para subir uma imagem para o Dockerhub utilizamos o seguinte comando:
```
docker push user/image-name:version
```

Como podemos ver é necessário que o nome de nossa imagem possua o nome de usuário da conta ao qual ela será enviada.<br/>
Como criamos nossa imagem ser definir isso teremos que definir isso antes de enviar a mesma para o Dockerhub, por sorte podemos apenas utilizar o comando ``docker tag`` para realizar esta correção conforme o exemplo abaixo:<br/>
```
docker tag api-produtos:v1 gladson21/api-produtos:v1
```

Para compreender melhor o comando acima veja a estrutura do comando docker tag abaixo:
```
docker tag image-name new-image-name
```

Após realizar a correção do nome de nossa imagem utilizaremos o comando ``docker push`` para realizar o upload de nossa imagem Docker para o Dockerhub:.<br/>
No caso de nosso exemplo utilizaremos o comando: 
```
docker push gladson21/api-produtos:v1
```

Após o comando ser finalizado poderemos ver nossa imagem Docker em nossa conta do Dockerhub.


# Orquestradores de Container
Os Orquestradores de Container de certa forma podem ser vistos como ferramentas mágicas que podem criar e gerenciar nosso containers de forma mais fácil, falaremos brevemente de um deles que é o Docker Compose.<br/>

## Docker Compose
O Docker Compose nos permite transferir toda a lógica do comando de criação de um container para um arquivo YML de forma que o próprio Docker Compose possa gerenciar nossos containers de acordo com o quer for configurado no arquivo .YML<br/>
Por padrão o arquivo é chamado de ``docker-compose.yml`` e possui uma estrutura semelhante a esta:
```java
version: 'x.x'

services:
  service-1:
    container_name: Name
    image: image:version
    environment:
      ENV_1: value_env_1
      ENV_2: value_env_2
    volumes:
      - /host-path/:/container-path
    ports:
      - "host-port:container-port"
  
  service-n:
    .
    .
    .
```

No caso de nossa aplicação nós teríamos um arquivo Docker Compose semelhante a este:
```java
version: '2.1'
services:
  mysql-db:
    container_name: MySqlDB
    image: mysql:latest
    restart: always
    environment:
      MYSQL_DATABASE: tdc
      MYSQL_ROOT_PASSWORD: root
    volumes:
      - ./db/:/docker-entrypoint-initdb.d
    healthcheck:
      test: mysqladmin ping -h 127.0.0.1 -u root --password=$$MYSQL_ROOT_PASSWORD
      interval: 30s
      timeout: 10s
      retries: 5

  api-produtos:
    container_name: ApiProdutos
    image: gladson21/api-produtos:v1
    restart: always
    volumes:
      - ./api-produtos/src/main/resources/application.yml:/home/binarios/application.yml
    ports:
      - "8080:8080"
    depends_on:
      mysql-db:
          condition: service_healthy
```

O código acima deve ser inserido no arquivo ``docker-compose.yml`` e o mesmo deve ser inserido no diretório raiz de nosso repositório clonado.<br/>

Antes de subir nossos containers pelo Docker Compose primeiro precisaremos remover nossos primeiros containers e nossa rede que criamos, para isso execute o seguinte comando:
```
docker rm -f ApiProdutos MySqlDB
docker network rm rede-api-produtos
```

Com os containers antigos removidos podemos executar o comando ``docker-compose up -d`` para criar nossos containers.<br/>
A opção **-d** é semelhante a opção **-d** do Docker, ou seja, nossos containers serão iniciados em segundo plano.
Após o comando finalizar sua execução teremos nossos containers em funcionamento.

Em nosso arquivo docker-compose.yml não foi necessário definir uma configuração de rede pois o Docker Compose se encarrega de criar uma rede padrão durate a criação dos containers mas também é possível definir uma outra rede para nossos containers.

## Comandos úteis do Docker Compose
- **docker-compose stop**: Para a execução de nossos containers
- **docker-compose rm**: Remove nossos containers que estiverem parados
- **docker-compose down**: Para e remove nossos containers em execução
