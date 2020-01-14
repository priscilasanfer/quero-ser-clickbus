<hr/>
<p align="right">
  <sub><b>CHALLENGE</b></sub>
</p>
<p align="center">
  <img src="https://static.clickbus.com/live/ClickBus/logo-clickbus-purple-2018.svg">
  </br>
  An API that manage places. This application was developed as part of ClickBus's selective process.
</p>
<hr/>

## :bus: Table of Contents
- [Introduction](#bus-introduction)
- [Technologies](#bus-technologies)
- [How to Run the project](#bus-how-to-run-the-project)
  - [Getting Started](#bus-getting-started)
  - [Running the Application](#bus-running-the-application)
  - [Running the Tests](#bus-running-the-tests)
- [How to use the API](#bus-how-to-use-the-api)
  - [Available Routes](#bus-available-routes)  
  
## :bus: Introduction
An API that manage places: it allows to:
- Create a place
- Edit a place
- Get a specific place
- List places and filter them by name

Some premises of the challenge:
- I followed the SOLID principles.
- The code is full of tests.
- All API responses are JSON.

## :bus: Technologies
What was used:
- **[SpringBoot](https://spring.io/projects/spring-boot)** to create stand-alone, production-grade Spring based Applications.
- **[PostgreSQL](https://www.postgresql.org/)** to store data.
- **[Spring's MockMvc](https://spring.io/guides/gs/testing-web/)**, **[Mockito](https://site.mockito.org/)** and **[JUnit4](https://junit.org/junit4/)** to write the tests.
- **[Docker](https://docs.docker.com)** and **[DockerCompose](https://docs.docker.com/compose/)** to provide a testing environment.
- **[Swagger](https://swagger.io/)** to document the endpoints.

## :bus: How to Run the project
### :bus: Getting Started
To get started, you should have **Docker** and **Maven** installed. To verify if you already have docker compose run:
```
$ docker-compose --version
```
And to verify if you already have maven, run:
```
$ mvn --version 
```
Clone the repository **[quero-ser-clickbus](https://github.com/vanessadants/quero-ser-clickbus)**:
```
$ git clone https://github.com/vanessadants/quero-ser-clickbus.git
```
In your JAVA IDE, import the application as a maven project. As reference, this project was developed using **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**.

### :bus: Running the Application
Build the docker image of PostgreSQL:
```
$  docker-compose up --build
```
Then, run the SpringBootApplication class **PlacesManagementApplication.java**.
And you're already good to go!

### :bus: Running the Tests
In order to run the tests, go to **/quero-ser-clickbus/testes/backend-developer/solution/places-management/src/test/java**.

## :bus: How to use the API
With the project running, we can check swagger services documentation with the link bellow: **[Swagger endpoints documentation](http://localhost:8080/swagger-ui.html)**.

Here we have a **[Postman Documentation](https://documenter.getpostman.com/view/5901848/SWLiamfD)** that describes and demonstrates how the API should be used and serve to validade the endpoints.

## :bus: Available Routes 

| Routes                 | Description                          | HTTP Methods |
|------------------------|--------------------------------------|--------------|
|/places                 | register a new place                 | POST         |
|/places/{id}            | update an existing place             | PUT          |
|/places/{id}            | get a place by the id                | GET          |
|/places                 | list all the places                  | GET          |
|/places/list/{name}     | get a place by name like ignore case | GET          |

**[More details and examples can be found in the Postman documentation](https://documenter.getpostman.com/view/5901848/SWLiamfD)**
