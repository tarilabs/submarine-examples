package dummy;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kie.kogito.Application;

@Path("/Traffic%20Violation")
public class Traffic_32ViolationResource {

    @javax.inject.Inject()
    Application application;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public org.kie.dmn.kogito.rest.quarkus.DMNResult dmn(java.util.Map<String, Object> variables) {
        return null;
    }
}
