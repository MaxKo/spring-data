User Management App
in order to run ap within docker please execute 
1. in user-address-management folder:
mvn package -DskipTests

2. in spring-data folder:
docker build -t user-management-img user-address-management

3. in user-address-management folder next command
docker-compose up 

try GET http://localhost:8080/api/users

have fun!
thanks!