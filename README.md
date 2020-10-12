# Lunch
Lunch API

The lunch application reads data from the given urls and loads them into an in memory instance of h2 db using hibernate.

It provides an endpoint to retrieve the list of recipes.

The application requires docker to be running, as the dockerfile contains all the required steps to create the image.

### Running the Application in Docker

The application can be run in docker container by following the below steps:

Clone the application to your local file system.

#### Creating the image
`docker build -t lunch .`

It creates a docker image with name lunch and tag it as latest

#### Running the image as container
`docker run --publish 8090:8080 --name lunch --detach lunch:latest`

This run the docker container in port 8090 for external use, so we can access the application using localhost:8090.

Change 8090 to any other value to change the port.

The name of the container is specified using --name, so we can use the name when referencing the container

Once the container is running, the endpoint can be accessed using the url

`localhost:8090/api/lunch`

#### Stopping the container
`docker stop lunch`

This stops the running container.

#### Starting the container
`docker start lunch`

This starts a container that is stopped.

#### Removing the container
`docker rm lunch`

This removes the container

#### Removing the image
`docker rmi lunch`

This removes the image

### Running the tests

Unit tests can be run using `mvn test`.

### Creating the artifact

War package can be created using `mvn package`, although this is not required as the artifact will be created during docker image creation.