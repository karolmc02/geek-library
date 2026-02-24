package com.geekapps.geeklibrary.boot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.genai.Client;

@Configuration
public class GeminiClientConfiguration {

  @Value("${app.gemini.api_key}")
  private String apiKey;

  @Bean
  public Client geminiClient() {
    return Client.builder().apiKey(this.apiKey).build();
  }

}
