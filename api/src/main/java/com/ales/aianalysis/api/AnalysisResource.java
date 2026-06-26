package com.ales.aianalysis.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import com.ales.aianalysis.api.dto.AnalysisResponse;
import com.ales.aianalysis.api.dto.CreateAnalysisRequest;

import com.ales.aianalysis.service.AnalysisService;

@Path("/analyses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class AnalysisResource {

    @Inject
    AnalysisService analysisService;

    @POST
    public AnalysisResponse createAnalysis(CreateAnalysisRequest request) {
        return AnalysisResponse.fromEntity(
            analysisService.createAnalysis(request.title, request.description)
        );
    }

    @GET
    public List<AnalysisResponse> getAllAnalyses() {
        return analysisService.getAllAnalyses()
            .stream()
            .map(AnalysisResponse::fromEntity)
            .toList();
    }
    
}
