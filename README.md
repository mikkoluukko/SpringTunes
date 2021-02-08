# Task 4: Fake Itunes
## About
The goal of this project was to demonstrate:
- Configuring REST endpoints with Spring.
- Using Thymeleaf to create template html pages with templated data.
- Publishing a Spring application to Heroku.
- Proper endpoint naming conventions .
- SQL queries combining JOIN, ORDERBY, GROUPBY, SUM, COUNT and MAX commands.
- Creating and running a Docker container.

The program uses a SQLite sample database called Chinook Database ([link to GitHub](https://github.com/lerocha/chinook-database)).

## Description
The project uses Spring Boot and Spring Initializr to create a web application.
The web application uses Thymeleaf as a templating engine.
The application also offers a separate REST API.

### Thymeleaf views
The application has two different Thymeleaf views.
1. The home page shows 5 random artists, 5 random songs and 5 random genres from the database.
The home page also contains a search bar which can be used to search for any song in the database.
2. The search results page shows the results for the search query that the user has made.
The result row includes the track name, artist, album and genre. The search is case insensitive.
   
### API endpoints
The API endpoints are accessed from /api/customers/ and cater to the following functionality:
- A GET request to /api/customers/ returns a list of all the customers in the database,
displaying their id, first name, last name, country, postal code, phone number and email.
- A POST request to /api/customers/new tries to add a new customer to the database.
All the fields (except `customerId` which is optional) are required for the customer to be accepted into the database.
Successful request returns the added customer in the response body while unsuccessful request returns the data that was attempted to be added as a new customer.
- A PUT request to /api/customers/id (with id being the actual customerId number of the specific customer) tries to update an existing customer.
The id number has to exist in the database and all the fields except `customerId` are required in order for the customer to be updated.
Successful request returns the updated customer in the response body while unsuccessful request returns the data that was attempted to be stored into the database in the update.
- A GET request to /api/customers/per-country returns a list of countries, ordered descending by the number of customers per country.
Each entry includes the name of the country and the number of customers in that country.
- A GET request to /api/customers/by-invoice-total returns a list of customers, ordered descending by their total invoice value.
Each entry includes the full name of the customer and their total invoice value.
- A GET request to /api/customers/id/popular/genre (with id being the actual customerId number of the specific customer) returns the specific customers most listened to genre(s).
In most cases the response will only include one genre but in case of a tie, all the genres that have the highest listened-to-count will be displayed.

## Demonstration
Finished application hosted on Heroku: [Spring Tunes](https://spring-tunes.herokuapp.com)




