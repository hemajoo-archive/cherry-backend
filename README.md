# Cherry

The **Cherry** project is a Java full-stack solution providing a complete backend and client applications to buy and sell any kind of products 
or services. It is built as a microservices' solution directly deployable on the cloud. 

## 1. Project description

<hr>

The **Cherry Backend** project is composed by 5 different modules.

| Artifact      | Description                                                                                   |
|:--------------|:----------------------------------------------------------------------------------------------|
| `parent`      | Artifact used for the whole **Maven** build.                                                  |
| `commons`     | Artifact providing **common** entities to be used by the client applications.                 |
| `shared`      | Artifact containing the shared **data model** entities to be used by the client applications. |
| `persistence` | Artifact containing the **server** entities (data-model, repositories, etc.).                 |
| `rest`        | Artifact containing the **REST** controllers that can be used by the client applications.     |


## 2. Module information

<hr>

### 2.1 Cherry Backend - Commons

### 2.2 Cherry Backend - Shared

### 2.3 Cherry Backend - Persistence

### 2.4 Cherry Backend - REST

To access the Swagger UI exposing the REST controllers, visit: https://localhost:8084/swagger-ui/





# 3 - Databases
<hr>

## 3.1 - Generate the local database

To generate or re-generate the local database, ensure a local **Docker** daemon is running and that you have a container for the target database running and accessible.

Select a database profile under the `Maven` view of the project. The following profiles are available:

- `cherry-postgres-dev` - Cherry **PostgreSQL** development database. 
- `cherry-postgres-sit` - Cherry **PostgreSQL** system integration tests database.
- `cherry-postgres-uat` - Cherry **PostgreSQL** user acceptance tests database.
- `cherry-postgres-prd` - Cherry **PostgreSQL** production database.
- `cherry-h2-dev` - Cherry **H2** development database.
- `cherry-h2-sit` - Cherry **H2** system integration tests database.
- `cherry-h2-uat` - Cherry **H2** user acceptance tests database.
- `cherry-h2-prd` - Cherry **H2** production database.

### 3.1.1 - PostgresSQL database

Under the `cherry-backend-persistence` Maven project, go to the `Plugins` section and locate the `flyway` plugin.

Execute the following goal for:

- `flyway:clean` - to clean the local database according to the select database profile.
- `flyway:validate` - to validate the local database according to the select database profile.
- `flyway:info` - to retrieve information on the local database according to the select database profile.
- `flyway:migrate` - to migrate the database changes on the local database according to the select database profile.

### <u>Example</u>

If you want to re-generate the local database (due to some changes in the database structure), you will have to execute the following goals:

- `flyway:clean` 
- `flyway:migrate` 

After connecting to the local database, you should be able to see the following tables:

- `dummy`
- `flyway_schema_history`
 
If now you execute the `CherryBackendRestSpringBootApplication` from the `cherry-backend-rest` project, all the **Cherry** tables will be created and initialized.  
