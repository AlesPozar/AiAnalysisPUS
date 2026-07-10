# 03 – Podatkovni model

## Vse entitete

- `analysis`,
- `question`,
- `analysis_session`,
- `question_response`,
- `response_message`.

## analysis

Tabela `analysis` predstavlja eno analizo oziroma vprašalnik.

Polja:

- `id`,
- `title`,
- `description`,
- `public_code` - uporablja pri povezavah, unikaten za vsako analizo,
- `created_at`.

## question

Tabela `question` predstavlja vprašanje znotraj analize.

Polja:

- `id`,
- `analysis_id`,
- `question_text`,
- `position` - katero bo na vrsti,
- `created_at`.

Constraint `analysis_id + position` par mora biti unikaten.

## analysis_session

Tabela `analysis_session` predstavlja eno izpolnjeno analizo.

Polja:

- `id`,
- `analysis_id`,
- `respondent`,
- `completed_at`.

## question_response

Tabela `question_response` predstavlja pogovor za eno glavno vprašanje.

Polja:

- `id`,
- `session_id`,
- `question_id`.

## response_message

Tabela `response_message` predstavlja posamezno sporočilo v pogovoru za neko vprašanje.

Polja:

- `id`,
- `question_response_id`,
- `sender` - AI ali uporabnik (`USER` ali `AI`),
- `message_text`,
- `position` - po vrsti katero je bilo, za kasnejše sortiranje,
- `created_at`.


## Relacije

```text
analysis 1 -> N question
analysis 1 -> N analysis_session
analysis_session 1 -> N question_response
question 1 -> N question_response
question_response 1 -> N response_message
```

## Cascade delete

Projekt uporablja database cascade delete prek Liquibase migracij.
