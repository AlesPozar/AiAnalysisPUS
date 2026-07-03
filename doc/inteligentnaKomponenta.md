# Inteligentna komponenta za AI voden pogovor

## Cilj

Backend inteligentna komponenta ki vodi pogovorno analizo z Gemini AI modelom.

AI na podlagi trenutnega pogovora in konteksta analize odloči, kaj se zgodi naprej

- `NEXT_QUESTION` — odgovor je dovolj dober, frontend gre na naslednje vprašanje
- `FOLLOWUP` — AI postavi dodatno podvprašanje
- `EXPLANATION` — AI dodatno pojasni trenutno vprašanje ali podvprašanje
- `DONE` — celotna analizaintervju je zaključen

---

## AI flow

Frontend med reševanjem hrani trenutni pogovor v svojem state-u.

Za vsako vprašanje frontend pošlje backendu

- osnovni kontekst analize
- seznam vseh vprašanj
- kratke povzetke zaključenih odgovorov
- celoten pogovor za trenutno vprašanje

Backend pol pokliče AI model, ki vrne odločitev frontend-u.

Na koncu se celoten pogovor pošlje in shrani v bazo.

---

## Endpoint

```txt
POST analyses>/{analysisId}/questions/{questionId}/ai/conversation
```


## TODO
- nadaljuj v service za ai, flow/path je ze ucrtan