# "swiftcodes" Application User Manual 
This manual describes how to run the "swiftcodes" application, check the database, and use its API.
1. Project Setup
Open the "swiftcodes" project in your IntelliJ IDEA development environment. Then, run the application.
2. Accessing the H2 Database
To inspect the database contents, ensure you have the H2 database installed. Then, follow these steps:
a. Open your web browser and navigate to: http://localhost:8080/h2-console/login.do
b. Enter the following login credentials:
•	JDBC URL: jdbc:h2:mem:swiftcodedb
•	Login: sa
•	Password: password
c. Click the Connect button to establish a connection to the database.
3. Using the API with Postman
Interacting with the "swiftcodes" API is recommended using the Postman application, which should be installed on your computer.
a. Launch the Postman application.
b. Navigate to your online workspace at: https://web.postman.co/workspaces
c. You can now utilize the following API commands:
•	[GET] http://localhost:8080/v1/swift-codes/{swift-code}
o	Description: Returns information about a Bank based on the provided SWIFT code. If the SWIFT code belongs to a headquarters, it will also display information about all its branches.
o	Example: To retrieve information about the bank with the SWIFT code ABCDPLPWXXX, send a GET request to: http://localhost:8080/v1/swift-codes/ABCDPLPWXXX
•	[GET] http://localhost:8080/v1/swift-codes/country/{countryISO2code}
o	Description: Returns all SWIFT codes with details for a specific country (both headquarters and branches). The country code should be provided in the ISO 3166-1 alpha-2 standard (two-letter country code).
o	Example: To retrieve a list of all SWIFT codes for Poland (ISO 3166-1 alpha-2 code: PL), send a GET request to: http://localhost:8080/v1/swift-codes/country/PL
•	[POST] http://localhost:8080/v1/swift-codes
o	Description: Adds new SWIFT code entries to the database for a specific country.
o	Request Structure (Body - Raw - JSON): 
JSON
{
  "address": "string",
  "bankName": "string",
  "countryISO2": "string",
  "countryName": "string",
  "isHeadquarter": true/false,
  "swiftCode": "string"
}
o	Example: To add a new bank branch: 
JSON
{
  "address": "10 New Street, Warsaw",
  "bankName": "New Bank SA",
  "countryISO2": "PL",
  "countryName": "Poland",
  "isHeadquarter": false,
  "swiftCode": "NOWAPLPW001"
}
•	[DELETE] http://localhost:8080/v1/swift-codes/{swift-code}
o	Description: Deletes SWIFT code data if the provided SWIFT code matches an entry in the database.
o	Example: To delete information about the bank with the SWIFT code OLDAPLPWXXX, send a DELETE request to: http://localhost:8080/v1/swift-codes/OLDAPLPWXXX
Important Note: The API runs locally. Any changes made to the database via the API will be lost after the application is shut down.

