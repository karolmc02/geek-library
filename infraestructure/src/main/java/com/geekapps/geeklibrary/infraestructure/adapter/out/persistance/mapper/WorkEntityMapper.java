package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.WorkEntity;

@Mapper(uses = EditionEntityMapper.class)
public interface WorkEntityMapper {

  Work toDomain(WorkEntity workEntity);

  @Mapping(target = "editions", ignore = true)
  WorkEntity toEntity(Work work);

}
