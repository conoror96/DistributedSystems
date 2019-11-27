# DistributedSystems
Project for Distributed Systems. A password service that provides hashing and verification services which exposes a gRPC API. <br />
Developed with Maven and IntelliJ <br />

GitHub Repo: https://github.com/kodama96/DistributedSystems

# Part 1
[Part 1 specifications can be found here](https://learnonline.gmit.ie/pluginfile.php/119965/mod_assign/intro/Project2019_Part1.pdf)
## Running the code
###  Running the server
* Navigate into the .jar file directory */DistributedSystems/project/target/* on command line <br /> 
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
[Part 2 specifications can be found here](https://learnonline.gmit.ie/pluginfile.php/130649/mod_assign/intro/Project2019_Part2.pdf)

## How to Run
### Server
java -jar target/PasswordServiceWithgRPC-1.0-SNAPSHOT.jar server UserApiConfig.yaml

### Localhost
http://localhost:9000/users

### Resources
* https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
* https://github.com/john-french/artistAPI-dropwizard
