#MyRetail documentation

##Stack
Framework: Spring Boot Version: 2.3.2
Hosting: AWS Beanstalk
DB: MongoDB managed cluster

###Prerequisites to run locally
* mongodb
* Java 8

###Configuration
To run the application locally and have connect to your local
database, you need to update the database connection property (spring.data.mongodb.uri)
in application.properties file. 

You also need to have mongodb installed locally. 

###Prerequisites to run deployed version
* Postman or A browser such as firefox, chrome, or edge
* Internet connection. 


### API Requests
The API makes use of swagger for documentation. 
Please see <a href="http://targetassignment-env.eba-89qmitrp.us-east-1.elasticbeanstalk.com/swagger-ui.html"> Swagger API documentation</a>

You may import the postman collection `TargetMyRetail.postman_collection.json`
into postman to test the API endpoint. 
* The only endpoint that is open is the /products endpoint. 
* It supports the following http methods: GET, POST, PUT, and DELETE
http requests. 
* You may access the deployed version by making requests to http://targetassignment-env.eba-89qmitrp.us-east-1.elasticbeanstalk.com/api/v1/products
The url is already included in the provided postman collection.


####Note: 
If you run the application locally then you have to modify the postman collection url to match your running instance url






