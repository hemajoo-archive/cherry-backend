# Hemajoo Commerce - Cherry Solution

<hr>

# 1 - Description

<hr>

The `cherry` solution is a **commercial** solution implemented with the Java & Spring technologies used to provide a complete platform deployed in the cloud
that allow to create and manage virtual shops and companies for selling any kind of product and/or service.

The main components of the **Cherry** solution are:

- `cherry-backend` provides a framework to manage the backend of the solution with low-level services
- `cherry-microservice` provides a set of microservices to manage the data by topic
- `cherry-web` provides a UI application for owners to manage their virtual shop/company  
- `cherry-mobile` provides a UI application for end users on mobile to access the stores/companies and buy products/services 
- `cherry-admin` provides a UI application to ease the administration of the whole **Cherry** platform and solution

**Note:**<br>
Other components/applications may be implemented in the future according to the needs. 
 
# 2 - Architecture

<hr>

## 2.1 - Cherry Backend (cherry-backend)

The `cherry-backend` initiative provides a library/framework with low-level entities and services used to manage the data of the whole solution. It is implemented as a `Maven` multi-modules `JPA` application.

It is composed of the following modules:
As each module is supposed to deal directly with the database, it defines a set of JPA entities that are only intended to be used on the persistence side.

That is why a `shared` version of the JPA entities have been designed and implemented and are supposed to be used by clients of the persistence modules (typically microservices and by transition also by UI applications).  

- `cherry-backend-commons`
- `cherry-backend-shared` *** 
- `cherry-backend-persistence-commons`
- `cherry-backend-persistence-document`
- `cherry-backend-persistence-person`
- `cherry-backend-persistence-account`
- `cherry-backend-persistence-organization`
- `cherry-backend-persistence-account`
- `cherry-backend-persistence-billing`
- `cherry-backend-persistence-catalog`
- `cherry-backend-persistence-calendar`
- `cherry-backend-persistence-product`
- `cherry-backend-persistence-service`
- `cherry-backend-persistence-. . .`
- `cherry-backend-rest` ***
- `cherry-backend-coverage`

The typical structure for a persistence module contains, at least, the following set of packages:

- `domain.entity.server` provides server (JPA) entities interfaces, abstract and concrete classes 
- `domain.entity.client` provides client (shared) entities interfaces, abstract and concrete classes 
- `domain.transform.converter` provides services to convert from server to client entities (and vice versa) 
- `domain.transform.mapper` providing services to map from server to client entities (and vice versa) 
- `domain.randomizer` provides services to randomly generate server and/or client entities 
- `domain.repository` provides services access the underlying JPA repository 
- `domain.service` provides services to interact with the underlying data model entities 
- `domain.validation` provides services to validate server and client entities 

Note that `domain` should be replaced by the real domain name such as `person` or `organization`.<br>
For example, the `person` domain will contain all entities for:

- `person`
- `email address`
- `postal address`
- `phone number`

**Note(s):**<br>
An *email address*, a *postal address* or a *phone number* can only be attached to a *person* meaning that, for attaching a *postal address* or a *phone number* to a company, 
you first need to attach a *person* to this company. You will then be able to attach a *postal address* to the *person*.  

## 2.2 - Cherry Microservice (cherry-microservice)

The `cherry-microservice` initiative provides a set of Java/Spring projects providing microservices with specialized services for each domain such as (example list):

- `cherry-microservice-person` microservice providing services to manage **persons**
- `cherry-microservice-account` microservice providing services to manage **accounts**
- `cherry-microservice-organization` microservice providing services to manage **organizations**
- `cherry-microservice-company` microservice providing services to manage a **company**
- `cherry-microservice-shop` microservice providing services to manage **shops**
- `cherry-microservice-calendar` microservice providing services to manage **calendars**
- `cherry-microservice-catalog` microservice providing services to manage a **catalog** of products/services
- `cherry-microservice-customer` microservice providing services to manage **customers**
- `cherry-microservice-product` microservice providing services to manage **products** of a catalog
- `cherry-microservice-service` microservice providing services to manage **services** of a catalog
- `cherry-microservice-cart` microservice providing services to manage **carts**
- `cherry-microservice-billing` microservice providing services to manage the **billing** part of a shop
- . . . 

Each microservice is a **Spring Boot** application and exposes a set of **REST APIs** to allow client applications to interact with it.


## 2.3 - Cherry Web (cherry-web)

. . . 

## 2.4 - Cherry Mobile (cherry-mobile)

. . .

## 2.5 - Cherry Admin (cherry-admin)

. . .


![Cherry Backend - Architecture Diagram](./doc/architecture.png)

# 3 - Infrastructure

<hr>

## 3.1 - Code

<hr>

The **GitHub** platform is used to store the project's source code, as a continuous development and continuous integration platform.

### See: [Cherry Backend on GitHub](./doc/github.md)

## 3.2 - Sonar

<hr>

The **Sonar** platform is used to execute SAT (Static Code Analysis) to measure:

- Code quality
  - readability
  - security
  - maintainability
  - code coverage

### See: [Cherry Backend on SonarQube](https://sonarqube.k8s-dam-dev-a19fe168445bd781eeb95cecb0df6e59-0000.us-south.containers.appdomain.cloud/dashboard?id=pims-service-gsar) for analysis and reports on the project's code.
### See: [Official SonarQube documentation](https://www.sonarqube.org/features/multi-languages/?gads_campaign=Europe4-SonarQube&gads_ad_group=Multi-Language&gads_keyword=c%20sonarqube&gclid=CjwKCAjwy_aUBhACEiwA2IHHQBw5zf29UknQkfp8n5l1xVG-bOoxFDK63rgpNMdXmnKvV2BrqxwVHRoCX5QQAvD_BwE) to have detailed information on the **SonarQube** product.


## 3.3 - Cloud Provider

<hr>

**Cirrus** is the **IBM Hybrid Cloud** where the `pims-service-gsar` application is deployed.

### See: [Cherry Backend on the Cloud](./doc/cloud.md)

## 3.4 - External system(s)

<hr>

### See: [Cherry Backend - External Systems](./doc/external_systems.md)

## 3.5 - Resource(s)

<hr>

### See: [Cherry Backend - Resources](./doc/resources.md)


# 4 - Code Documentation

<hr>

### See: [Cherry Backend - Code documentation](./doc/code.md)

### See: [Cherry Backend - Maven usage](./doc/maven.md)


# 5 - Release history

<hr>

To have details about the available versions of `pims-service-gsar` and know which one is the latest one, have a look at the release history.

### See: [Cherry Backend - Release history](./doc/history.md)

# 6 - Notes

<hr>

# 7 - Links

<hr>
