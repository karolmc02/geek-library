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
import com.geekapps.geeklibrary.application.port.in.model.PersonTO;
import com.geekapps.geeklibrary.application.port.in.model.UpdateWorkCommand;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import com.geekapps.geeklibrary.domain.port.out.PersonRepository;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateWorkUseCase Tests")
class UpdateWorkUseCaseImplTest {

    @Mock
    private WorkRepository workRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private UpdateWorkUseCaseImpl updateWorkUseCase;

    private UUID workId;
    private UpdateWorkCommand command;
    private Work existingWork;
    private Work updatedWork;
    private Person oldAuthor;
    private Person newAuthor;
    private Person newIllustrator;

    @BeforeEach
    void setUp() {
        this.workId = UUID.randomUUID();

        final var oldAuthorId = UUID.randomUUID();
        final var newAuthorId = UUID.randomUUID();
        final var newIllustratorId = UUID.randomUUID();

        this.oldAuthor = new Person(oldAuthorId, "Old", "Author");
        this.newAuthor = new Person(newAuthorId, "New", "Author");
        this.newIllustrator = new Person(newIllustratorId, "New", "Illustrator");

        this.existingWork = new Work(this.workId, WorkType.MANGA, "Old Title", "Old Description",
                this.oldAuthor, this.oldAuthor);

        final var newAuthorCommand = new PersonTO(newAuthorId, "New", "Author");
        final var newIllustratorCommand = new PersonTO(newIllustratorId, "New", "Illustrator");

        this.command = new UpdateWorkCommand(this.workId, WorkType.LIGHT_NOVEL, "Updated Title",
                "Updated Description", newAuthorCommand, newIllustratorCommand);

        this.updatedWork = new Work(this.workId, WorkType.LIGHT_NOVEL, "Updated Title",
                "Updated Description", this.newAuthor, this.newIllustrator);
    }

    @Test
    @DisplayName("Should update work successfully when work exists")
    void shouldUpdateWorkSuccessfullyWhenWorkExists() {
        // Given
        Mockito.when(this.workRepository.findById(this.workId)).thenReturn(this.existingWork);
        Mockito.when(this.personRepository.findById(this.command.author().id()))
                .thenReturn(this.newAuthor);
        Mockito.when(this.personRepository.findById(this.command.illustrator().id()))
                .thenReturn(this.newIllustrator);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(this.updatedWork);

        // When
        final var result = this.updateWorkUseCase.execute(this.command);

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(this.workId);
        Assertions.assertThat(result.getType()).isEqualTo(WorkType.LIGHT_NOVEL);
        Assertions.assertThat(result.getTitle()).isEqualTo("Updated Title");
        Assertions.assertThat(result.getDescription()).isEqualTo("Updated Description");

        Mockito.verify(this.workRepository).findById(this.workId);
        Mockito.verify(this.personRepository).findById(this.command.author().id());
        Mockito.verify(this.personRepository).findById(this.command.illustrator().id());
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
        final var authorId = UUID.randomUUID();
        final var authorCommand = new PersonTO(authorId, "Author", "Name");
        final var commandWithoutIllustrator = new UpdateWorkCommand(this.workId, WorkType.MANGA,
                "Title", "Description", authorCommand, null);

        final var author = new Person(authorId, "Author", "Name");
        final var updatedWorkWithoutIllustrator =
                new Work(this.workId, WorkType.MANGA, "Title", "Description", author, null);

        Mockito.when(this.workRepository.findById(this.workId)).thenReturn(this.existingWork);
        Mockito.when(this.personRepository.findById(authorId)).thenReturn(author);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(updatedWorkWithoutIllustrator);

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
        Mockito.when(this.personRepository.findById(this.command.author().id()))
                .thenReturn(this.newAuthor);
        Mockito.when(this.personRepository.findById(this.command.illustrator().id()))
                .thenReturn(this.newIllustrator);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(this.updatedWork);

        // When
        final var result = this.updateWorkUseCase.execute(this.command);

        // Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(this.workId);
        Assertions.assertThat(result.getId()).isEqualTo(this.existingWork.getId());
    }

    @Test
    @DisplayName("Should create new person if not found by id")
    void shouldCreateNewPersonIfNotFoundById() {
        // Given
        final var newPersonId = UUID.randomUUID();
        final var personCommand = new PersonTO(newPersonId, "New", "Person");
        final var commandWithNewPerson = new UpdateWorkCommand(this.workId, WorkType.MANGA, "Title",
                "Description", personCommand, null);

        final var newPerson = new Person(newPersonId, "New", "Person");
        final var updatedWorkWithNewPerson =
                new Work(this.workId, WorkType.MANGA, "Title", "Description", newPerson, null);

        Mockito.when(this.workRepository.findById(this.workId)).thenReturn(this.existingWork);
        Mockito.when(this.personRepository.findById(newPersonId)).thenReturn(null);
        Mockito.when(this.personRepository.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(newPerson);
        Mockito.when(this.workRepository.save(ArgumentMatchers.any(Work.class)))
                .thenReturn(updatedWorkWithNewPerson);

        // When
        final var result = this.updateWorkUseCase.execute(commandWithNewPerson);

        // Then
        Assertions.assertThat(result).isNotNull();
        Mockito.verify(this.personRepository).findById(newPersonId);
        Mockito.verify(this.personRepository).save(ArgumentMatchers.any(Person.class));
        Mockito.verify(this.workRepository).save(ArgumentMatchers.any(Work.class));
    }
}
