package com.geekapps.geeklibrary.infraestructure.adapter.in.rest;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestController;
import com.geekapps.geeklibrary.client.api.api.WorksApi;
import com.geekapps.geeklibrary.client.api.model.QueryWorks200ResponseDTO;
import com.geekapps.geeklibrary.client.api.model.WorkDTO;
import com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper.WorkMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import port.in.CreateWorkUseCase;

@RestController
public class WorkContoller implements WorksApi {

  private final WorkMapper workMapper;

  private final CreateWorkUseCase createWorkUseCase;

  public WorkContoller(final WorkMapper workMapper, final CreateWorkUseCase createWorkUseCase) {
    this.workMapper = workMapper;
    this.createWorkUseCase = createWorkUseCase;
  }

  @Override
  public ResponseEntity<WorkDTO> createWork(@Valid final WorkDTO workDTO) {
    final var createWorkCommand = this.workMapper.toCreateWorkCommand(workDTO);
    final var createdWork = this.createWorkUseCase.execute(createWorkCommand);
    final var createdWorkDTO = this.workMapper.toWorkDTO(createdWork);
    return ResponseEntity.ok().body(createdWorkDTO);
  }

  @Override
  public ResponseEntity<Void> deleteWorkById(@NotNull final UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteWorkById'");
  }

  @Override
  public ResponseEntity<WorkDTO> getWorkById(@NotNull final UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getWorkById'");
  }

  @Override
  public ResponseEntity<QueryWorks200ResponseDTO> queryWorks(@Nullable @Valid final String title,
      @Nullable @Valid final String author) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'queryWorks'");
  }

  @Override
  public ResponseEntity<WorkDTO> updateWorkById(@NotNull final UUID id, @Valid final WorkDTO work) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateWorkById'");
  }

}
