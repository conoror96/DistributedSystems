# DistributedSystems
Project for Distributed Systems. <br />
Part 1 is A password service that provides hashing and verification services which exposes a gRPC API. <br />
Part 2 is a REST API for a User Account web service using OpenAPI and SwaggerHub. The API is implemented in Java using the Dropwizard microservice framework <br />

Developed with Maven and IntelliJ <br />

GitHub Repo: https://github.com/kodama96/DistributedSystems

# Part 1
[Part 1 specifications can be found here](https://learnonline.gmit.ie/pluginfile.php/119965/mod_assign/intro/Project2019_Part1.pdf)
## Running the code
###  Running the server
* Navigate into the .jar file directory *part1/target/* on command line <br /> 
* Run the following command: *java -jar passwordservice.jar* <br />

### Running the client for part 1
* Run the PasswordServiceClient <br />
* The user will be prompted to enter user ID & Password <br />
* The user ID, password, hashed password & salt will then be output along with validation message.<br />

![Output](https://github.com/kodama96/DistributedSystems/blob/master/READMEimage/Screen%20Shot%202019-10-31%20at%2014.24.00.png)

## What it does
* Client makes a request to the server and awaits a response.
* Server makes a call to passwords class through PasswordServiceImpl which server then returns to client.
* Client then prompts the user and prints out values

# Part 2
[Part 2 specifications can be found here](https://learnonline.gmit.ie/pluginfile.php/130649/mod_assign/intro/Project2019_Part2.pdf) <br />
[SwaggerHub Link](https://app.swaggerhub.com/apis/gmit-sw/UserAPI/1)
### How to Run
#### Have the server from part 1 running
* Navigate into the .jar file directory *part1/target/* on command line <br /> 
* Run the following command: *java -jar passwordservice.jar* <br />
* This is running on port 50551 <br />

#### Run the following command to run the .jar file and server
* Navigate into the .jar file directory *part2/target/* on command line <br /> 
* Run the following command: *java -jar target/PasswordServiceWithgRPC-1.0-SNAPSHOT.jar server UserApiConfig.yaml* <br />
* This is running on port 9000

### Making the Requests
* Download Postman [here](getpostman.com/product/api-client) and open the app 
* List all users: **GET** http://localhost:9000/users/ <br />

* List a specific user by ID: **GET** http://localhost:9000/users/* <br />

* Delete a user: **DELETE** http://localhost:8080/users/*

* Create a new user: **POST** http://localhost:9000/users <br />

```
{
        "userID": 1,
        "userName": "example",
        "email": "example@gmail.com",
        "password": "example"
}
```

* Update a user by ID: **PUT** http://localhost:9000/users/*

```
{
        "userID": 1,
        "userName": "example",
        "email": "example@gmail.com",
        "password": "example"
}
```


### Resources
* https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
* https://github.com/john-french/artistAPI-dropwizard
• https://github.com/john-french/distributed-systems-labs/blob/master/grpc-async-inventory/README.md
* https://app.swaggerhub.com/help/tutorials/index
