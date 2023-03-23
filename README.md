# chatbot

## Frontend: [link](https://github.com/smartisanyyh/chatbot_wmp)

## Preparation

* Wechat mini program [enroll/login](https://mp.weixin.qq.com/)
* ChatGPT apikey [enroll/get](https://platform.openai.com/account/api-keys)

## Deploy

[![Deploy on Railway](https://railway.app/button.svg)](https://railway.app/template/Pfg93k?referralCode=jdPmwi)

Due to the problem of railway current template creation, please copy and paste below variables to the variable row
editor manually.

```
wx.appId=your app id
wx.secret=your secret
PGDATABASE=${{Postgres.PGDATABASE}}
PGHOST=${{Postgres.PGHOST}}
PGPASSWORD=${{Postgres.PGPASSWORD}}
PGPORT=${{Postgres.PGPORT}}
PGUSER=${{Postgres.PGUSER}}
REDIS_URL=${{Redis.REDIS_URL}}
```

> Since there is no time to do the management interface, let's reluctantly use curl to add apikey.

### Api

#### get api key

```shell
curl --location --request GET '{your_host}/key' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

#### add api key

```shell
curl --location --request POST '{your_host}/key/{your_api_key}' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

#### delete api key

```shell
curl --location --request DELETE '{your_host}/key/{your_api_key}' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

### Change admin password

Now the method of http basic is used for identity authentication, and the password can be changed by yourself in the
database.
password should be encrypt with io.quarkus.elytron.security.common.BcryptUtil.bcryptHash(password)

```java
    public static void main(String[]args){
        String password="admin";
        BcryptUtil.bcryptHash(password);
        }
```

---

> If you want to know more about quarkus, please look at the below part. If you don't want to, you can skip it directly.


This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/chatbot-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and
  method parameters for your beans (REST, CDI, JPA)
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time
  processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions
  that depend on it.
- Redis Client ([guide](https://quarkus.io/guides/redis)): Connect to Redis in either imperative or reactive style
- REST resources for Hibernate Reactive with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate
  JAX-RS resources for your Hibernate Reactive Panache entities and repositories

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
