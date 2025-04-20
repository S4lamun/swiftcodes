# "swiftcodes" Application User Manual

This manual describes how to run the **"swiftcodes"** application, check the database, and use its API.

---

## 1. Project Setup

Open the `swiftcodes` project in your IntelliJ IDEA development environment. Then, run the application.

---

## 2. Accessing the H2 Database

To inspect the database contents, ensure you have the H2 database installed. Then, follow these steps:

1. Open your web browser and navigate to:  
   [http://localhost:8080/h2-console/login.do](http://localhost:8080/h2-console/login.do)

2. Enter the following login credentials:
   - **JDBC URL**: `jdbc:h2:mem:swiftcodedb`
   - **Login**: `sa`
   - **Password**: `password`

3. Click the **Connect** button to establish a connection to the database.

---

## 3. Using the API with Postman

Interacting with the **"swiftcodes"** API is recommended using the [Postman](https://www.postman.com/downloads/) application.

1. Launch the Postman application.
2. Navigate to your workspace:  
   [https://web.postman.co/workspaces](https://web.postman.co/workspaces)
3. You can now use the following API commands:

---

### 🔹 `[GET] /v1/swift-codes/{swift-code}`

- **Description**: Returns information about a Bank based on the provided SWIFT code.  
  If it's a headquarters SWIFT code, it will also return info about all its branches.
- **Example**:  
  `GET http://localhost:8080/v1/swift-codes/ABCDPLPWXXX`

---

### 🔹 `[GET] /v1/swift-codes/country/{countryISO2code}`

- **Description**: Returns all SWIFT codes for a specific country (headquarters and branches).  
  The country code must follow ISO 3166-1 alpha-2 format.
- **Example**:  
  `GET http://localhost:8080/v1/swift-codes/country/PL`

---

### 🔹 `[POST] /v1/swift-codes`

- **Description**: Adds a new SWIFT code to the database.
- **Request Body** (raw JSON):
  
  ```json
  {
    "address": "string",
    "bankName": "string",
    "countryISO2": "string",
    "countryName": "string",
    "isHeadquarter": true,
    "swiftCode": "string"
  }


### 🔹 Running Tests

To run tests:

1. In IntelliJ IDEA, set the run configuration to **"Current File"**.
2. Navigate to the folder:  
   `src/test/org/example/swiftapi`
3. Select the test file you want to execute and run it.
