#MyRetail documentation

##Stack
Framework: Spring Boot Version: 2.3.2
Hosting: AWS Beanstalk
DB: MongoDB managed cluster

###Prerequisites to run locally
* mongodb
* Java 8

###Configuration
To run the application locally and have it connect to your local
database, you need to update the database connection property (spring.data.mongodb.uri)
in application.properties file. 

### API Requests
* The only endpoint that is open is the /products endpoint. 
* It supports the following http methods: GET, POST, PUT, and DELETE
http requests. 
* You may access it making requests to http://targetassignment-env.eba-89qmitrp.us-east-1.elasticbeanstalk.com/api/v1/products







