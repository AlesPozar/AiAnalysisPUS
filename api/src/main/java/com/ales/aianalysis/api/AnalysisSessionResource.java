package com.ales.aianalysis.api;

import com.ales.aianalysis.api.dto.AnalysisSessionResponse;
import com.ales.aianalysis.api.dto.CreateAnalysisSessionRequest;
import com.ales.aianalysis.entity.AnalysisSession;
import com.ales.aianalysis.service.AnalysisSessionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;



@Path("/analyses/{analysisId}/sessions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnalysisSessionResource {
    
    @Inject
    AnalysisSessionService analysisSessionService;

    @POST
    public AnalysisSessionResponse createAnalysisSession(@PathParam("analysisId") Long analysisId, CreateAnalysisSessionRequest request) {
        AnalysisSession analysisSession = analysisSessionService.createAnalysisSession(analysisId, request.respondent);
        return AnalysisSessionResponse.fromEntity(analysisSession);
    }

    @GET
    public List<AnalysisSessionResponse> getSessionsByAnalysis(@PathParam("analysisId") Long analysisId) {
        return analysisSessionService.getSessionsByAnalysisId(analysisId)
            .stream()
            .map(AnalysisSessionResponse::fromEntity)
            .toList();
    }

    @GET
    @Path("/{sessionId}")
    public AnalysisSessionResponse getSessionByAnalysisIdAndSessionId(@PathParam("analysisId") Long analysisId, @PathParam("sessionId") Long sessionId) {
        return AnalysisSessionResponse.fromEntity(analysisSessionService.getSessionByAnalysisIdAndSessionId(analysisId, sessionId));
    }
}
