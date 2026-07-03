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


    public String generateText(String prompt){
        try{
            String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent";

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
                throw new IllegalStateException(
                        "Gemini API error. Status: " + response.statusCode() + ", body: " + response.body()
                );
            }
            return extractTextFromResponse(response.body());
        }
        catch (Exception e) {
            throw new RuntimeException("Error occurred while generating text with Gemini API", e);
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

