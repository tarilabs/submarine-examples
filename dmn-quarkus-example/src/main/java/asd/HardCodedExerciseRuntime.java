package asd;

import java.util.List;
import java.util.Set;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;

@javax.inject.Singleton
@javax.inject.Named("ASDRuntime")
public class HardCodedExerciseRuntime implements DMNRuntime {

    private final static org.kie.dmn.api.core.DMNRuntime wrapped = org.kie.kogito.dmn.DMNKogito
            .createGenericDMNRuntime(new java.io.InputStreamReader(
                    HardCodedExerciseRuntime.class.getResourceAsStream("/Traffic Violation.dmn")));

    @Override
    public void addListener(DMNRuntimeEventListener listener) {
        wrapped.addListener(listener);
    }

    @Override
    public void removeListener(DMNRuntimeEventListener listener) {
        wrapped.removeListener(listener);
    }

    @Override
    public Set<DMNRuntimeEventListener> getListeners() {
        return wrapped.getListeners();
    }

    @Override
    public boolean hasListeners() {
        return wrapped.hasListeners();
    }

    @Override
    public DMNRuntime getRuntime() {
        return wrapped.getRuntime();
    }

    @Override
    public List<DMNModel> getModels() {
        return wrapped.getModels();
    }

    @Override
    public DMNModel getModel(String namespace, String modelName) {
        return wrapped.getModel(namespace, modelName);
    }

    @Override
    public DMNModel getModelById(String namespace, String modelId) {
        return wrapped.getModelById(namespace, modelId);
    }

    @Override
    public DMNResult evaluateAll(DMNModel model, DMNContext context) {
        return wrapped.evaluateAll(model, context);
    }

    @Override
    public DMNResult evaluateDecisionByName(DMNModel model, String decisionName, DMNContext context) {
        return wrapped.evaluateDecisionByName(model, decisionName, context);
    }

    @Override
    public DMNResult evaluateDecisionById(DMNModel model, String decisionId, DMNContext context) {
        return wrapped.evaluateDecisionById(model, decisionId, context);
    }

    @Override
    public DMNResult evaluateByName(DMNModel model, DMNContext context, String... decisionNames) {
        return wrapped.evaluateByName(model, context, decisionNames);
    }

    @Override
    public DMNResult evaluateById(DMNModel model, DMNContext context, String... decisionIds) {
        return wrapped.evaluateById(model, context, decisionIds);
    }

    @Override
    public DMNContext newContext() {
        return wrapped.newContext();
    }

    @Override
    public ClassLoader getRootClassLoader() {
        return wrapped.getRootClassLoader();
    }

    @Override
    public DMNResult evaluateDecisionService(DMNModel model, DMNContext context, String decisionServiceName) {
        return wrapped.evaluateDecisionService(model, context, decisionServiceName);
    }

}