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
import port.in.DeleteWorkByIdUseCase;
import port.in.GetWorkByIdUseCase;
import port.in.QueryWorksUseCase;
import port.in.UpdateWorkUseCase;

@RestController
public class WorkContoller implements WorksApi {

  private final WorkMapper workMapper;

  private final CreateWorkUseCase createWorkUseCase;

  private final QueryWorksUseCase queryWorksUseCase;

  private final GetWorkByIdUseCase getWorkByIdUseCase;

  private final UpdateWorkUseCase updateWorkUseCase;

  private final DeleteWorkByIdUseCase deleteWorkByIdUseCase;

  public WorkContoller(final WorkMapper workMapper, final CreateWorkUseCase createWorkUseCase,
      final QueryWorksUseCase queryWorksUseCase, final GetWorkByIdUseCase getWorkByIdUseCase,
      final UpdateWorkUseCase updateWorkUseCase,
      final DeleteWorkByIdUseCase deleteWorkByIdUseCase) {
    this.workMapper = workMapper;
    this.createWorkUseCase = createWorkUseCase;
    this.queryWorksUseCase = queryWorksUseCase;
    this.getWorkByIdUseCase = getWorkByIdUseCase;
    this.updateWorkUseCase = updateWorkUseCase;
    this.deleteWorkByIdUseCase = deleteWorkByIdUseCase;
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
    this.deleteWorkByIdUseCase.execute(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<WorkDTO> getWorkById(@NotNull final UUID id) {
    final var work = this.getWorkByIdUseCase.execute(id);
    final var workDTO = this.workMapper.toWorkDTO(work);
    return ResponseEntity.ok().body(workDTO);
  }

  @Override
  public ResponseEntity<QueryWorks200ResponseDTO> queryWorks(@Nullable @Valid final String title,
      @Nullable @Valid final String author) {
    final var queryWorksCommand = this.workMapper.toQueryWorksCommand(title, author);
    final var works = this.queryWorksUseCase.execute(queryWorksCommand);
    final var workDTOs = this.workMapper.toWorkDTOList(works);
    final var response = new QueryWorks200ResponseDTO().works(workDTOs);
    return ResponseEntity.ok().body(response);
  }

  @Override
  public ResponseEntity<WorkDTO> updateWorkById(@NotNull final UUID id, @Valid final WorkDTO work) {
    final var updateWorkCommand = this.workMapper.toUpdateWorkCommand(id, work);
    final var updatedWork = this.updateWorkUseCase.execute(updateWorkCommand);
    if (updatedWork == null) {
      return ResponseEntity.notFound().build();
    }
    final var updatedWorkDTO = this.workMapper.toWorkDTO(updatedWork);
    return ResponseEntity.ok().body(updatedWorkDTO);
  }

}
