### 1.创建Postgresql数据库
```
docker run --name pactbroker-db \
--restart unless-stopped \
-p 5432:5432 \
-e POSTGRES_PASSWORD=123456 \
-e POSTGRES_USER=admin \
-e POSTGRES_DB=pact \
-v /你本地的一个地址:/var/lib/postgresql/data \
-d postgres
```

### 2.创建Pact数据库和专有用户（可选）
##### 使用docker指令进入容器
```
docker run -it --rm --link pactbroker-db:postgres \
postgres psql -h postgres -U admin -d template1
```
##### 创建pactuser用户和pactbroker数据库
```
template1=# CREATE USER pactuser WITH PASSWORD '123456';
CREATE ROLE
template1=# CREATE DATABASE pactbroker WITH OWNER pactuser;
CREATE DATABASE
template1=# GRANT ALL PRIVILEGES ON DATABASE pactbroker TO pactuser;
GRANT
template1=# \q
```

### 3.创建PactBroker实例
```
docker run --name pactbroker -p 9500:80 \
--link pactbroker-db:postgres \
-e PACT_BROKER_DATABASE_USERNAME=pactuser \
-e PACT_BROKER_DATABASE_PASSWORD=123456 \
-e PACT_BROKER_DATABASE_HOST=postgres \
-e PACT_BROKER_DATABASE_NAME=pactbroker \ 
-d dius/pact-broker
```

### 4.访问 [http://localhost:9500](http://localhost:9500)


