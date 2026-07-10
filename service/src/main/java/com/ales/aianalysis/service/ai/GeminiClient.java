package com.ales.aianalysis.service.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperties;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;


//TO DO, preveri se enkrat in komentriraj kasneje, da si zapomnis

@ApplicationScoped
public class GeminiClient {
    
    //namesto DTOjev
    @Inject
    ObjectMapper objectMapper;

    //da dobimo iz properties v /api modulu
    @ConfigProperty(name = "gemini.api.key")
    String apiKey;

    @ConfigProperty(name = "gemini.model")
    String model;

    //za fallback setup, ker tisti gemini-3.1-flash je velikokrat nedostopen, sicer pa zato malo slbše deluje
    @ConfigProperty(name = "gemini.fallback-model", defaultValue = "gemini-3.1-flash-lite")
    String fallbackModel;


    public String generateText(String prompt){
        try{
            return generateTextWithModel(prompt, model);
        }
        catch (GeminiApiException primaryException) {
            if (!isRetryable(primaryException.statusCode)) {
                throw new RuntimeException(
                        "Error occurred while generating text with Gemini API: " + primaryException.getMessage(),
                        primaryException
                );
            }

            try {
                return generateTextWithModel(prompt, fallbackModel);
            } catch (Exception fallbackException) {
                throw new RuntimeException(
                        "Primary Gemini model " + model + " failed: " + primaryException.getMessage()
                                + "; fallback model " + fallbackModel + " failed: " + fallbackException.getMessage(),
                        fallbackException
                );
            }
        }
        catch (Exception e) {
            throw new RuntimeException(
                    "Error occurred while generating text with Gemini API: " + e.getMessage(),
                    e
            );
        }
    }

    private String generateTextWithModel(String prompt, String modelName) throws Exception {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/" + modelName + ":generateContent";

            /*
            {
                "contents": [
                    {
                    "parts": [
                        {
                        "text": "TUKAJ JE TVOJ PROMPT"
                        }
                    ]
                    }
                ]
            }
            */
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of(
                        "parts", List.of(
                            Map.of(
                                "text", prompt
                            )
                        )
                    )
                )
            );

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json").header("x-goog-api-key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();
            
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new GeminiApiException(response.statusCode(), response.body());
            }
            return extractTextFromResponse(response.body());
    }

    private boolean isRetryable(int statusCode) {
        return statusCode == 429 || statusCode == 500 || statusCode == 503 || statusCode == 504;
    }

    private static class GeminiApiException extends IllegalStateException {
        final int statusCode;

        GeminiApiException(int statusCode, String responseBody) {
            super("Gemini API error. Status: " + statusCode + ", body: " + responseBody);
            this.statusCode = statusCode;
        }
    }


    private String extractTextFromResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            JsonNode textNode = root
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.asText().isBlank())
                throw new IllegalStateException("Gemini response did not contain text: " + responseBody);

            return textNode.asText();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to parse Gemini response: " + responseBody, exception);
        }
    }
}

