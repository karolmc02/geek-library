package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper;

import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import com.geekapps.geeklibrary.client.api.model.WorkDTO;
import com.geekapps.geeklibrary.domain.model.work.Work;
import port.in.model.CreateWorkCommand;
import port.in.model.QueryWorksCommand;
import port.in.model.UpdateWorkCommand;

@Mapper
public interface WorkMapper {

  CreateWorkCommand toCreateWorkCommand(WorkDTO workDTO);

  WorkDTO toWorkDTO(Work work);

  List<WorkDTO> toWorkDTOList(List<Work> works);

  QueryWorksCommand toQueryWorksCommand(final String title, final String author);

  UpdateWorkCommand toUpdateWorkCommand(final UUID id, final WorkDTO workDTO);

}
