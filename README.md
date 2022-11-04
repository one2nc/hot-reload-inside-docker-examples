# Hot-Reload-Inside-Docker-Examples

<img src="./fast.png" alt="" width="300" height="300">

Use this repo to set up a fast local development environment in docker with a debugger. Using this repo, you can set up a SpringBoot or
Micronaut application with the following features:
- application built inside docker
- code changes auto-compiled and updated without having to restart the app or container
- remote debugging using IntelliJIdea
- integration test execution using TestContainers in docker

## Prerequisites

- Make sure that you have Docker and Docker-Compose installed
  - Windows or macOS: [Install Docker Desktop](https://www.docker.com/get-started/)
  - Linux: [Install Docker](https://www.docker.com/get-started/) and
    then [Docker Compose](https://github.com/docker/compose)


## Samples of Hot Reload Inside Docker application with multiple integrated services

- [`Spring-Boot / Postgres`](https://github.com/RamanaReddy0M/hot-reload-inside-docker-examples/tree/master/spring-boot-postgres)
  - Sample Java application with SpringBoot Framework and a Postgres database.
- [`Micronaut / Postgres`](https://github.com/RamanaReddy0M/hot-reload-inside-docker-examples/tree/master/micronaut-postgres)
  - Sample Java application with Micronaut Framework and a Postgres database.

## Running the application

The root directory of each application, i.e SpringBoot and Micronaut contains a `docker-compose.yml` file which describes the
configuration of service components. All samples can be run in a local environment by going into the
root directory of the application and executing the following command:

```yaml
docker compose up
```

Check the `README.md` of the respective SpringBoot and Micronaut applications to get more details on the structure and what is the expected
output. To stop and remove all containers of the sample application run:

```yaml
docker compose down
```

## Explanation

### Key Concepts

- Volume Mapping
- Gradle / Maven Dependency Caching

### Volume Mapping

The essential component is __mounting__ current directory from local machine to `app`(WORKDIR)
directory
inside Docker container. SpringBoot / Micronaut application by default comes with Maven / Gradle
Wrappers, this would allow us to run the application within Docker container.

### Gradle / Maven Dependency Caching

Mounting current directory into Docker container helps to have source code and the
build tool within Docker container, but the source code within Docker is dependent on many external
libraries(dependencies) which are not present in current directory.

There are two ways to solve this issue:

- Mounting `.m2` / `.gradle` from Docker Host to Docker container.
- Caching all the dependencies while building the Docker image

Mounting root level directories is not an option to choose. But there are some other issue with
Gradle
caching, if you are mounting `.gradle` and running the application with Gradle build tool within
Docker container, then docker acquires lock for gradle cache, that means we
can't run any application with Gradle build in local machine until Docker container is stopped.

Caching all the dependencies while building Docker image is a good option during development phase.
Since we are downloading all the dependencies image size would be larger(depends on dependencies).


## Remote Debugging Using IntelliJIDEA

https://user-images.githubusercontent.com/90540245/199643735-1462e99f-61ba-4e6b-84f9-6ae60fb3b686.mp4
