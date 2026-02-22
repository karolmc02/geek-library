package com.geekapps.geeklibrary.infraestructure.adapter.out.rest.work;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.work.WorkPreviewPort;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import tools.jackson.databind.JsonNode;

@Service
public class WorkPreviewAdapter implements WorkPreviewPort {

  private final Logger logger = LoggerFactory.getLogger(WorkPreviewAdapter.class);

  @Override
  public Work previewWork(final URI url) {
    final SchemaGeneratorConfigBuilder configBuilder =
        new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
    final SchemaGeneratorConfig config = configBuilder.build();
    final SchemaGenerator generator = new SchemaGenerator(config);
    final JsonNode jsonSchema = generator.generateSchema(Work.class);

    this.logger.info("Generated JSON Schema for Work: {}", jsonSchema.toPrettyString());

    return null;
  }

}
