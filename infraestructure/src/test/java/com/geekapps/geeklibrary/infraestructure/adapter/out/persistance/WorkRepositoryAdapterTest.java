package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.PersonEmbeddable;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.WorkEntity;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper.WorkEntityMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorkRepositoryAdapter Tests")
class WorkRepositoryAdapterTest {

  @Mock
  private WorkJpaRepository workJpaRepository;

  @Mock
  private WorkEntityMapper workEntityMapper;

  @InjectMocks
  private WorkRepositoryAdapter workRepositoryAdapter;

  private Work work;
  private WorkEntity workEntity;
  private UUID workId;

  @BeforeEach
  void setUp() {
    this.workId = UUID.randomUUID();

    final var person = new Person("Eiichiro", "Oda");
    this.work = new Work(this.workId, WorkType.MANGA, "One Piece", "A pirate adventure", person, person);

    this.workEntity = new WorkEntity();
    this.workEntity.setId(this.workId);
    this.workEntity.setType(WorkType.MANGA);
    this.workEntity.setTitle("One Piece");
    this.workEntity.setDescription("A pirate adventure");

    final var personEmbeddable = new PersonEmbeddable();
    personEmbeddable.setFirstName("Eiichiro");
    personEmbeddable.setLastName("Oda");
    this.workEntity.setAuthor(personEmbeddable);
    this.workEntity.setIllustrator(personEmbeddable);
  }

  @Test
  @DisplayName("Should save work successfully")
  void shouldSaveWorkSuccessfully() {
    // Given
    Mockito.when(this.workEntityMapper.toEntity(this.work)).thenReturn(this.workEntity);
    Mockito.when(this.workJpaRepository.save(this.workEntity)).thenReturn(this.workEntity);
    Mockito.when(this.workEntityMapper.toDomain(this.workEntity)).thenReturn(this.work);

    // When
    final var result = this.workRepositoryAdapter.save(this.work);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(this.workId);
    Assertions.assertThat(result.getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workEntityMapper).toEntity(this.work);
    Mockito.verify(this.workJpaRepository).save(this.workEntity);
    Mockito.verify(this.workEntityMapper).toDomain(this.workEntity);
  }

  @Test
  @DisplayName("Should find work by ID when it exists")
  void shouldFindWorkByIdWhenItExists() {
    // Given
    Mockito.when(this.workJpaRepository.findById(this.workId)).thenReturn(Optional.of(this.workEntity));
    Mockito.when(this.workEntityMapper.toDomain(this.workEntity)).thenReturn(this.work);

    // When
    final var result = this.workRepositoryAdapter.findById(this.workId);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(this.workId);
    Assertions.assertThat(result.getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workJpaRepository).findById(this.workId);
    Mockito.verify(this.workEntityMapper).toDomain(this.workEntity);
  }

  @Test
  @DisplayName("Should return null when work not found by ID")
  void shouldReturnNullWhenWorkNotFoundById() {
    // Given
    Mockito.when(this.workJpaRepository.findById(this.workId)).thenReturn(Optional.empty());

    // When
    final var result = this.workRepositoryAdapter.findById(this.workId);

    // Then
    Assertions.assertThat(result).isNull();

    Mockito.verify(this.workJpaRepository).findById(this.workId);
  }

  @Test
  @DisplayName("Should delete work by ID")
  void shouldDeleteWorkById() {
    // When
    this.workRepositoryAdapter.deleteById(this.workId);

    // Then
    Mockito.verify(this.workJpaRepository).deleteById(this.workId);
  }

  @Test
  @DisplayName("Should query works with title")
  void shouldQueryWorksWithTitle() {
    // Given
    final var title = "One Piece";
    final var entities = List.of(this.workEntity);

    Mockito.when(this.workJpaRepository.query(title, null)).thenReturn(entities);
    Mockito.when(this.workEntityMapper.toDomain(this.workEntity)).thenReturn(this.work);

    // When
    final var result = this.workRepositoryAdapter.query(title, null);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(1);
    Assertions.assertThat(result.get(0).getTitle()).isEqualTo("One Piece");

    Mockito.verify(this.workJpaRepository).query(title, null);
    Mockito.verify(this.workEntityMapper).toDomain(this.workEntity);
  }

  @Test
  @DisplayName("Should query works with author")
  void shouldQueryWorksWithAuthor() {
    // Given
    final var author = "Oda";
    final var entities = List.of(this.workEntity);

    Mockito.when(this.workJpaRepository.query(null, author)).thenReturn(entities);
    Mockito.when(this.workEntityMapper.toDomain(this.workEntity)).thenReturn(this.work);

    // When
    final var result = this.workRepositoryAdapter.query(null, author);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(1);

    Mockito.verify(this.workJpaRepository).query(null, author);
    Mockito.verify(this.workEntityMapper).toDomain(this.workEntity);
  }

  @Test
  @DisplayName("Should query works with both title and author")
  void shouldQueryWorksWithBothTitleAndAuthor() {
    // Given
    final var title = "One Piece";
    final var author = "Oda";
    final var entities = List.of(this.workEntity);

    Mockito.when(this.workJpaRepository.query(title, author)).thenReturn(entities);
    Mockito.when(this.workEntityMapper.toDomain(this.workEntity)).thenReturn(this.work);

    // When
    final var result = this.workRepositoryAdapter.query(title, author);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(1);

    Mockito.verify(this.workJpaRepository).query(title, author);
    Mockito.verify(this.workEntityMapper).toDomain(this.workEntity);
  }

  @Test
  @DisplayName("Should return empty list when no works found in query")
  void shouldReturnEmptyListWhenNoWorksFoundInQuery() {
    // Given
    Mockito.when(this.workJpaRepository.query(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(List.of());

    // When
    final var result = this.workRepositoryAdapter.query("Unknown", "Unknown");

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isEmpty();

    Mockito.verify(this.workJpaRepository).query("Unknown", "Unknown");
  }

  @Test
  @DisplayName("Should query multiple works")
  void shouldQueryMultipleWorks() {
    // Given
    final var work2 = new Work(UUID.randomUUID(), WorkType.MANGA, "Naruto", "Ninja story",
        new Person("Masashi", "Kishimoto"), new Person("Masashi", "Kishimoto"));

    final var workEntity2 = new WorkEntity();
    workEntity2.setId(UUID.randomUUID());
    workEntity2.setType(WorkType.MANGA);
    workEntity2.setTitle("Naruto");

    final var entities = Arrays.asList(this.workEntity, workEntity2);

    Mockito.when(this.workJpaRepository.query(null, null)).thenReturn(entities);
    Mockito.when(this.workEntityMapper.toDomain(this.workEntity)).thenReturn(this.work);
    Mockito.when(this.workEntityMapper.toDomain(workEntity2)).thenReturn(work2);

    // When
    final var result = this.workRepositoryAdapter.query(null, null);

    // Then
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).hasSize(2);
    Assertions.assertThat(result.get(0).getTitle()).isEqualTo("One Piece");
    Assertions.assertThat(result.get(1).getTitle()).isEqualTo("Naruto");

    Mockito.verify(this.workJpaRepository).query(null, null);
  }
}
