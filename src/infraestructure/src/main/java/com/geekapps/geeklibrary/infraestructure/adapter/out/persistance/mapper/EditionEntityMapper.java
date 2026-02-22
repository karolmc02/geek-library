package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.geekapps.geeklibrary.domain.model.edition.Edition;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.EditionEntity;

@Mapper
public interface EditionEntityMapper {

  @Mapping(target = "work", ignore = true)
  @Mapping(target = "languageIsoCode", source = "language.isoCode")
  @Mapping(target = "countryIsoCode", source = "country.isoCode")
  EditionEntity toEntity(Edition edition);

  @Mapping(target = "language.isoCode", source = "languageIsoCode")
  @Mapping(target = "country.isoCode", source = "countryIsoCode")
  Edition toDomain(EditionEntity editionEntity);

}
