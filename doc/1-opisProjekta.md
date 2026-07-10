# 01 – Opis projekta

## Kratek opis

Projekt je vmesnik za ustvarjanje in izvajanje AI-vodenih analiz. Uporabnik ustvarji analizo z naslovom, opisom in seznamom vprašanj. Drugi uporabniki pa nato na vprašanja odgovarjajo skozi pogovorni vmesnik, kjer mu umetna inteligenca pomaga z dodatnimi podvprašanji, razlagami in usmerjanjem pogovora. Prav tako lahko vprašanja prilagaja glede na jezik uporabnika.

Glavna ideja projekta bi bila izboljšanje klasičnih anket tako, da odgovarjanje ni omejeno samo na statična vprašanja, ampak poteka bolj naravno, podobno pogovoru. AI lahko oceni, ali je odgovor dovolj dober, ali je potrebno dodatno vprašanje, ali uporabnik potrebuje razlago in podobno.

## Cilji projekta

Glavni cilji projekta so:

- omogočiti ustvarjanje analiz,
- omogočiti dodajanje vprašanj analizi,
- omogočiti AI-voden pogovor za posamezno vprašanje,
- shraniti zaključene seje z odgovori,
- omogočiti pregled oddanih sej,
- omogočiti urejanje in brisanje analiz ter vprašanj,
- uporabiti predlagano večnivojsko arhitekturo backend sistema,
- uporabiti PostgreSQL, JPA/Hibernate in Liquibase,
- pripraviti REST API z dokumentacijo v Swagger/OpenAPI.
- ...

## Uporabljene tehnologije

Backend:

- Java 17,
- Quarkus,
- Maven multi-module projekt,
- Jakarta REST,
- Hibernate ORM / JPA,
- PostgreSQL,
- Liquibase,
- Jackson,
- OpenAPI / Swagger UI,
- Gemini API za AI del in
- Docker za lokalno podatkovno bazo.

Frontend:

- Angular,
- Tailwind CSS in
- Angular Material.

Frontend je prisoten v drugem repozitoriju.