# Task Management Application

## Overview

This is a **Task Management Application** built using **Spring Boot** and **Spring Security**. The application allows users to manage tasks, including features like **task creation, updating, deleting, and filtering tasks**. It also incorporates **JWT-based authentication** and **role-based authorization**.

## Requirements

- **Java 17 or higher**
- **MySQL Database** (or any compatible SQL database)
- **Maven** (for building the project)
- **Postman** (for testing the APIs)
- **IDE** (e.g., IntelliJ IDEA, Eclipse)

## Getting Started

Follow these steps to get the solution running on your local machine:

# Clone the Repository

### Configure Database Connection (MySQ)
# create db called task_management 
# change those with ur credential user & pass  in application.properties file :
spring.datasource.username=root
spring.datasource.password=ahmedshamaa


# Email Configuration (google smtp)
### edit those also in application.properties file with yours
spring.mail.username=
spring.mail.password=

# u can run it now .

# For testing use json file to impot it in post man .

# spring automatically will create tables but if u need it manual make it with file .sql also that i upload it in repo.

