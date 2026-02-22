package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.VolumeEntity;

@Mapper
public interface VolumeEntityMapper {

  @Mapping(target = "edition", ignore = true)
  @Mapping(target = "price.currency", source = "price.currency")
  @Mapping(target = "price.amount", source = "price.amount")
  VolumeEntity toEntity(Volume volume);

  @Mapping(target = "price.currency", source = "price.currency")
  @Mapping(target = "price.amount", source = "price.amount")
  Volume toDomain(VolumeEntity volumeEntity);

}
