package com.geekapps.geeklibrary.infraestructure.adapter.in.rest;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import com.geekapps.geeklibrary.client.api.api.WorksApi;
import com.geekapps.geeklibrary.client.api.model.QueryWorks200ResponseDTO;
import com.geekapps.geeklibrary.client.api.model.WorkDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class WorkContoller implements WorksApi {

  @Override
  public ResponseEntity<WorkDTO> createWork(@Valid final WorkDTO work) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createWork'");
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
