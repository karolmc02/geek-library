package com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper;

import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.geekapps.geeklibrary.application.port.in.model.volume.CreateVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.model.volume.DeleteVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.model.volume.GetVolumeByIdCommand;
import com.geekapps.geeklibrary.application.port.in.model.volume.UpdateVolumeCommand;
import com.geekapps.geeklibrary.client.api.model.VolumeDTO;
import com.geekapps.geeklibrary.domain.model.volume.Volume;

@Mapper
public interface VolumeMapper {

  @Mapping(target = "workId", source = "workId")
  @Mapping(target = "editionId", source = "editionId")
  CreateVolumeCommand toCreateVolumeCommand(UUID workId, UUID editionId, VolumeDTO volumeDTO);

  @Mapping(target = "workId", source = "workId")
  @Mapping(target = "editionId", source = "editionId")
  @Mapping(target = "volumeId", source = "volumeId")
  UpdateVolumeCommand toUpdateVolumeCommand(UUID workId, UUID editionId, UUID volumeId,
      VolumeDTO volumeDTO);

  GetVolumeByIdCommand toGetVolumeByIdCommand(UUID workId, UUID editionId, UUID volumeId);

  DeleteVolumeCommand toDeleteVolumeCommand(UUID workId, UUID editionId, UUID volumeId);

  VolumeDTO toVolumeDTO(Volume volume);

  List<VolumeDTO> toVolumeDTOList(List<Volume> volumes);

}
