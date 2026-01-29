package port.usecase;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.model.UpdateWorkCommand;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateWorkUseCase Tests")
class UpdateWorkUseCaseImplTest {

  @Mock
  private WorkRepository workRepository;

  @InjectMocks
  private UpdateWorkUseCaseImpl updateWorkUseCase;

  private UUID workId;
  private UpdateWorkCommand command;
  private Work existingWork;
  private Work updatedWork;

  @BeforeEach
  void setUp() {
    this.workId = UUID.randomUUID();

    final var oldAuthor = new Person("Old", "Author");
    final var newAuthor = new Person("New", "Author");
    final var newIllustrator = new Person("New", "Illustrator");

    this.existingWork =
        new Work(this.workId, WorkType.MANGA, "Old Title", "Old Description", oldAuthor, oldAuthor);

    this.command = new UpdateWorkCommand(this.workId, WorkType.LIGHT_NOVEL, "Updated Title",
        "Updated Description", newAuthor, newIllustrator);

    this.updatedWork = new Work(this.workId, WorkType.LIGHT_NOVEL, "Updated Title", "Updated Description",
        newAuthor, newIllustrator);
  }

  @Test
  @DisplayName("Should update work successfully when work exists")
  void shouldUpdateWorkSuccessfullyWhenWorkExists() {
    // Given
    Mockito.when(this.workRepository.findById(this.workId)).thenReturn(this.existingWork);
    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(this.updatedWork);

    // When
    final var result = this.updateWorkUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(this.workId);
    Assertions.assertThat(result.getType()).isEqualTo(WorkType.LIGHT_NOVEL);
    Assertions.assertThat(result.getTitle()).isEqualTo("Updated Title");
    Assertions.assertThat(result.getDescription()).isEqualTo("Updated Description");

    Mockito.verify(this.workRepository).findById(this.workId);
    Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
  }

  @Test
  @DisplayName("Should return null when work does not exist")
  void shouldReturnNullWhenWorkDoesNotExist() {
    // Given
    Mockito.when(this.workRepository.findById(this.workId)).thenReturn(null);

    // When
    final var result = this.updateWorkUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNull();
    Mockito.verify(this.workRepository).findById(this.workId);
    Mockito.verify(this.workRepository, Mockito.never()).save(ArgumentMatchers.any(Work.class));
  }

  @Test
  @DisplayName("Should update work with null illustrator")
  void shouldUpdateWorkWithNullIllustrator() {
    // Given
    final var commandWithoutIllustrator = new UpdateWorkCommand(this.workId, WorkType.MANGA, "Title",
        "Description", new Person("Author", "Name"), null);

    final var updatedWorkWithoutIllustrator = new Work(this.workId, WorkType.MANGA, "Title",
        "Description", new Person("Author", "Name"), null);

    Mockito.when(this.workRepository.findById(this.workId)).thenReturn(this.existingWork);
    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(updatedWorkWithoutIllustrator);

    // When
    final var result = this.updateWorkUseCase.execute(commandWithoutIllustrator);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getIllustrator()).isNull();
    Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
  }

  @Test
  @DisplayName("Should preserve work ID when updating")
  void shouldPreserveWorkIdWhenUpdating() {
    // Given
    Mockito.when(this.workRepository.findById(this.workId)).thenReturn(this.existingWork);
    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(this.updatedWork);

    // When
    final var result = this.updateWorkUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(this.workId);
    Assertions.assertThat(result.getId()).isEqualTo(this.existingWork.getId());
  }
}
