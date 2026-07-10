package com.ales.aianalysis.api;

import com.ales.aianalysis.api.dto.QuestionResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import com.ales.aianalysis.api.dto.CreateQuestionRequest;
import com.ales.aianalysis.service.QuestionService;

import java.util.List;

@Path("analyses/{analysisId}/questions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionResource {
    @Inject
    QuestionService questionService;

    @POST
    public QuestionResponse createQuestion(
        @PathParam("analysisId") Long analysisId,
        CreateQuestionRequest request
    ){
        return QuestionResponse.fromEntity(
            questionService.createQuestion(
                analysisId,
                request.questionText,
                request.position
            )
        );
    }

    @GET
    public List<QuestionResponse> getQuestionsByAnalysisId(
        @PathParam("analysisId") Long analysisId
    ){
        return questionService.getQuestionsByAnalysisId(analysisId)
            .stream()
            .map(QuestionResponse::fromEntity)
            .toList();
    }

    @GET
    @Path("/{questionId}")
    public QuestionResponse getQuestionByAnalysisIdAndQuestionId(
        @PathParam("analysisId") Long analysisId,
        @PathParam("questionId") Long questionId
    ){
        return QuestionResponse.fromEntity(
            questionService.getQuestionByAnalysisIDAndQuestionId(analysisId, questionId)
        );
    }

    @DELETE
    @Path("/{questionId}")
    public Response deleteQuestionByAnalysisIdAndQuestionId(@PathParam("analysisId") Long analysisId, @PathParam("questionId") Long questionId){
        questionService.deleteQuestion(analysisId, questionId);
        return Response.noContent().build();
    }
}
