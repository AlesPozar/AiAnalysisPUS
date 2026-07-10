# 06 – Navodila za zagon

## Zahteve

- Java 17,
- Maven,
- Docker,
- PostgreSQL prek Docker Compose,
- nastavljen bresplačni Gemini API key.


## Zagon podatkovne baze

V root mapi projekta:

```
docker compose up -d
```

Preverjanje logov:

```
docker logs ai-analysis-postgres
```

Povezava v PostgreSQL container:

```
docker exec -it ai-analysis-postgres psql -U user -d ai_analysis_db
```

## Nastavitev .env spremenljivke za Gemini

V PowerShellu nastavi:

```
$env:GEMINI_API_KEY="tvoj_api_key"
```

## Build projekta

V root mapi projekta:

```
mvn clean compile
```

## Zagon Quarkus dev mode

```
mvn -pl api -am io.quarkus.platform:quarkus-maven-plugin:3.37.0:dev
```

## Swagger UI

```
http://localhost:xxxx/q/swagger-ui
```

## Liquibase migracije

Liquibase migracije se izvedejo avtomatsko ob zagonu aplikacije.

```
quarkus.liquibase.migrate-at-start=true
quarkus.liquibase.change-log=db/changelog/db.changelog-master.xml
quarkus.hibernate-orm.schema-management.strategy=none
```

Strukturo baze upravlja Liquibase, ne Hibernate.