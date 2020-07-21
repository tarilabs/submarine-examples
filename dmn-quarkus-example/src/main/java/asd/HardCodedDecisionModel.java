package asd;

import java.util.Map;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.api.core.FEELPropertyAccessible;
import org.kie.kogito.decision.DecisionModel;
import org.kie.kogito.dmn.DmnDecisionModel;

@javax.inject.Singleton
@javax.inject.Named("Traffic Violation")
public class HardCodedDecisionModel extends DmnDecisionModel implements DecisionModel {

    @javax.inject.Inject()
    public HardCodedDecisionModel(@javax.inject.Named("ASDRuntime") DMNRuntime dmnRuntime) {
        super(dmnRuntime, "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF", "Traffic Violation");
    } 
    
}