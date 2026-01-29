package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper;

import org.mapstruct.Mapper;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.WorkEntity;

@Mapper
public interface WorkEntityMapper {

  Work toDomain(WorkEntity workEntity);

  WorkEntity toEntity(Work work);

}
