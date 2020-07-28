package asd;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.ConfigFunction;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class App {
    public static void main(String[] args) {
        JacksonModule module = new JacksonModule(JacksonOption.FLATTENED_ENUMS_FROM_JSONVALUE);
        SchemaGeneratorConfigBuilder builder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_7,
                 OptionPreset.PLAIN_JSON)
                .with(module);
        builder.forFields().withEnumResolver(fs -> {
            return Optional.ofNullable(fs.getAnnotationConsideringFieldAndGetter(Schema.class)).map(Schema::enumeration).map(Arrays::asList).orElse(Collections.emptyList());
        });
        SchemaGenerator generator = new SchemaGenerator(builder.build());
        ObjectWriter writer = new ObjectMapper().writer();
        ObjectNode generateSchema = generator.generateSchema(InputSet.class);
        String prettyString = generateSchema.toPrettyString();
        System.out.println(prettyString);
    }
}