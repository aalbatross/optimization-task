# Introduction

KOS code base is using maven build system. To run the application build from source on local machine we need following:

Pre-requisite:
1. git
2. maven 3.5
3. Java8
4. Docker Engine and compose

## Code structure

```
optimization-task ->
					-> optimization-service (KO Web service)
					-> optimization-solver (Knapsack Solver)
					-> optimization-persistence (Data layer of service)
					-> optimization-api (Service api and model classes for service interaction)
					-> optimization-client (Web client to interact with the KOS Service)
					-> integration-test (Integration test of the service and web client for KOService)
					-> docs (Design, Getting started and developers guide is available)
					docker-compose.yml (Build and run latest code in the repo, also used for integration test)
```
					
There are 2 main process optimization-service and optimization-solver, both these projects have there Dockerfile inside them.

## API docs
Generate the api docs.  

```
cd optimization-task
mvn javadocs:aggregate
```
This command creates the javadoc for the entire API in optimization-task directory.

## Taking solution from code base

```
git clone https://iamrp@bitbucket.org/iamrp/optimization-task.git
```

## Testing the solution

#### Running automated unit test

```
cd optimization-task
mvn clean install
```

#### Running integration tests
```
cd optimization-task
docker-compose build
docker-compose up
cd integration-test
mvn clean install test -Pintegration-test
```

#### Running all automated tests

```
cd optimization-test
docker-compose build
docker-compose up
mvn clean install test -Pintegration-test
```

## Docker images for service and solver
The Dockerfile for both the processes are available in there respective module.

## Running the solution on local machine
This step just needs docker and docker compose on the machine. It runs the solution on localhost.
You can start accessing service from http://localhost:8080/. Refer the Getting started guide to run step by step how to create and execute tasks.

```
cd optimization-task
docker-compose build
docker-compose up
```

