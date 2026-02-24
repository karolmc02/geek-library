package com.geekapps.geeklibrary.infraestructure.adapter.out.rest.work.gemini;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.exception.InvalidWorkPreviewImportException;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.work.WorkPreviewPort;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Tool;
import com.google.genai.types.UrlContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class GeminiWorkPreviewAdapter implements WorkPreviewPort {

  private final Logger logger = LoggerFactory.getLogger(GeminiWorkPreviewAdapter.class);


  @Value("${app.gemini.model}")
  private final String model = "gemini-2.5-flash";

  private final Client geminiClient;

  public GeminiWorkPreviewAdapter(final Client geminiClient) {
    this.geminiClient = geminiClient;
  }

  @Override
  public Work previewWork(final URI url) {
    final SchemaGeneratorConfigBuilder configBuilder =
        new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
    final SchemaGeneratorConfig config = configBuilder.build();
    final SchemaGenerator generator = new SchemaGenerator(config);
    final JsonNode jsonSchema = generator.generateSchema(Work.class);

    this.logger.info("Generated JSON Schema for Work: {}", jsonSchema.toPrettyString());

    final String prompt =
        """
            You are a data extractor.
            Your task is to extract the data of a work from a given URL. The data should be extracted in JSON format following the provided JSON Schema. The JSON Schema defines the structure and types of the data you need to extract.
            Here is the JSON Schema for the Work entity:
            {jsonSchema}
            Follow ISO from currency, langueage and country codes.
            Please visit the URL and extract the data of the work according to the JSON Schema. Make sure to follow the structure and types defined in the JSON Schema. If any data is missing or cannot be extracted, please set the corresponding fields to null in the JSON output.
            URL: {url}
            """
            .replace("{jsonSchema}", jsonSchema.toPrettyString())
            .replace("{url}", url.toString());

    final GenerateContentConfig generateContentConfig = GenerateContentConfig.builder()
        .tools(Tool.builder()
            .urlContext(UrlContext.builder()))
        .build();
    final GenerateContentResponse response =
        this.geminiClient.models.generateContent(this.model, prompt, generateContentConfig);


    try {
      final String text = response.text()
          .replace("```json", "")
          .replace("```", "");

      this.logger.info("Received text response from Gemini API for URL {}: {}", url, text);

      if (text == null) {
        final String errorMessage = "No text response received from Gemini API for URL: " + url;
        this.logger.error(errorMessage);
        throw new InvalidWorkPreviewImportException(errorMessage);
      }

      return new ObjectMapper().readValue(text, Work.class);
    } catch (final IllegalArgumentException e) {
      final String errorMessage =
          "Error parsing work preview JSON response from Gemini API: " + e.getMessage();
      this.logger.error(errorMessage, e);
      throw new InvalidWorkPreviewImportException(errorMessage, e);
    }
  }
}
