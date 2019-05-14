# Spring quickstart

Boilerplate project for rest API with spring with the following features

* Entities (JPA)
* Repositories (JPA)
* Services
* Controllers
* Tests with database setup and teardown
    * Repositories
    * Controllers (With spring MockMVC)

The packages under com.quick.start follow this pattern

* Application Scope
    * application
        * controllers (Application controllers)
    * domain
        * entities (System entities)
        * repositories (System repositories that manipulate the entities)
        * services (System services that deal with one repositories or 0-N services)
    * infrastructure
        * conf (Application configurations)
* Test Scope
    * controllers (For controller testing)
    * repositories (For repository testing)
    * utils (Helpers for setting the testing environment)

Both Functionality and Test Scope have the `resources` folder where the `application.properties` is kept for configuration. Under the test resources folder, there is de `datasets` folder, where the xml files are kept for setting up and tearing down the database in each test scope.

This project uses maven for it's build and you are free to use whatever you want to build the application. I was nice to put the tomcat7 plugin, so to run the application you can use

    mvn tomcat7:run 

on the backend folder and the application will boot on port 8080. the path of the application and the port can be configured on the pom.xml file.

To run the tests, you can run:

    mvn test

Remember to change the `application.properties` for both the application and test scope to math your needs.

It is recommended to keep the `jpa ddl-auto` configuration as `create-drop` for the test scope, but feel free to change as you need.
