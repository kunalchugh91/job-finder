# job-finder
A job search and application service using Spring MVC, Hibernate and JSTL

To run this application locally:

1. Create an instance of MySQL server running on localhost port 3306 (default).

2. Open and run the MySQLDatabaseSetup.sql file

3. Package the project into a war.
   (mvn package)

4. Copy the war file into tomcat webapps directory.

5. Start tomcat. It will serve requests on port 8080 (default).

6. Go to localhost:8080/myjobfinder