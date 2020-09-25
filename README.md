# VCS
VCS Project

#################
##Project Setup##
#################

Run following commands inside folder /frontend.
This is setup run time environment for React.
1. npm install
2. npm install axios
3. npm install http-proxy-middleware

Build the Spring Boot application with "maven clean install"

############
##DB setup##
############

This application uses MySql DB. Make sure the MySql server is up and running
before application start.


Perform following changes in application.properties file as setup in your env:

1. spring.datasource.url=
2. spring.datasource.username=
3. spring.datasource.password=

Starting the application:
1. Start MySql server
2. String Spring boot application
3. start UI by going to /frontend and run cmd "npm start"


