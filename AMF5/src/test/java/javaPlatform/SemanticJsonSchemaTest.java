package javaPlatform;

import amf.aml.client.platform.AMLDialectResult;
import amf.shapes.client.platform.config.SemanticJsonSchemaConfiguration;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class SemanticJsonSchemaTest {

    @Test
    public void jsonSchemaToAml() throws ExecutionException, InterruptedException {
        AMLDialectResult amlDialectResult = SemanticJsonSchemaConfiguration.predefined().baseUnitClient().parseDialect("file://src/test/resources/examples/json-schema.json").get();


        assert(!amlDialectResult.dialect().declares().isEmpty());

    }
}
