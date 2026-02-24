package com.geekapps.geeklibrary.infraestructure.adapter.out.rest.work.gemini;

import java.io.IOException;
import java.net.URI;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

    final String htmlContent = this.extractHtlmString(url);

    final String prompt =
        """
            You are a data extractor.
            Your task is to extract the data of a work from a given HTML content. The data should be extracted in JSON format following the provided JSON Schema. The JSON Schema defines the structure and types of the data you need to extract.
            Here is the JSON Schema for the Work entity:
            {jsonSchema}
            Follow ISO from currency, langueage and country codes.
            Here is the HTML content of the work's webpage:
            {htmlContent}
            Extract the relevant information from the HTML content and provide it in JSON format according to the JSON Schema. Make sure to include all required fields and adhere to the data types specified in the schema.
            """
            .replace("{jsonSchema}", jsonSchema.toPrettyString())
            .replace("{htmlContent}", htmlContent);

    final GenerateContentConfig generateContentConfig = GenerateContentConfig.builder()
        // .tools(Tool.builder()
        // .urlContext(UrlContext.builder()))
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

      final Work work = new ObjectMapper().readValue(text, Work.class);
      return work;
    } catch (final IllegalArgumentException e) {
      final String errorMessage =
          "Error parsing work preview JSON response from Gemini API: " + e.getMessage();
      this.logger.error(errorMessage, e);
      throw new InvalidWorkPreviewImportException(errorMessage, e);
    }
  }

  private String extractHtlmString(final URI url) {
    try {
      final Document doc = Jsoup.connect(url.toString())
          .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
          .timeout(10000)
          .get();

      doc.select("script, style, footer, nav, head")
          .remove();

      final String htmlContent = doc.body()
          .html();

      this.logger.info("Extracted HTML content from URL {}: {}", url, htmlContent);

      return htmlContent;
    } catch (final IOException e) {
      this.logger.error("Error fetching HTML content from URL: " + url, e);
      return null;
    }
  }
}
