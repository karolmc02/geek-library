package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper;

import org.mapstruct.Mapper;
import com.geekapps.geeklibrary.client.api.model.WorkDTO;
import port.in.model.CreateWorkCommand;

@Mapper
public interface WorkMapper {

  CreateWorkCommand toCreateWorkCommand(WorkDTO workDTO);

}
