package com.geekapps.geeklibrary.infraestructure.adapter.in.rest;

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
import com.geekapps.geeklibrary.client.api.model.PersonDTO;
import com.geekapps.geeklibrary.client.api.model.WorkDTO;
import com.geekapps.geeklibrary.client.api.model.WorkTypeDTO;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.infraestructure.adapter.in.rest.mapper.WorkMapper;
import port.in.CreateWorkUseCase;
import port.in.DeleteWorkByIdUseCase;
import port.in.GetWorkByIdUseCase;
import port.in.QueryWorksUseCase;
import port.in.UpdateWorkUseCase;
import port.in.model.CreateWorkCommand;
import port.in.model.QueryWorksCommand;
import port.in.model.UpdateWorkCommand;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkController Tests")
class WorkContollerTest {

  @Mock
  private WorkMapper workMapper;

  @Mock
  private CreateWorkUseCase createWorkUseCase;

  @Mock
  private QueryWorksUseCase queryWorksUseCase;

  @Mock
  private GetWorkByIdUseCase getWorkByIdUseCase;

  @Mock
  private UpdateWorkUseCase updateWorkUseCase;

  @Mock
  private DeleteWorkByIdUseCase deleteWorkByIdUseCase;

  @InjectMocks
  private WorkContoller workController;

  private WorkDTO workDTO;
  private Work work;
  private UUID workId;

  @BeforeEach
  void setUp() {
    this.workId = UUID.randomUUID();

    final var personDTO = new PersonDTO().firstName("Eiichiro").lastName("Oda");
    this.workDTO = new WorkDTO().type(WorkTypeDTO.MANGA).title("One Piece")
        .description("A pirate adventure").author(personDTO).illustrator(personDTO);

    final var person = new Person("Eiichiro", "Oda");
    this.work = new Work(this.workId, WorkType.MANGA, "One Piece", "A pirate adventure", person, person);
  }

  @Test
  @DisplayName("Should create work successfully")
  void shouldCreateWorkSuccessfully() {
    // Given
    final var createCommand = new CreateWorkCommand(WorkType.MANGA, "One Piece",
        "A pirate adventure", new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));

    Mockito.when(this.workMapper.toCreateWorkCommand(this.workDTO)).thenReturn(createCommand);
    Mockito.when(this.createWorkUseCase.execute(createCommand)).thenReturn(this.work);
    Mockito.when(this.workMapper.toWorkDTO(this.work)).thenReturn(this.workDTO);

    // When
    final var response = this.workController.createWork(this.workDTO);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workMapper).toCreateWorkCommand(this.workDTO);
    Mockito.verify(this.createWorkUseCase).execute(createCommand);
    Mockito.verify(this.workMapper).toWorkDTO(this.work);
  }

  @Test
  @DisplayName("Should get work by ID successfully")
  void shouldGetWorkByIdSuccessfully() {
    // Given
    Mockito.when(this.getWorkByIdUseCase.execute(this.workId)).thenReturn(this.work);
    Mockito.when(this.workMapper.toWorkDTO(this.work)).thenReturn(this.workDTO);

    // When
    final var response = this.workController.getWorkById(this.workId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.getWorkByIdUseCase).execute(this.workId);
    Mockito.verify(this.workMapper).toWorkDTO(this.work);
  }

  @Test
  @DisplayName("Should return 404 when work not found by ID")
  void shouldReturn404WhenWorkNotFoundById() {
    // Given
    Mockito.when(this.getWorkByIdUseCase.execute(this.workId)).thenReturn(null);

    // When
    final var response = this.workController.getWorkById(this.workId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    Assertions.assertThat(response.getBody()).isNull();

    Mockito.verify(this.getWorkByIdUseCase).execute(this.workId);
  }

  @Test
  @DisplayName("Should update work successfully")
  void shouldUpdateWorkSuccessfully() {
    // Given
    final var updateCommand = new UpdateWorkCommand(this.workId, WorkType.MANGA, "One Piece",
        "A pirate adventure", new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));

    Mockito.when(this.workMapper.toUpdateWorkCommand(this.workId, this.workDTO)).thenReturn(updateCommand);
    Mockito.when(this.updateWorkUseCase.execute(updateCommand)).thenReturn(this.work);
    Mockito.when(this.workMapper.toWorkDTO(this.work)).thenReturn(this.workDTO);

    // When
    final var response = this.workController.updateWorkById(this.workId, this.workDTO);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workMapper).toUpdateWorkCommand(this.workId, this.workDTO);
    Mockito.verify(this.updateWorkUseCase).execute(updateCommand);
    Mockito.verify(this.workMapper).toWorkDTO(this.work);
  }

  @Test
  @DisplayName("Should return 404 when updating non-existent work")
  void shouldReturn404WhenUpdatingNonExistentWork() {
    // Given
    final var updateCommand = new UpdateWorkCommand(this.workId, WorkType.MANGA, "One Piece",
        "A pirate adventure", new Person("Eiichiro", "Oda"), new Person("Eiichiro", "Oda"));

    Mockito.when(this.workMapper.toUpdateWorkCommand(this.workId, this.workDTO)).thenReturn(updateCommand);
    Mockito.when(this.updateWorkUseCase.execute(updateCommand)).thenReturn(null);

    // When
    final var response = this.workController.updateWorkById(this.workId, this.workDTO);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    Assertions.assertThat(response.getBody()).isNull();

    Mockito.verify(this.workMapper).toUpdateWorkCommand(this.workId, this.workDTO);
    Mockito.verify(this.updateWorkUseCase).execute(updateCommand);
  }

  @Test
  @DisplayName("Should delete work successfully")
  void shouldDeleteWorkSuccessfully() {
    // When
    final var response = this.workController.deleteWorkById(this.workId);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    Assertions.assertThat(response.getBody()).isNull();

    Mockito.verify(this.deleteWorkByIdUseCase).execute(this.workId);
  }

  @Test
  @DisplayName("Should query works with title filter")
  void shouldQueryWorksWithTitleFilter() {
    // Given
    final var title = "One Piece";
    final var queryCommand = new QueryWorksCommand(title, null);
    final var works = List.of(this.work);

    Mockito.when(this.workMapper.toQueryWorksCommand(title, null)).thenReturn(queryCommand);
    Mockito.when(this.queryWorksUseCase.execute(queryCommand)).thenReturn(works);
    Mockito.when(this.workMapper.toWorkDTOList(works)).thenReturn(List.of(this.workDTO));

    // When
    final var response = this.workController.queryWorks(title, null);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getWorks()).hasSize(1);
    Assertions.assertThat(response.getBody().getWorks().get(0).getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workMapper).toQueryWorksCommand(title, null);
    Mockito.verify(this.queryWorksUseCase).execute(queryCommand);
    Mockito.verify(this.workMapper).toWorkDTOList(works);
  }

  @Test
  @DisplayName("Should query works with author filter")
  void shouldQueryWorksWithAuthorFilter() {
    // Given
    final var author = "Oda";
    final var queryCommand = new QueryWorksCommand(null, author);
    final var works = List.of(this.work);

    Mockito.when(this.workMapper.toQueryWorksCommand(null, author)).thenReturn(queryCommand);
    Mockito.when(this.queryWorksUseCase.execute(queryCommand)).thenReturn(works);
    Mockito.when(this.workMapper.toWorkDTOList(works)).thenReturn(List.of(this.workDTO));

    // When
    final var response = this.workController.queryWorks(null, author);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getWorks()).hasSize(1);

    Mockito.verify(this.workMapper).toQueryWorksCommand(null, author);
    Mockito.verify(this.queryWorksUseCase).execute(queryCommand);
  }

  @Test
  @DisplayName("Should query works with both filters")
  void shouldQueryWorksWithBothFilters() {
    // Given
    final var title = "One Piece";
    final var author = "Oda";
    final var queryCommand = new QueryWorksCommand(title, author);
    final var works = List.of(this.work);

    Mockito.when(this.workMapper.toQueryWorksCommand(title, author)).thenReturn(queryCommand);
    Mockito.when(this.queryWorksUseCase.execute(queryCommand)).thenReturn(works);
    Mockito.when(this.workMapper.toWorkDTOList(works)).thenReturn(List.of(this.workDTO));

    // When
    final var response = this.workController.queryWorks(title, author);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getWorks()).hasSize(1);

    Mockito.verify(this.workMapper).toQueryWorksCommand(title, author);
    Mockito.verify(this.queryWorksUseCase).execute(queryCommand);
  }

  @Test
  @DisplayName("Should return empty list when no works found")
  void shouldReturnEmptyListWhenNoWorksFound() {
    // Given
    final var queryCommand = new QueryWorksCommand(null, null);
    final List<Work> emptyList = Collections.emptyList();

    Mockito.when(this.workMapper.toQueryWorksCommand(null, null)).thenReturn(queryCommand);
    Mockito.when(this.queryWorksUseCase.execute(queryCommand)).thenReturn(emptyList);
    Mockito.when(this.workMapper.toWorkDTOList(emptyList)).thenReturn(Collections.emptyList());

    // When
    final var response = this.workController.queryWorks(null, null);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getWorks()).isEmpty();

    Mockito.verify(this.workMapper).toQueryWorksCommand(null, null);
    Mockito.verify(this.queryWorksUseCase).execute(queryCommand);
  }

  @Test
  @DisplayName("Should query multiple works")
  void shouldQueryMultipleWorks() {
    // Given
    final var work2 = new Work(UUID.randomUUID(), WorkType.MANGA, "Naruto", "Ninja story",
        new Person("Masashi", "Kishimoto"), new Person("Masashi", "Kishimoto"));

    final var workDTO2 =
        new WorkDTO().type(WorkTypeDTO.MANGA).title("Naruto").description("Ninja story");

    final var queryCommand = new QueryWorksCommand(null, null);
    final var works = Arrays.asList(this.work, work2);

    Mockito.when(this.workMapper.toQueryWorksCommand(null, null)).thenReturn(queryCommand);
    Mockito.when(this.queryWorksUseCase.execute(queryCommand)).thenReturn(works);
    Mockito.when(this.workMapper.toWorkDTOList(works)).thenReturn(Arrays.asList(this.workDTO, workDTO2));

    // When
    final var response = this.workController.queryWorks(null, null);

    // Then
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().getWorks()).hasSize(2);

    Mockito.verify(this.workMapper).toQueryWorksCommand(null, null);
    Mockito.verify(this.queryWorksUseCase).execute(queryCommand);
  }
}
