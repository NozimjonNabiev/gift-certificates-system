# Gift Certificates System

Welcome to the Gift Certificates System, facilitating the creation, management, and retrieval of gift certificates and tags. This project simplifies the complexities of certificate operations and offers seamless integration.

## Getting Started

1. **Clone the Repository**: Begin by cloning this repository locally.
2. **Database Configuration**: Adjust the PostgreSQL dialects within the [`main`](repository/src) directory using `ddl.sql` and `init_database.sql` from [`database`](repository/src/main/resources/database).
3. **Application Settings**: Customize [`application.properties`](repository/src/main/resources/application.properties) to match your database configuration. Additionally, add a new Tomcat 10 configuration.
4. **Postman Installation**: Install Postman to facilitate API testing and interaction.
5. **System Requirements**: Ensure WSL2 and Docker are installed and running on your machine.

## Operations Overview

### Certificate Operations

- **Fetch All Certificates**: `GET /gift-certificates`
- **Retrieve a Single Certificate**: `GET /gift-certificates/{id}`
- **Find Certificates by Tag**: `POST /gift-certificates/tag`
- **Filter Certificates with a Search**: `POST /gift-certificates/search`
- **Sort Certificates**: `POST /gift-certificates/sort`
- **Create a Certificate**: `POST /gift-certificates`
- **Update a Certificate**: `PATCH /gift-certificates`
- **Delete a Certificate**: `DELETE /gift-certificates/{id}`

### Tag Operations

- **Get All Tags**: `GET /tags`
- **Fetch a Single Tag**: `GET /tags/{id}`
- **Create a Tag**: `POST /tags`
- **Delete a Tag**: `DELETE /tags/{id}`

## Certificate & Tag Specifications

### Certificate Specifications

- **id**: Long (Required)
- **name**: String (5 to 50 characters)
- **description**: String (10 to 200 characters)
- **price**: Double 
- **duration**: Integer
- **createDate**, **lastUpdateDate**: String (ISO 8601 format)
- **tags**: Array (Empty or containing tag arrays)

### Tag Specifications

- **id**: Long (Required)
- **name**: String (3 to 30 characters)

### Business Requirements

Develop a web service for the Gift Certificates system with the following entities (many-to-many):

![Many-to-Many Relationship](https://learn.epam.com/static/files/d4258d8d-c790-4b58-a306-91b5afb7029e)

- **Gift Certificate Entities**:
  - **CreateDate** and **LastUpdateDate**: Format ISO 8601 ([ISO 8601 format example](https://en.wikipedia.org/wiki/ISO_8601)). Example: `2018-08-29T06:12:15.156`.
  - **Duration**: Expressed in days (expiration period).

The system should expose REST APIs to perform the following operations:

1. **GiftCertificate Operations**:
   - CRUD operations for GiftCertificates. 
   - Creation or modification of new tags in the DB if passed during creation/modification. 
   - Update only fields specified in the request during modification. Others should not be updated. Batch insert is out of scope.

2. **Tag Operations**:
   - CRD (Create, Retrieve, Delete) operations for Tags.

3. **Additional Operations**:
   - Retrieve certificates with tags (optional parameters can be used together):
     - By tag name (single tag).
     - Search by part of name/description (can be implemented using DB function call).
     - Sort by date or name in ASC/DESC order (bonus task: implement the ability to apply both sort types simultaneously).

### Application Requirements

- **JDK version**: 8, utilizing Streams, java.time.*, etc. where possible. (JDK version increase possible in agreement with coordinators)
- **Application Packages Root**: com.epam.esm
- **Connection Pool**: Any widely-used connection pool.
- **Data Access**: JDBC / Spring JDBC Template.
- **Transactions**: Use where necessary.
- **Code Convention**: Follow Java Code Convention (Exception: margin size – 120 chars).
- **Build Tool**: Maven/Gradle (latest version) for a multi-module project.
- **Web Server**: Apache Tomcat/Jetty.
- **Application Container**: Spring IoC. Spring Framework (latest version).
- **Database**: PostgreSQL/MySQL (latest version).
- **Testing**: JUnit 5.+, Mockito.
- **Testing Coverage**: Service layer with unit tests not less than 80%. Repository layer with integration tests using an in-memory embedded database (all operations with certificates).

### General Requirements

- Clean code without "developer-purpose" constructions.
- Developed respecting OOD and SOLID principles.
- Valuable comments where appropriate.
- Documented public APIs (Javadoc).
- Clear layered structure with defined responsibilities for each application layer.
- Utilize JSON as the format for client-server communication messages.
- Implement a convenient error/exception handling mechanism, ensuring meaningful and localized errors on the backend side.
