# job-finder
A job search and application service using Spring MVC, Hibernate and JSTL

To run this application locally: 

1. Create an instance of MySQL server running on localhost port 3306 (default). 
   Create a user with credentials- username: root, password: root.
   Create a database / schema named jobfinder.
   ( create schema jobfinder )

2. Package the project into a war
   mvn package

3. Copy the war file into tomcat webapps directory.

4. Start tomcat. It will serve requests on port 8080 (default).

5. Go to localhost:8080/myjobfinder
