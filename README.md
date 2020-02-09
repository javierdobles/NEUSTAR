# NEUSTAR

## Description

This markdown is to guide the user in the building and deployment of the tool.



## Requirements

 - Java 1.8
 - Gradle 6.0.1

## Installation


### Config modification

for the usage of the application, you need to provide your ssh credentials, you need to: ${PROJECT_ROOT_DIRECTORY}/src/main/resources/ssh.properties and modify the credentials with your credentials, after that, the jar will use these credentials to connect to any box you require.

Example: 

    com.neustar.ssh.port=22
    com.neustar.ssh.username=your-user
    com.neustar.ssh.password=password

after the above step is completed, please proceed with the below steps. 

### Building
get into the root directory of the project and type the next command:

-   `gradle build`

if the build fail, you need to first format the code with the next command:

-   `gradle spotlessApply`

after the process above is completed, you should be able to get the jar file in the  **build/libs**  directory with the name of **app.jar**

### Running test integration

if you want to only run the test integration:

-   `gradle test`

the report of the test you'll find it in ${PROJECT_ROOT_DIRECTORY}/build/reports/tests/test/index.html
open the index.html to review the report.

### Clean up of directory

if you want to clean up the project, type the next command in the root directory of the project:

-   `gradle clean`

at this point, the application installation is now completed

## Usage


for the usage of the application, you need to get the **app.jar** mentioned in the above steps and run the command:

    java -jar app.jar 34.203.33.78,100.26.255.64,100.25.143.64

this will execute the application to run all the tasks:

 - Get all the process
 - Get top 3 of the cpu intensive process
 - Get top 3 of the memory intensive process
 - Get disk capacity in machine format
 - Get disk capacity in human readable format
 - Export all the output into user home as pdf, each pdf name will have the ip as the name.
