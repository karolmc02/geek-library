package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper;

import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.geekapps.geeklibrary.application.port.in.model.edition.CreateEditionCommand;
import com.geekapps.geeklibrary.application.port.in.model.edition.DeleteEditionCommand;
import com.geekapps.geeklibrary.application.port.in.model.edition.GetEditionByIdCommand;
import com.geekapps.geeklibrary.application.port.in.model.edition.UpdateEditionCommand;
import com.geekapps.geeklibrary.client.api.model.EditionDTO;
import com.geekapps.geeklibrary.domain.model.edition.Edition;

@Mapper
public interface EditionMapper {

  @Mapping(target = "workId", source = "workId")
  CreateEditionCommand toCreateEditionCommand(UUID workId, EditionDTO editionDTO);

  @Mapping(target = "workId", source = "workId")
  @Mapping(target = "editionId", source = "editionId")
  UpdateEditionCommand toUpdateEditionCommand(UUID workId, UUID editionId, EditionDTO editionDTO);

  GetEditionByIdCommand toGetEditionByIdCommand(UUID workId, UUID editionId);

  DeleteEditionCommand toDeleteEditionCommand(UUID workId, UUID editionId);

  EditionDTO toEditionDTO(Edition edition);

  List<EditionDTO> toEditionDTOList(List<Edition> editions);

}
