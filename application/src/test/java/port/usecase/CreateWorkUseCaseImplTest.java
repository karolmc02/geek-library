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
import port.in.model.CreateWorkCommand;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateWorkUseCase Tests")
class CreateWorkUseCaseImplTest {

  @Mock
  private WorkRepository workRepository;

  @InjectMocks
  private CreateWorkUseCaseImpl createWorkUseCase;

  private CreateWorkCommand command;
  private Work expectedWork;

  @BeforeEach
  void setUp() {
    final var author = new Person("Eiichiro", "Oda");
    final var illustrator = new Person("Eiichiro", "Oda");

    this.command = new CreateWorkCommand(WorkType.MANGA, "One Piece", "A pirate adventure", author,
        illustrator);

    this.expectedWork = new Work(UUID.randomUUID(), WorkType.MANGA, "One Piece", "A pirate adventure",
        author, illustrator);
  }

  @Test
  @DisplayName("Should create work successfully")
  void shouldCreateWorkSuccessfully() {
    // Given
    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(this.expectedWork);

    // When
    final var result = this.createWorkUseCase.execute(this.command);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getType()).isEqualTo(WorkType.MANGA);
    Assertions.assertThat(result.getTitle()).isEqualTo("One Piece");
    Assertions.assertThat(result.getDescription()).isEqualTo("A pirate adventure");
    Assertions.assertThat(result.getAuthor()).isEqualTo(this.command.author());
    Assertions.assertThat(result.getIllustrator()).isEqualTo(this.command.illustrator());

    Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
  }

  @Test
  @DisplayName("Should call repository with correct Work object")
  void shouldCallRepositoryWithCorrectWorkObject() {
    // Given
    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(this.expectedWork);

    // When
    this.createWorkUseCase.execute(this.command);

    // Then
    Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
  }

  @Test
  @DisplayName("Should create work with null illustrator")
  void shouldCreateWorkWithNullIllustrator() {
    // Given
    final var commandWithoutIllustrator = new CreateWorkCommand(WorkType.LIGHT_NOVEL,
        "Sword Art Online", "VR adventure", new Person("Reki", "Kawahara"), null);

    final var workWithoutIllustrator = new Work(UUID.randomUUID(), WorkType.LIGHT_NOVEL,
        "Sword Art Online", "VR adventure", new Person("Reki", "Kawahara"), null);

    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(workWithoutIllustrator);

    // When
    final var result = this.createWorkUseCase.execute(commandWithoutIllustrator);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getIllustrator()).isNull();
    Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
  }

  @Test
  @DisplayName("Should create work of type ARTBOOK")
  void shouldCreateWorkOfTypeArtbook() {
    // Given
    final var artbookCommand = new CreateWorkCommand(WorkType.ARTBOOK, "Art Collection",
        "Beautiful artwork", new Person("Artist", "Name"), new Person("Artist", "Name"));

    final var artbook = new Work(UUID.randomUUID(), WorkType.ARTBOOK, "Art Collection",
        "Beautiful artwork", new Person("Artist", "Name"), new Person("Artist", "Name"));

    Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class))).thenReturn(artbook);

    // When
    final var result = this.createWorkUseCase.execute(artbookCommand);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getType()).isEqualTo(WorkType.ARTBOOK);
    Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
  }
}
