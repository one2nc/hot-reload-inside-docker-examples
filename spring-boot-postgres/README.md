## Spring-Boot Postgres

### Use with Docker Development Environment

    A sample application to set up fast local development with hot reload and debugger inside docker.

## Outcome

By doing `docker-compose up` inside working directory you should be able to run the Spring Boot
application. The app is compiled in docker. Your code changes will be automatically compiled and hot reloaded without
having to restart the app or docker container.

## Prerequisites

- Make sure that you have Docker and Docker-Compose installed
    - Windows or macOS: [Install Docker Desktop](https://www.docker.com/get-started/)
    - Linux: [Install Docker](https://www.docker.com/get-started/) and
      then [Docker Compose](https://github.com/docker/compose)

## Caution!

To achieve hot reload inside docker, You just need to add Dockerfile and Docker-Compose file to the
existing project. But it isn't straight forward in case of Spring-Boot application. We need to
perform additional check i.e, Does hot reload works locally?

Quick answer to happen hot reload locally for a Spring-Boot application is to
have `spring-boot-devtools` dependency
inside `pom.xl`.

## Spring-Boot + Postgres + Docker

### Step-1:

Ensure that hot reload works locally, For that we need to
add `spring-boot-devtools` dependency to `pom.xml` Which help to re-run the application when the
changes detected.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
</dependency>
```

> __Note__: If you're using intellij, After adding `devtools` dependency enable two properties
> named `Build project automatically`
> and `Allow auto-make to start even if developed application is currently running` by navigating to
> Preferences of intellij as shown.

![intellij-build-setting](./src/main/resources/images/intellij-build-setting.png)

![intellij-setting](./src/main/resources/images/intellij-hot-relaod-setting.png)

After applying these changes run the application. Now, While the application is up and running, Do
some Changes to your code base and you would see in the console that the application is
automatically restarted
without hitting run button.

### Step-2:

Add `Dockerfile` and `docker-compose.yml` to the working directory.

Project structure after adding `Dockerfile` and `docker-compose.yml`

```
<working-dir>
├── ...
├── src
|     └── ...
├── Dockerfile
├── docker-compose.yml
└── README.md
```

Now, Copy the content of bellowed `Dockerfile` and `docker-compose.yml` file and paste it into newly
created __Dockerfile__ and __docker-compose.yml__ files in your project.

### Templates of Dockerfile and Docker-Compose

[Dockerfile](./Dockerfile)

```dockerfile
FROM openjdk:11

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -T 4 dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
```

[docker-compose.yml](./docker-compose.yml)

```yaml
version: '3.8'
services:
  <your-application-name-as-service>:
    image: <your-image-name>
    container_name: <your-container-name>
    networks:
      - spring-boot-postgres-network
    build:
      context: .
    env_file: .env
    depends_on:
      - db
    ports:
      - ${APPLICATION_PORT_ON_DOCKER_HOST}:${APPLICATION_PORT_ON_CONTAINER}
      - ${DEBUG_PORT_ON_DOCKER_HOST}:${DEBUG_PORT_ON_CONTAINER}
    volumes:
      - ./:/app
    command: ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:${DEBUG_PORT_ON_CONTAINER}"

  db:
    container_name: spring-boot-postgres-db
    image: postgres:14.1-alpine
    env_file: .env
    ports:
      - ${DB_PORT_ON_DOCKER_HOST}:${DB_PORT_ON_CONTAINER}
    volumes:
      - db:/var/lib/postgresql/data
    networks:
      - spring-boot-postgres-network

volumes:
  db:

networks:
  spring-boot-postgres-network:
```

> __NOTE__: Replace `<tags>` a/c to your application name.


Here each service acts as new container. Since our application is dependent on `db` service, We need
to take care of few things like -
- `<your-application-name-as-service>` service shouldn't start before `db` service. And that is why we
  used `depends_on` property under `<your-application-name-as-service>`.
- `<your-application-name-as-service>` service and `db` both has to be on the same network. So that they
  can communicate each other. If we don't provide any network to services, They might run in
  isolated networks which leads to communication link failure between application and database.
- Finally, for hot reloading of the app inside docker, our current directory(where the source code exists)
  should be mounted to working directory inside container.
```yaml
    volumes:
      - ./:/app
```

### Step-3:

In this `docker-compose.yml` file, You would see that the variables used
like `${APPLICATION_PORT_ON_DOCKER_HOST}`, `${APPLICATION_PORT_ON_CONTAINER}`,  
and `${DB_PORT_ON_CONTAINER}`. One might think(people new to docker) that how would we pass values
to
these variables? Well there are different ways to do that, One is by defining under
the `environment` property of any service. Other way is to
define all these values
inside [.env](./.env).

Here, We'll be using `.env` file to pass values to these variables.

Create a [`.env`](./.env) file inside the working directory.

Then the project structure is:

```
<working-dir>
├── ...
├── src
|     └── ...
├── .env
├── Dockerfile
├── docker-compose.yml
└── README.md
```

Replace the content of newly created `.env` file with this [.env](./.env) file.

### Step-4:

Follow the commands to run docker-compose file

1. Change directory in Terminal or CMD to `<working-dir>`

> $ cd `<PATH-TO-WORKING-DIR>`

2. Run the `docker-compose.yml` file.

> $ docker-compose up

If you're running `docker-compose up -d` command for the first time, it would take 7-10 minutes to pull images(
postgres and openjdk:11) and download dependencies. If everything runs successfully, By doing `docker ps` you
would see the following outcome.

```
➜  student-grading-micronaut ✗ docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS          PORTS                                            NAMES
8247f3b42566   student-grading-micronaut-image   "./mvnw spring-boot:…"   29 seconds ago   Up 25 seconds   0.0.0.0:8080->8080/tcp   student-grading-micronaut-app
04a7dbf0c0e3   postgres:14.1-alpine              "docker-entrypoint.s…"   4 minutes ago    Up 4 minutes    5432/tcp                 student-grading-db
```

If application is failed to start, You would need to figure why it failed by observing the logs.
> $ docker logs --follow `<container-name>`

## How to run E2E tests inside docker?

To run End-To-End(E2E) tests, we need to mock the server and database. One way to do that is by
using
[test containers](https://www.testcontainers.org/).

Add `docker-compose-test.yml` file would help to run test inside docker. Then the project structure
is:

```
<working-dir>
├── ...
├── src
|     └── ...
├── Dockerfile
├── docker-compose.yml
├── docker-compose-test.yml
└── README.md
```

[docker-compose-test.yml](https://github.com/chinmaysomani07/student-grading-micronaut/blob/dockerise-setup/docker-compose-test.yml)

```yaml
version: '3.8'
services:

  tests:
    image: maven:3
    stop_signal: SIGKILL
    stdin_open: true
    tty: true
    working_dir: /app
    environment:
      - TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal
    volumes:
      - ./:/app
      - /var/run/docker.sock:/var/run/docker.sock
      # Maven cache (optional), if .m2 repository isn't mounted, maven will download all
      # the dependencies mentioned in 'pom.xml' from mvn central repository.
      - ~/.m2:/root/.m2
    command: mvn clean test
```

Here `~/.m2` is specific to mac, If you're using different platform, Replace `~/.m2`
with `C:\Users\{your-username}\.m2` for windows or `/root/.m2` for linux.

Follow the command to run tests inside docker.

1. Change directory in Terminal or CMD to `<working-dir>`

> $ cd `<PATH-TO-WORKING-DIR>`

2. Run the `docker-compose-test.yml` file.

> $ docker-compose -f docker-compose-test.yml up

If you're not mounting the `.m2` then it would time take to download all the dependencies mentioned
in `pom.xml`.

Once the dependencies mounted or downloaded, You would see the following logs as good sign -

![Test-Logs](./src/main/resources/images/docker-compose-test-logs.png)
