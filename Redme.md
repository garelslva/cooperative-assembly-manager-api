# # Cooperative Assembly Manager API

### Requisitos minimos
- java 20+
- maven 3.6.3+
- postgresSQL 15.3
- redis 7.0.11

### Requisitos Opcionais
- docker
- docker compose

### ..: PS: Os comandos a seguir deve ser executado no path root do projeto

### Subindo a aplicação Local usando o docker compose
- 1 - usar maven versão 3.6.3 ou superior

 ```
 # mvn clean install
 ```

- 2 - executar o comando docker-compose
 ```
 # docker-compose up --build -d
 ```

### Ou Subindo a aplicação Local usando o docker

- 1 - postgres
```
# docker run --name postgres -v /tmp/postegreslocal -e POSTGRES_DB=cooperative-assembly-manager-api -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 postgres:15.3
```

- 2 - redis
```
# docker run --name redis -v /tmp/redis -p 6379:6379 redis:7.0.11-alpine
```

- 3 - api
```
# docker build -t api .
# docker run --name api -e SERVER_PORT=8080 -e DATABASE_URL=postgres -e DATABASE_PORT=5432 -e DATABASE_NAME=cooperative-assembly-manager-api -e DATABASE_SSLMODE=disable -e REDIS_HOST=redis -e REDIS_PORT=6379 -p 8080:8080 -it api
```

### Ou Subindo a aplicação Local usando comandos shell
#### - api
- Considera-se que:
- - postgres e o redis esta rodando localhost
- - o camando mvn clean install ja foi executado
- - esse comando deve ser executado no path root do projeto

```
java -Xss512k -Xms512M -Xmx1024M -XX:MaxGCPauseMillis=500 -XX:+UseG1GC -XX:+DisableExplicitGC -XX:SurvivorRatio=6 -XX:MaxMetaspaceSize=256m -XX:+ParallelRefProcEnabled \
-Dserver.port=8080 \
-Dpostgre.database.host=localhost \
-Dpostgre.database.port=5432 \
-Dpostgre.database.name=cooperative-assembly-manager-api \
-Dpostgre.database.sslmode=disable \
-Dspring.datasource.username=postgres \
-Dspring.datasource.password=admin \
-Dspring.redis.host=localhost \
-Dspring.redis.port=6379 \
-jar ./target/cooperative-assembly-manager-api-1.0.0.jar
```

### Swagger - Documentação da Api
##### Após execução da aplicação acessar pelo navegador a url abaixo
- http://localhost:8080/swagger-ui/index.html#/

### Fluxo

- Exemplo criar uma Pauta
```
curl -X 'POST' \
  'http://localhost:8080/subject' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "question": "exemplo de pauta a ser votada em uma assembleia",
  "answers": [
    "SIM",
    "NAO"
  ]
}'
```

- Exemplo Criar um associado
```
curl -X 'POST' \
  'http://localhost:8088/associate' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Nome",
  "cpf": "33822803022"
}'
```

- Exemplo Criar uma sessão
```
curl -X 'POST' \
  'http://localhost:8088/section' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "limitTimeInSeconds": 480,
  "subjectId": 11
}'
```

- Exemplo Votar uma pauta
```
curl -X 'POST' \
  'http://localhost:8088/session' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "limitTimeInSeconds": 1000,
  "subjectId": 3
}'
```

- Exemplo Consultar pautas
```
curl -X 'GET' \
  'http://localhost:8088/subject' \
  -H 'accept: application/json'
```

##### ..: PS Mais informações consulte a documentação da api (swagger) http://localhost:8080/swagger-ui/index.html#/

## Desafio proposto

### Objetivo
- No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação.


### Solicitação: API REST

- [X] ●	Cadastrar uma nova pauta
- [X] ●	Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
- [X] ●	Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
- [X] ●	Contabilizar os votos e dar o resultado da votação na pauta
- [X] ●	Tarefa Bônus 1 - Integração com sistemas externos
- [X] ●	Tarefa Bônus 2 - Performance
- [X] ●	Tarefa Bônus 3 - Versionamento da API

## Solução e hipóteses arquiteturais
## Abordagem

### Hipóteses facilitadoras
* Software irá crescer em sua complexidade
* Software irá crescer no seu número de features
* Será utilizados por muitos
* Atenderá volume alto de requisições
* Sofrerá muitas manutenções

Foi adotado o principio de clan Architecture, com uma aboradagem mais simplista, mantendo o dominio e regras de negocio no centro e suas camadas externas com interfaces e converters para manter a alta coesão e baixo acoplamento, assim como os dominios totalmente desacoplado.

