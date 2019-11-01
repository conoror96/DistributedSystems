# DistributedSystems
Project for Distributed Systems. A password service that provides hashing and verification services which exposes a gRPC API. <br />
Developed with Maven and IntelliJ <br />

GitHub Repo: https://github.com/kodama96/DistributedSystems

# Part 1
[Part 1 specifications can be found here](https://learnonline.gmit.ie/pluginfile.php/119965/mod_assign/intro/Project2019_Part1.pdf)
## Running the code
###  Running the server

### Note:
I have two different .jar files generated two different ways. Both are included in the .zip folder for submission as I wasnt sure which one to add. They both run the server on port 50551. <br />
* The first one is *project/target/PasswordService.jar* in the project directory <br />
* The second one is *project/out/artifacts/project_jar/project.jar*  in the project directory.

### Option 1
* Navigate into the .jar file directory */DistributedSystems/project/target/* on command line <br /> 
* Run the following command: *java -jar passwordservice.jar* <br />
### Option 2
* Navigate into the .jar file directory *DistributedSystems/project/out/artifacts/project_jar/project.jar* on command line <br /> 
* Run the following command: *project/out/artifacts/project_jar/project.jar*

### Running the client
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
