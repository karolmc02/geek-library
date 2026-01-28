package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper;

import org.mapstruct.Mapper;
import com.geekapps.geeklibrary.client.api.model.WorkDTO;
import com.geekapps.geeklibrary.domain.model.work.Work;
import port.in.model.CreateWorkCommand;

@Mapper
public interface WorkMapper {

  CreateWorkCommand toCreateWorkCommand(WorkDTO workDTO);

  WorkDTO toWorkDTO(Work work);

}
