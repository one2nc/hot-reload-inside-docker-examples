# hot-reload-inside-docker-examples

Use this repo to set up a fast local dev env in docker with debugger. Using this repo, you can set up a Spring Boot or 
Micronaut application with following features:
  - application built inside docker
  - code changes auto-compiled and updated without having to restart the app or container
  - remote debugging using IntelliJIdea
  - integration test execution using TestContainers in docker

A list of sample applications to set up Fast Local Development Environment with Docker.

<img src="./fast.png" alt="" width="300" height="300">


### What do we mean by Fast Local Development?

What we mean by fast local development is that in our application we incorporate the following
functionalities:

- Code changes should be reflected immediately i.e hot reload of code
- Test cases should fail when changed on the fly
- Add new dependency, and no other dependency will be downloaded, only the most recently added
  dependency will be downloaded and cached for the next build

## Samples of Hot Reload Inside Docker application with multiple integrated services

- [`Spring-Boot / Postgres`](https://github.com/RamanaReddy0M/hot-reload-inside-docker-examples/tree/master/spring-boot-postgres)
    - Sample Java application with Spring Framework and a Postgres database.
- [`Micronaut / Postgres`](https://github.com/RamanaReddy0M/hot-reload-inside-docker-examples/tree/master/micronaut-postgres)
    - Sample Java application with Micronaut Framework and a Postgres database.

## Running a sample

The root directory of each sample contains the `docker-compose.yml` which describes the
configuration of service components. All samples can be run in a local environment by going into the
root directory of each one and executing:

```yaml
docker compose up -d
```

Check the `README.md` of each sample to get more details on the structure and what is the expected
output. To stop and remove all containers of the sample application run:

```yaml
docker compose down
```
