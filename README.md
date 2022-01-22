## Test Postgres Database

Use docker to start a test database:

```
docker run --name postgres-spring -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:alpine
```

```
psql -U postgres;
CREATE DATABASE springbootpostgresdb;

\d springbootpostgresdb

CREATE EXTENSION "uuid-ossp";
SELECT uuid_generate_v4();
```