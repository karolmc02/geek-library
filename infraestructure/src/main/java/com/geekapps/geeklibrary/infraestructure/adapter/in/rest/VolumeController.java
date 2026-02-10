package com.geekapps.geeklibrary.infraestructure.adapter.in.rest;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.geekapps.geeklibrary.application.port.in.volume.CreateVolumeUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.DeleteVolumeUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumeByIdUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumesByEditionIdUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.UpdateVolumeUseCase;
import com.geekapps.geeklibrary.client.api.api.VolumesApi;
import com.geekapps.geeklibrary.client.api.model.GetVolumesByEditionId200ResponseDTO;
import com.geekapps.geeklibrary.client.api.model.VolumeDTO;
import com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper.VolumeMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class VolumeController implements VolumesApi {

  private final VolumeMapper volumeMapper;
  private final CreateVolumeUseCase createVolumeUseCase;
  private final GetVolumesByEditionIdUseCase getVolumesByEditionIdUseCase;
  private final GetVolumeByIdUseCase getVolumeByIdUseCase;
  private final UpdateVolumeUseCase updateVolumeUseCase;
  private final DeleteVolumeUseCase deleteVolumeUseCase;

  public VolumeController(final VolumeMapper volumeMapper,
      final CreateVolumeUseCase createVolumeUseCase,
      final GetVolumesByEditionIdUseCase getVolumesByEditionIdUseCase,
      final GetVolumeByIdUseCase getVolumeByIdUseCase,
      final UpdateVolumeUseCase updateVolumeUseCase,
      final DeleteVolumeUseCase deleteVolumeUseCase) {
    this.volumeMapper = volumeMapper;
    this.createVolumeUseCase = createVolumeUseCase;
    this.getVolumesByEditionIdUseCase = getVolumesByEditionIdUseCase;
    this.getVolumeByIdUseCase = getVolumeByIdUseCase;
    this.updateVolumeUseCase = updateVolumeUseCase;
    this.deleteVolumeUseCase = deleteVolumeUseCase;
  }

  @Override
  public ResponseEntity<VolumeDTO> createVolume(@NotNull final UUID workId,
      @NotNull final UUID editionId, @Valid final VolumeDTO volumeDTO) {
    final var createVolumeCommand =
        this.volumeMapper.toCreateVolumeCommand(workId, editionId, volumeDTO);
    final var createdVolume = this.createVolumeUseCase.execute(createVolumeCommand);
    final var createdVolumeDTO = this.volumeMapper.toVolumeDTO(createdVolume);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdVolumeDTO);
  }

  @Override
  public ResponseEntity<GetVolumesByEditionId200ResponseDTO> getVolumesByEditionId(
      @NotNull final UUID workId, @NotNull final UUID editionId) {
    final var volumes = this.getVolumesByEditionIdUseCase.execute(editionId);
    final var volumeDTOs = this.volumeMapper.toVolumeDTOList(volumes);
    final var response = new GetVolumesByEditionId200ResponseDTO().volumes(volumeDTOs);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<VolumeDTO> getVolumeById(@NotNull final UUID workId,
      @NotNull final UUID editionId, @NotNull final UUID volumeId) {
    final var getVolumeByIdCommand =
        this.volumeMapper.toGetVolumeByIdCommand(workId, editionId, volumeId);
    final var volume = this.getVolumeByIdUseCase.execute(getVolumeByIdCommand);
    final var volumeDTO = this.volumeMapper.toVolumeDTO(volume);
    return ResponseEntity.ok(volumeDTO);
  }

  @Override
  public ResponseEntity<VolumeDTO> updateVolumeById(@NotNull final UUID workId,
      @NotNull final UUID editionId, @NotNull final UUID volumeId,
      @Valid final VolumeDTO volumeDTO) {
    final var updateVolumeCommand =
        this.volumeMapper.toUpdateVolumeCommand(workId, editionId, volumeId, volumeDTO);
    final var updatedVolume = this.updateVolumeUseCase.execute(updateVolumeCommand);
    final var updatedVolumeDTO = this.volumeMapper.toVolumeDTO(updatedVolume);
    return ResponseEntity.ok(updatedVolumeDTO);
  }

  @Override
  public ResponseEntity<Void> deleteVolumeById(@NotNull final UUID workId,
      @NotNull final UUID editionId, @NotNull final UUID volumeId) {
    final var deleteVolumeCommand =
        this.volumeMapper.toDeleteVolumeCommand(workId, editionId, volumeId);
    this.deleteVolumeUseCase.execute(deleteVolumeCommand);
    return ResponseEntity.noContent().build();
  }

}
