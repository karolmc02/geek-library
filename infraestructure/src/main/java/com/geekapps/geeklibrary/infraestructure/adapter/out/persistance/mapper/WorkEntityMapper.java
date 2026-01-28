package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.mapper;

import org.mapstruct.Mapper;
import com.geekapps.geeklibrary.domain.model.work.ArtBook;
import com.geekapps.geeklibrary.domain.model.work.LightNovel;
import com.geekapps.geeklibrary.domain.model.work.Manga;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.ArtBookEntity;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.LightNovelEntity;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.MangaEntity;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.WorkEntity;

@Mapper
public interface WorkEntityMapper {

  default Work toDomain(final WorkEntity workEntity) {
    return switch (workEntity) {
      case final MangaEntity mangaEntity -> this.toDomainManga(mangaEntity);
      case final LightNovelEntity lightNovelEntity -> this.toDomainLightNovel(lightNovelEntity);
      case final ArtBookEntity artBookEntity -> this.toDomainArtBook(artBookEntity);
      default -> throw new IllegalArgumentException(
          "Unknown WorkEntity subtype: " + workEntity.getClass().getName());
    };
  }

  Manga toDomainManga(MangaEntity mangaEntity);

  LightNovel toDomainLightNovel(LightNovelEntity lightNovelEntity);

  ArtBook toDomainArtBook(ArtBookEntity artBookEntity);

  default WorkEntity toEntity(final Work work) {
    return switch (work) {
      case final Manga manga -> this.toEntityManga(manga);
      case final LightNovel lightNovel -> this.toEntityLightNovel(lightNovel);
      case final ArtBook artBook -> this.toEntityArtBook(artBook);
      default -> throw new IllegalArgumentException(
          "Unknown Work subtype: " + work.getClass().getName());
    };
  }

  MangaEntity toEntityManga(Manga manga);

  LightNovelEntity toEntityLightNovel(LightNovel lightNovel);

  ArtBookEntity toEntityArtBook(ArtBook artBook);

}
