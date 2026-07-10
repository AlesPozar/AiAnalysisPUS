# 05 – AI komponenta

## Endpoint

```text
POST /analyses/{analysisId}/questions/{questionId}/ai/conversation
```

Frontend temu endpointu pošlje povzetke prejšnjih odgovorov in trenutno zgodovino sporočil za aktualno vprašanje.

Backend doda podatke o analizi, vsa vprašanja in trenutno vprašanje. Na podlagi tega sestavi prompt in ga pošlje Gemini API-ju.

## Request struktura

```json
{
  "previousResponses": [
    {
      "questionId": 1,
      "summary": "Uporabnik je povedal, da mu je bila všeč enostavna navigacija."
    }
  ],
  "messages": [
    {
      "sender": "AI",
      "messageText": "Kaj vam je bilo pri aplikaciji najbolj všeč?"
    },
    {
      "sender": "USER",
      "messageText": "Najbolj mi je bila všeč preprosta uporaba."
    }
  ]
}
```

## Response struktura

```json
{
  "state": "FOLLOWUP",
  "messageText": "Lahko navedete konkreten primer, kjer se vam je uporaba zdela preprosta?"
}
```

## stanja

### FOLLOWUP

AI postavi dodatno podvprašanje, kadar je odgovor uporaben, vendar še ni dovolj dober.

### EXPLANATION

AI razloži trenutno vprašanje.

### NEXT_QUESTION

AI oceni, da je odgovor na trenutno vprašanje dovolj dober in da se lahko pogovor premakne na naslednje vprašanje.

Pri stanju `NEXT_QUESTION` AI ne postavi naslednjega vprašanja. `messageText` predstavlja povzetek odgovora, ki naj bi ga frontend shrani v `previousResponses`. Ta se "summary" se ne shrani v bazo, TRENUTNO! To je razsiritev.

### DONE

AI oceni, da je analiza zaključena. Frontend lahko nato omogoči oddajo celotne seje.

## Vloga frontenda

Frontend vodi stanje pogovora med izpolnjevanjem analize. Za vsako vprašanje hrani AI sporočila, uporabnikova sporočila, trenutno vprašanje in povzetke prejšnjih odgovorov.

Ko AI vrne `NEXT_QUESTION`, frontend shrani povzetek in se premakne na naslednje vprašanje. Ko AI vrne `DONE`, frontend omogoči zaključek in pošlje celotno sejo na backend.

## Shranjevanje seje

Ko je analiza zaključena, frontend pošlje celotno sejo na endpoint:

```text
POST /analyses/{analysisId}/sessions/full
```

Backend nato shrani `analysis_session`, `question_response` in `response_message` zapise.
