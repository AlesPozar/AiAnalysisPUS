package com.ales.aianalysis.service.ai;

public enum AiConversationState {
    NEXT_QUESTION, // odgovor je dovolj dober, gremo naprej
    FOLLOWUP, // AI postavi dodatno podvprašanje
    EXPLANATION, // AI dodatno pojasni trenutno vprašanje ali podvprašanje
    DONE // celotna analiza je zakljucena
}
