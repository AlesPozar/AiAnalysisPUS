# 02 – Arhitektura projekta

## Pregled arhitekture

Backend je zasnovan kot Maven multi-module projekt. Projekt je razdeljen na tri glavne module:

```text
api
service
entity
```

Odvisnosti med moduli so urejene v eni smeri:

```text
api -> service -> entity

oz.
     api
|------------|
|   service  |
| |--------| |
| | entity | |
```

`api` modul vsebuje REST endpoint-e in DTO objekte, `service` modul vsebuje poslovno logiko, repozitorije in dto kopije, `entity` modul pa vsebuje JPA entitete in podatkovni model.

## api modul

`api` modul vsebuje REST resource razrede.

In sicer:

- `AnalysisResource`,
- `QuestionResource`,
- `AnalysisSessionResource`,
- `AiConversationResource`.

Ima tudi DTO razrede za omenjene resource razrede.

## service modul

`service` modul vsebuje poslovno logiko aplikacije. Tu se izvajajo validacije, transakcije, delo z repozitoriji in komunikacija z zunanjimi sistemi.

V tem modulu so tudi repozitoriji za delo z bazo. AI integracija za inteligentno komponento je tudi del service modula, saj predstavlja poslovno logiko pogovora.

## entity modul

`entity` modul vsebuje JPA entitete, ki predstavljajo tabele v podatkovni bazi.

Odrazajo se iz liquidbase migracij.

## Potek zahteve skozi sistem

Primer ustvarjanja analize:

```text
Frontend
  -> POST /analyses
    -> AnalysisResource
      -> AnalysisService
        -> AnalysisRepository
          -> PostgreSQL
            -> Entity
```

Za inteligentno komponento:

```text
Frontend
  -> POST /analyses/{analysisId}/questions/{questionId}/ai/conversation
    -> AiConversationResource
      -> AiConversationService
        -> GeminiClient
          -> Gemini API
```

Uporabljam tudi transakcije `@Transactional`, znotraj `service` modula.
