package com.geekapps.geeklibrary.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.geekapps.geeklibrary.domain.port.out.edition.EditionRepository;
import com.geekapps.geeklibrary.domain.port.out.volume.VolumeRepository;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;
import com.geekapps.geeklibrary.domain.service.EditionValidatorService;
import com.geekapps.geeklibrary.domain.service.VolumeValidatorService;
import com.geekapps.geeklibrary.domain.service.WorkValidatorService;

@Configuration
public class ValidatorsConfiguration {

  @Bean
  public WorkValidatorService workValidatorService(final WorkRepository workRepository) {
    return new WorkValidatorService(workRepository);
  }

  @Bean
  public EditionValidatorService editionValidatorService(
      final EditionRepository editionRepository) {
    return new EditionValidatorService(editionRepository);
  }

  @Bean
  public VolumeValidatorService volumeValidatorService(final VolumeRepository volumeRepository) {
    return new VolumeValidatorService(volumeRepository);
  }

}
