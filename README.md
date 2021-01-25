1. 
Server's domain should be set in application.yml file before deploying the application on server.
Request's can be verified by `rest-api.http` file using IntelliJ.
To use

2. 
Java 15 - because it's my main language

Spring - most common and easy to use Java's web framework

Gradle - because I like it 

Lombok - to avoid boilerplate code

3. 
I would write endpoint that will take ship types and its position.
It is required to write a code that restricts number of ships and its types.

4.
changes in external application response - exception handling in http client, 
external application responds with 500 - implement retry mechanism to repeat request for specified number of times, 

