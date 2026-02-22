package com.geekapps.geeklibrary.infraestructure.adapter.in.rest;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.geekapps.geeklibrary.application.port.in.edition.CreateEditionUseCase;
import com.geekapps.geeklibrary.application.port.in.edition.DeleteEditionUseCase;
import com.geekapps.geeklibrary.application.port.in.edition.GetEditionByIdUseCase;
import com.geekapps.geeklibrary.application.port.in.edition.GetEditionsByWorkIdUseCase;
import com.geekapps.geeklibrary.application.port.in.edition.UpdateEditionUseCase;
import com.geekapps.geeklibrary.client.api.api.EditionsApi;
import com.geekapps.geeklibrary.client.api.model.EditionDTO;
import com.geekapps.geeklibrary.client.api.model.GetEditionsByWorkId200ResponseDTO;
import com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper.EditionMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class EditionController implements EditionsApi {

  private final EditionMapper editionMapper;
  private final CreateEditionUseCase createEditionUseCase;
  private final GetEditionsByWorkIdUseCase getEditionsByWorkIdUseCase;
  private final GetEditionByIdUseCase getEditionByIdUseCase;
  private final UpdateEditionUseCase updateEditionUseCase;
  private final DeleteEditionUseCase deleteEditionUseCase;

  public EditionController(final EditionMapper editionMapper,
      final CreateEditionUseCase createEditionUseCase,
      final GetEditionsByWorkIdUseCase getEditionsByWorkIdUseCase,
      final GetEditionByIdUseCase getEditionByIdUseCase,
      final UpdateEditionUseCase updateEditionUseCase,
      final DeleteEditionUseCase deleteEditionUseCase) {
    this.editionMapper = editionMapper;
    this.createEditionUseCase = createEditionUseCase;
    this.getEditionsByWorkIdUseCase = getEditionsByWorkIdUseCase;
    this.getEditionByIdUseCase = getEditionByIdUseCase;
    this.updateEditionUseCase = updateEditionUseCase;
    this.deleteEditionUseCase = deleteEditionUseCase;
  }

  @Override
  public ResponseEntity<EditionDTO> createEdition(@NotNull final UUID workId,
      @Valid final EditionDTO editionDTO) {
    final var createEditionCommand = this.editionMapper.toCreateEditionCommand(workId, editionDTO);
    final var createdEdition = this.createEditionUseCase.execute(createEditionCommand);
    final var createdEditionDTO = this.editionMapper.toEditionDTO(createdEdition);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdEditionDTO);
  }

  @Override
  public ResponseEntity<GetEditionsByWorkId200ResponseDTO> getEditionsByWorkId(
      @NotNull final UUID workId) {
    final var editions = this.getEditionsByWorkIdUseCase.execute(workId);
    final var editionDTOs = this.editionMapper.toEditionDTOList(editions);
    final var response = new GetEditionsByWorkId200ResponseDTO().editions(editionDTOs);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<EditionDTO> getEditionById(@NotNull final UUID workId,
      @NotNull final UUID editionId) {
    final var getEditionByIdCommand = this.editionMapper.toGetEditionByIdCommand(workId, editionId);
    final var edition = this.getEditionByIdUseCase.execute(getEditionByIdCommand);
    final var editionDTO = this.editionMapper.toEditionDTO(edition);
    return ResponseEntity.ok(editionDTO);
  }

  @Override
  public ResponseEntity<EditionDTO> updateEditionById(@NotNull final UUID workId,
      @NotNull final UUID editionId, @Valid final EditionDTO editionDTO) {
    final var updateEditionCommand =
        this.editionMapper.toUpdateEditionCommand(workId, editionId, editionDTO);
    final var updatedEdition = this.updateEditionUseCase.execute(updateEditionCommand);
    final var updatedEditionDTO = this.editionMapper.toEditionDTO(updatedEdition);
    return ResponseEntity.ok(updatedEditionDTO);
  }

  @Override
  public ResponseEntity<Void> deleteEditionById(@NotNull final UUID workId,
      @NotNull final UUID editionId) {
    final var deleteEditionCommand = this.editionMapper.toDeleteEditionCommand(workId, editionId);
    this.deleteEditionUseCase.execute(deleteEditionCommand);
    return ResponseEntity.noContent().build();
  }

}
