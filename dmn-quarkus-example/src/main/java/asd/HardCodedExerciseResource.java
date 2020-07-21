package https_58_47_47github_46com_47kiegroup_47drools_47kie_45dmn_47__A4BCA8B8_45CF08_45433F_4593B2_45A2598F19ECFF;

import java.time.Period;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.drools.core.beliefsystem.simple.SimpleMode;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.core.impl.DMNContextImpl;
import org.kie.dmn.feel.lang.types.impl.ComparablePeriod;
import org.kie.kogito.Application;
import org.kie.kogito.decision.DecisionModel;
import org.kie.kogito.dmn.rest.DMNEvaluationErrorException;
import org.kie.kogito.dmn.rest.DMNResult;

@Path("/HardCodedExerciseResource")
public class HardCodedExerciseResource {

    // -- RELEVANT PART OF THE HARD CODE EXERCISE --
    // @javax.inject.Inject()
    // Application application;
    @javax.inject.Inject()
    @javax.inject.Named("Traffic Violation")
    DecisionModel decision;
    // -- RELEVANT PART OF THE HARD CODE EXERCISE --
    
    private static final String KOGITO_DECISION_INFOWARN_HEADER = "X-Kogito-decision-messages";

    @javax.ws.rs.core.Context
    private org.jboss.resteasy.spi.HttpResponse httpResponse;

    private static final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule()).registerModule(new com.fasterxml.jackson.databind.module.SimpleModule().addSerializer(org.kie.dmn.feel.lang.types.impl.ComparablePeriod.class, new org.kie.kogito.dmn.rest.DMNFEELComparablePeriodSerializer())).disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object dmn(java.util.Map<String, Object> variables) {
        org.kie.dmn.api.core.DMNResult evaluateAll = decision.evaluateAll(decision.newContext(variables));
        org.kie.kogito.dmn.rest.DMNResult result = new org.kie.kogito.dmn.rest.DMNResult("https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF", "Traffic Violation", evaluateAll);
        return extractContextIfSucceded(result);
    }

    @javax.ws.rs.ext.Provider
    public static class DMNEvaluationErrorExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<org.kie.kogito.dmn.rest.DMNEvaluationErrorException> {

        public javax.ws.rs.core.Response toResponse(org.kie.kogito.dmn.rest.DMNEvaluationErrorException e) {
            return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR).entity(e.getResult()).build();
        }
    }

    private Object extractContextIfSucceded(DMNResult result) {
        if (!result.hasErrors()) {
            try {
                enrichResponseHeaders(result);
                return objectMapper.writeValueAsString(result.getDmnContext());
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new DMNEvaluationErrorException(result);
        }
    }

    private Object extractSingletonDSIfSucceded(DMNResult result) {
        if (!result.hasErrors()) {
            try {
                enrichResponseHeaders(result);
                return objectMapper.writeValueAsString(result.getDecisionResults().get(0).getResult());
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new DMNEvaluationErrorException(result);
        }
    }

    private void enrichResponseHeaders(DMNResult result) {
        if (!result.getMessages().isEmpty()) {
            String infoWarns = result.getMessages().stream().map(m -> m.getLevel() + " " + m.getMessage()).collect(java.util.stream.Collectors.joining(", "));
            httpResponse.getOutputHeaders().add(KOGITO_DECISION_INFOWARN_HEADER, infoWarns);
        }
    }
}
