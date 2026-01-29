package port.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetWorkByIdUseCase Tests")
class GetWorkByIdUseCaseImplTest {

  @Mock
  private WorkRepository workRepository;

  @InjectMocks
  private GetWorkByIdUseCaseImpl getWorkByIdUseCase;

  @Test
  @DisplayName("Should return work when it exists")
  void shouldReturnWorkWhenItExists() {
    // Given
    final var workId = UUID.randomUUID();
    final var author = new Person("Eiichiro", "Oda");
    final var work =
        new Work(workId, WorkType.MANGA, "One Piece", "A pirate adventure", author, author);

    when(workRepository.findById(workId)).thenReturn(work);

    // When
    final var result = getWorkByIdUseCase.execute(workId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(workId);
    assertThat(result.getTitle()).isEqualTo("One Piece");
    assertThat(result.getType()).isEqualTo(WorkType.MANGA);

    verify(workRepository).findById(workId);
  }

  @Test
  @DisplayName("Should return null when work does not exist")
  void shouldReturnNullWhenWorkDoesNotExist() {
    // Given
    final var workId = UUID.randomUUID();
    when(workRepository.findById(workId)).thenReturn(null);

    // When
    final var result = getWorkByIdUseCase.execute(workId);

    // Then
    assertThat(result).isNull();
    verify(workRepository).findById(workId);
  }

  @Test
  @DisplayName("Should call repository with correct ID")
  void shouldCallRepositoryWithCorrectId() {
    // Given
    final var workId = UUID.randomUUID();
    when(workRepository.findById(workId)).thenReturn(null);

    // When
    getWorkByIdUseCase.execute(workId);

    // Then
    verify(workRepository).findById(workId);
  }

  @Test
  @DisplayName("Should return work with all properties")
  void shouldReturnWorkWithAllProperties() {
    // Given
    final var workId = UUID.randomUUID();
    final var author = new Person("Masashi", "Kishimoto");
    final var illustrator = new Person("Masashi", "Kishimoto");
    final var work = new Work(workId, WorkType.MANGA, "Naruto", "Ninja story", author, illustrator);

    when(workRepository.findById(workId)).thenReturn(work);

    // When
    final var result = getWorkByIdUseCase.execute(workId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(workId);
    assertThat(result.getType()).isEqualTo(WorkType.MANGA);
    assertThat(result.getTitle()).isEqualTo("Naruto");
    assertThat(result.getDescription()).isEqualTo("Ninja story");
    assertThat(result.getAuthor()).isEqualTo(author);
    assertThat(result.getIllustrator()).isEqualTo(illustrator);
  }
}
