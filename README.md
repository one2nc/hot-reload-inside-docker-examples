# Hot-Reload-Inside-Docker-Examples

<img src="./fast.png" alt="" width="300" height="300">

Use this repo to set up a fast local development environment in docker with debugger. Using this repo, you can set up a SpringBoot or 
Micronaut application with following features:
  - application built inside docker
  - code changes auto-compiled and updated without having to restart the app or container
  - remote debugging using IntelliJIdea
  - integration test execution using TestContainers in docker

## Samples of Hot Reload Inside Docker application with multiple integrated services

- [`Spring-Boot / Postgres`](https://github.com/RamanaReddy0M/hot-reload-inside-docker-examples/tree/master/spring-boot-postgres)
    - Sample Java application with SpringBoot Framework and a Postgres database.
- [`Micronaut / Postgres`](https://github.com/RamanaReddy0M/hot-reload-inside-docker-examples/tree/master/micronaut-postgres)
    - Sample Java application with Micronaut Framework and a Postgres database.

## Running the application

The root directory of each application, i.e SpringBoot and Micronaut contains a `docker-compose.yml` file which describes the
configuration of service components. All samples can be run in a local environment by going into the
root directory of application and executing the following command:

```yaml
docker compose up -d
```

Check the `README.md` of respective SpringBoot and Micronaut application to get more details on the structure and what is the expected
output. To stop and remove all containers of the sample application run:

```yaml
docker compose down
```
