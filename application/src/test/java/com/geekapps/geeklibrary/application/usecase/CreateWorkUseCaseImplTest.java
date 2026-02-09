package com.geekapps.geeklibrary.application.usecase;

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
import com.geekapps.geeklibrary.application.port.in.model.CreateWorkCommand;
import com.geekapps.geeklibrary.application.port.in.model.PersonTO;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.domain.port.out.PersonRepository;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateWorkUseCase Tests")
class CreateWorkUseCaseImplTest {

    @Mock
    private WorkRepository workRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private CreateWorkUseCaseImpl createWorkUseCase;

    private CreateWorkCommand command;
    private Work expectedWork;
    private Person author;
    private Person illustrator;

    @BeforeEach
    void setUp() {
        final var authorId = UUID.randomUUID();
        final var illustratorId = UUID.randomUUID();

        this.author = new Person(authorId, "Eiichiro", "Oda");
        this.illustrator = new Person(illustratorId, "Eiichiro", "Oda");

        final var authorCommand = new PersonTO(authorId, "Eiichiro", "Oda");
        final var illustratorCommand = new PersonTO(illustratorId, "Eiichiro", "Oda");

        this.command = new CreateWorkCommand(WorkType.MANGA, "One Piece", "A pirate adventure",
                authorCommand, illustratorCommand);

        this.expectedWork = new Work(UUID.randomUUID(), WorkType.MANGA, "One Piece",
                "A pirate adventure", this.author, this.illustrator);
    }

    @Test
    @DisplayName("Should create work successfully with existing persons")
    void shouldCreateWorkSuccessfullyWithExistingPersons() {
        // Given
        Mockito.when(this.personRepository.findById(this.command.author().id()))
                .thenReturn(this.author);
        Mockito.when(this.personRepository.findById(this.command.illustrator().id()))
                .thenReturn(this.illustrator);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(this.expectedWork);

        // When
        final var result = this.createWorkUseCase.execute(this.command);

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getType()).isEqualTo(WorkType.MANGA);
        Assertions.assertThat(result.getTitle()).isEqualTo("One Piece");
        Assertions.assertThat(result.getDescription()).isEqualTo("A pirate adventure");

        Mockito.verify(this.personRepository).findById(this.command.author().id());
        Mockito.verify(this.personRepository).findById(this.command.illustrator().id());
        Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
    }

    @Test
    @DisplayName("Should create work successfully with new persons")
    void shouldCreateWorkSuccessfullyWithNewPersons() {
        // Given
        final var authorCommand = new PersonTO(null, "New", "Author");
        final var illustratorCommand = new PersonTO(null, "New", "Illustrator");

        final var newAuthor = new Person("New", "Author");
        final var newIllustrator = new Person("New", "Illustrator");

        final var commandWithNewPersons = new CreateWorkCommand(WorkType.MANGA, "New Work",
                "Description", authorCommand, illustratorCommand);

        Mockito.when(this.personRepository.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(newAuthor).thenReturn(newIllustrator);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(this.expectedWork);

        // When
        final var result = this.createWorkUseCase.execute(commandWithNewPersons);

        // Then
        Assertions.assertThat(result).isNotNull();
        Mockito.verify(this.personRepository, Mockito.times(2))
                .save(ArgumentMatchers.any(Person.class));
        Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
    }

    @Test
    @DisplayName("Should call repository with correct Work object")
    void shouldCallRepositoryWithCorrectWorkObject() {
        // Given
        Mockito.when(this.personRepository.findById(this.command.author().id()))
                .thenReturn(this.author);
        Mockito.when(this.personRepository.findById(this.command.illustrator().id()))
                .thenReturn(this.illustrator);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(this.expectedWork);

        // When
        this.createWorkUseCase.execute(this.command);

        // Then
        Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
    }

    @Test
    @DisplayName("Should create work with null illustrator")
    void shouldCreateWorkWithNullIllustrator() {
        // Given
        final var authorCommand = new PersonTO(UUID.randomUUID(), "Reki", "Kawahara");
        final var commandWithoutIllustrator = new CreateWorkCommand(WorkType.LIGHT_NOVEL,
                "Sword Art Online", "VR adventure", authorCommand, null);

        final var authorPerson = new Person(authorCommand.id(), "Reki", "Kawahara");
        final var workWithoutIllustrator = new Work(UUID.randomUUID(), WorkType.LIGHT_NOVEL,
                "Sword Art Online", "VR adventure", authorPerson, null);

        Mockito.when(this.personRepository.findById(authorCommand.id())).thenReturn(authorPerson);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(workWithoutIllustrator);

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
        final var artistId = UUID.randomUUID();
        final var artistCommand = new PersonTO(artistId, "Artist", "Name");
        final var artbookCommand = new CreateWorkCommand(WorkType.ARTBOOK, "Art Collection",
                "Beautiful artwork", artistCommand, artistCommand);

        final var artist = new Person(artistId, "Artist", "Name");
        final var artbook = new Work(UUID.randomUUID(), WorkType.ARTBOOK, "Art Collection",
                "Beautiful artwork", artist, artist);

        Mockito.when(this.personRepository.findById(artistId)).thenReturn(artist);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(artbook);

        // When
        final var result = this.createWorkUseCase.execute(artbookCommand);

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getType()).isEqualTo(WorkType.ARTBOOK);
        Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
    }
}
