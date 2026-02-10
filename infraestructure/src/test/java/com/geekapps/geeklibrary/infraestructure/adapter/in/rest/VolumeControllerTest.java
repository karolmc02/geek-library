package com.geekapps.geeklibrary.infraestructure.adapter.in.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import com.geekapps.geeklibrary.application.port.in.model.to.MoneyTO;
import com.geekapps.geeklibrary.application.port.in.model.volume.CreateVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.model.volume.DeleteVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.model.volume.GetVolumeByIdCommand;
import com.geekapps.geeklibrary.application.port.in.model.volume.UpdateVolumeCommand;
import com.geekapps.geeklibrary.application.port.in.volume.CreateVolumeUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.DeleteVolumeUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumeByIdUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.GetVolumesByEditionIdUseCase;
import com.geekapps.geeklibrary.application.port.in.volume.UpdateVolumeUseCase;
import com.geekapps.geeklibrary.client.api.model.MoneyDTO;
import com.geekapps.geeklibrary.client.api.model.VolumeDTO;
import com.geekapps.geeklibrary.domain.model.common.Money;
import com.geekapps.geeklibrary.domain.model.volume.Volume;
import com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper.VolumeMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("VolumeController Tests")
class VolumeControllerTest {

  @Mock
  private VolumeMapper volumeMapper;

  @Mock
  private CreateVolumeUseCase createVolumeUseCase;

  @Mock
  private GetVolumesByEditionIdUseCase getVolumesByEditionIdUseCase;

  @Mock
  private GetVolumeByIdUseCase getVolumeByIdUseCase;

  @Mock
  private UpdateVolumeUseCase updateVolumeUseCase;

  @Mock
  private DeleteVolumeUseCase deleteVolumeUseCase;

  @InjectMocks
  private VolumeController volumeController;

  private UUID workId;
  private UUID editionId;
  private UUID volumeId;
  private VolumeDTO volumeDTO;
  private Volume volume;
  private MoneyDTO moneyDTO;
  private Money money;

  @BeforeEach
  void setUp() {
    this.workId = UUID.randomUUID();
    this.editionId = UUID.randomUUID();
    this.volumeId = UUID.randomUUID();

    this.moneyDTO = new MoneyDTO().amount(19.99).currency("USD");
    this.volumeDTO = new VolumeDTO().title("Volume 1").number(1).price(this.moneyDTO)
        .publicationDate(LocalDate.of(2024, 1, 15)).isbn("978-1234567890").pages(200);

    this.money = new Money("USD", new BigDecimal("19.99"));
    this.volume = new Volume(this.volumeId, "Volume 1", 1, this.money, LocalDate.of(2024, 1, 15),
        "978-1234567890", 200);
  }

  @Test
  @DisplayName("Should create volume successfully")
  void shouldCreateVolumeSuccessfully() {
    // Given
    final var moneyTO = new MoneyTO("USD", new BigDecimal("19.99"));
    final var createCommand = new CreateVolumeCommand(this.workId, this.editionId, "Volume 1", 1,
        moneyTO, LocalDate.of(2024, 1, 15), "978-1234567890", 200);

    Mockito.when(this.volumeMapper.toCreateVolumeCommand(this.workId, this.editionId, this.volumeDTO))
        .thenReturn(createCommand);
    Mockito.when(this.createVolumeUseCase.execute(createCommand)).thenReturn(this.volume);
    Mockito.when(this.volumeMapper.toVolumeDTO(this.volume)).thenReturn(this.volumeDTO);

    // When
    final var response =
        this.volumeController.createVolume(this.workId, this.editionId, this.volumeDTO);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(response.getBody().getNumber()).isEqualTo(1);

    Mockito.verify(this.volumeMapper).toCreateVolumeCommand(this.workId, this.editionId, this.volumeDTO);
    Mockito.verify(this.createVolumeUseCase).execute(createCommand);
    Mockito.verify(this.volumeMapper).toVolumeDTO(this.volume);
  }

  @Test
  @DisplayName("Should get volumes by edition ID successfully")
  void shouldGetVolumesByEditionIdSuccessfully() {
    // Given
    final var volumes = List.of(this.volume);
    final var volumeDTOs = List.of(this.volumeDTO);

    Mockito.when(this.getVolumesByEditionIdUseCase.execute(this.editionId)).thenReturn(volumes);
    Mockito.when(this.volumeMapper.toVolumeDTOList(volumes)).thenReturn(volumeDTOs);

    // When
    final var response = this.volumeController.getVolumesByEditionId(this.workId, this.editionId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getVolumes()).hasSize(1);
    Assertions.assertThat(response.getBody().getVolumes().get(0).getTitle()).isEqualTo("Volume 1");

    Mockito.verify(this.getVolumesByEditionIdUseCase).execute(this.editionId);
    Mockito.verify(this.volumeMapper).toVolumeDTOList(volumes);
  }

  @Test
  @DisplayName("Should return empty list when no volumes found for edition")
  void shouldReturnEmptyListWhenNoVolumesFoundForEdition() {
    // Given
    final List<Volume> emptyVolumes = Collections.emptyList();
    final List<VolumeDTO> emptyDTOs = Collections.emptyList();

    Mockito.when(this.getVolumesByEditionIdUseCase.execute(this.editionId)).thenReturn(emptyVolumes);
    Mockito.when(this.volumeMapper.toVolumeDTOList(emptyVolumes)).thenReturn(emptyDTOs);

    // When
    final var response = this.volumeController.getVolumesByEditionId(this.workId, this.editionId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getVolumes()).isEmpty();

    Mockito.verify(this.getVolumesByEditionIdUseCase).execute(this.editionId);
    Mockito.verify(this.volumeMapper).toVolumeDTOList(emptyVolumes);
  }

  @Test
  @DisplayName("Should get multiple volumes for edition")
  void shouldGetMultipleVolumesForEdition() {
    // Given
    final var volume2 = new Volume(UUID.randomUUID(), "Volume 2", 2, this.money,
        LocalDate.of(2024, 2, 15), "978-0987654321", 210);
    final var volumeDTO2 = new VolumeDTO().title("Volume 2").number(2).price(this.moneyDTO)
        .publicationDate(LocalDate.of(2024, 2, 15)).isbn("978-0987654321").pages(210);

    final var volumes = Arrays.asList(this.volume, volume2);
    final var volumeDTOs = Arrays.asList(this.volumeDTO, volumeDTO2);

    Mockito.when(this.getVolumesByEditionIdUseCase.execute(this.editionId)).thenReturn(volumes);
    Mockito.when(this.volumeMapper.toVolumeDTOList(volumes)).thenReturn(volumeDTOs);

    // When
    final var response = this.volumeController.getVolumesByEditionId(this.workId, this.editionId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getVolumes()).hasSize(2);
    Assertions.assertThat(response.getBody().getVolumes().get(0).getNumber()).isEqualTo(1);
    Assertions.assertThat(response.getBody().getVolumes().get(1).getNumber()).isEqualTo(2);

    Mockito.verify(this.getVolumesByEditionIdUseCase).execute(this.editionId);
    Mockito.verify(this.volumeMapper).toVolumeDTOList(volumes);
  }

  @Test
  @DisplayName("Should get volume by ID successfully")
  void shouldGetVolumeByIdSuccessfully() {
    // Given
    final var getVolumeByIdCommand =
        new GetVolumeByIdCommand(this.workId, this.editionId, this.volumeId);

    Mockito.when(this.volumeMapper.toGetVolumeByIdCommand(this.workId, this.editionId, this.volumeId))
        .thenReturn(getVolumeByIdCommand);
    Mockito.when(this.getVolumeByIdUseCase.execute(getVolumeByIdCommand)).thenReturn(this.volume);
    Mockito.when(this.volumeMapper.toVolumeDTO(this.volume)).thenReturn(this.volumeDTO);

    // When
    final var response =
        this.volumeController.getVolumeById(this.workId, this.editionId, this.volumeId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getTitle()).isEqualTo("Volume 1");
    Assertions.assertThat(response.getBody().getNumber()).isEqualTo(1);

    Mockito.verify(this.volumeMapper).toGetVolumeByIdCommand(this.workId, this.editionId, this.volumeId);
    Mockito.verify(this.getVolumeByIdUseCase).execute(getVolumeByIdCommand);
    Mockito.verify(this.volumeMapper).toVolumeDTO(this.volume);
  }

  @Test
  @DisplayName("Should update volume successfully")
  void shouldUpdateVolumeSuccessfully() {
    // Given
    final var moneyTO = new MoneyTO("USD", new BigDecimal("19.99"));
    final var updateCommand = new UpdateVolumeCommand(this.workId, this.editionId, this.volumeId,
        "Volume 1", 1, moneyTO, LocalDate.of(2024, 1, 15), "978-1234567890", 200);

    Mockito.when(this.volumeMapper.toUpdateVolumeCommand(this.workId, this.editionId, this.volumeId,
        this.volumeDTO)).thenReturn(updateCommand);
    Mockito.when(this.updateVolumeUseCase.execute(updateCommand)).thenReturn(this.volume);
    Mockito.when(this.volumeMapper.toVolumeDTO(this.volume)).thenReturn(this.volumeDTO);

    // When
    final var response = this.volumeController.updateVolumeById(this.workId, this.editionId,
        this.volumeId, this.volumeDTO);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getTitle()).isEqualTo("Volume 1");

    Mockito.verify(this.volumeMapper).toUpdateVolumeCommand(this.workId, this.editionId, this.volumeId,
        this.volumeDTO);
    Mockito.verify(this.updateVolumeUseCase).execute(updateCommand);
    Mockito.verify(this.volumeMapper).toVolumeDTO(this.volume);
  }

  @Test
  @DisplayName("Should delete volume successfully")
  void shouldDeleteVolumeSuccessfully() {
    // Given
    final var deleteCommand = new DeleteVolumeCommand(this.workId, this.editionId, this.volumeId);

    Mockito.when(this.volumeMapper.toDeleteVolumeCommand(this.workId, this.editionId, this.volumeId))
        .thenReturn(deleteCommand);

    // When
    final var response =
        this.volumeController.deleteVolumeById(this.workId, this.editionId, this.volumeId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    Assertions.assertThat(response.getBody()).isNull();

    Mockito.verify(this.volumeMapper).toDeleteVolumeCommand(this.workId, this.editionId, this.volumeId);
    Mockito.verify(this.deleteVolumeUseCase).execute(deleteCommand);
  }
}
