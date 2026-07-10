package com.ales.aianalysis.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @DELETE
    @Path("/{analysisId}")
    public Response deleteAnalysisById(@PathParam("analysisId") Long id) {
        analysisService.deleteAnalysis(id);
        // vrne 204 No Content, da pove da je bil delete uspešen, nekaj podobnega sem deleal v Spring Bootu
        return Response.noContent().build();
    }

    @PUT
    @Path("/{analysisId}")
    public AnalysisResponse updateAnalysisById(@PathParam("analysisId") Long id, CreateAnalysisRequest request) {
        return AnalysisResponse.fromEntity(
            analysisService.updateAnalysis(id, request.title, request.description)
        );
    }
    
}
