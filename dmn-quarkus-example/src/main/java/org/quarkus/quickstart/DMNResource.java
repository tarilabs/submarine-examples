package org.quarkus.quickstart;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.drools.core.RuleBaseConfiguration;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.definitions.ResourceTypePackageRegistry;
import org.drools.core.definitions.impl.KnowledgePackageImpl;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.kie.api.io.ResourceType;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.compiler.DMNCompilerImpl;
import org.kie.dmn.core.impl.DMNPackageImpl;
import org.kie.dmn.core.impl.DMNRuntimeImpl;

@Path("/dmn")
public class DMNResource {

    static final DMNRuntime dmnRuntime;
    static {
        KnowledgeBaseImpl knowledgeBase = new KnowledgeBaseImpl("", new RuleBaseConfiguration());
        Map<String, InternalKnowledgePackage> pkgs = knowledgeBase.getPackagesMap();
        DMNCompilerImpl compilerImpl = new DMNCompilerImpl();
        try {
            List<java.nio.file.Path> files = Files.walk(Paths.get("."))
                                                  .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".dmn"))
                                                  .peek(System.out::println)
                                                  .collect(Collectors.toList());
            for (java.nio.file.Path file : files) {
                DMNModel m = compilerImpl.compile(new FileReader(file.toFile()));
                InternalKnowledgePackage pkg = pkgs.computeIfAbsent(m.getNamespace(), ns -> new KnowledgePackageImpl(ns));
                ResourceTypePackageRegistry rpkg = pkg.getResourceTypePackages();
                DMNPackageImpl dmnpkg = rpkg.computeIfAbsent(ResourceType.DMN, rtp -> new DMNPackageImpl(m.getNamespace()));
                dmnpkg.addModel(m.getName(), m);// TODO add profiles?
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dmnRuntime = new DMNRuntimeImpl(knowledgeBase);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/dmn/{name}")
    public String dmn(@PathParam("name") String name) {
        DMNContext ctx = dmnRuntime.newContext();
        ctx.set("a name", name);
        DMNResult evaluateAll = dmnRuntime.evaluateAll(dmnRuntime.getModels().get(0), ctx);
        for (DMNMessage m : evaluateAll.getMessages()) {
            System.out.println(m);
        }
        return evaluateAll.toString();
    }

}
