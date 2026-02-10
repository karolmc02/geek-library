package com.geekapps.geeklibrary.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidator;
import com.geekapps.geeklibrary.domain.service.VolumeValidator;
import com.geekapps.geeklibrary.domain.service.WorkValidator;

@Configuration
public class ValidatorsConfiguration {

  @Bean
  public WorkValidator workValidator(final WorkRepository workRepository) {
    return new WorkValidator(workRepository);
  }

  @Bean
  public EditionValidator editionValidator(final EditionRepository editionRepository) {
    return new EditionValidator(editionRepository);
  }

  @Bean
  public VolumeValidator volumeValidator(final VolumeRepository volumeRepository) {
    return new VolumeValidator(volumeRepository);
  }

}
