# User-APIs

The exposed APIs allow creating users. Once logged in, the user can read/update/delete the user's information. Written in Spring Boot Java, h2 database and Swagger for documentation.

## You can see all the end points [here](https://yw-user-apis.herokuapp.com/api/swagger-ui.html#/)

<image src="assets/user_apis_snapshot.png">

## Summary
2.24.20

I wrote these APIs and published to Heroku while going over Spring-Boot material.
This project covers a lot of different aspects of a Full Stack developer's responsibilities, including:

### Back End
Authorization Header (Bearer)
Deployment to Heroku  
h2 database 
Swagger documentation  
  
##  Installation Instructions (Check out Swagger-UI for Endpoints documentation)

1. Download Repository  
2. In command line, cd to root directory and type: mvn install -DskipTests  
3. next: mvn spring-boot:run  
4. Using Postman or any other API Developemnt Platform: create a user  
5. Login using user credentials  
6. Copy Bearer from response header  
7. Use bearer in all other APIs  


## Author

* **Jonathan Hirshowitz** - *Full-Stack Software Developer* - [Website](https://jonathan-hirshowitz-portfolio.firebaseapp.com/) | [LinkedIn](https://www.linkedin.com/in/jonathan-hirshowitz/)
