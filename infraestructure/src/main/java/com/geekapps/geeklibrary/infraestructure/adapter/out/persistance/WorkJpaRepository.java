package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity.WorkEntity;

interface WorkJpaRepository extends JpaRepository<WorkEntity, UUID> {

  @Query("""
      SELECT w FROM WorkEntity w WHERE \
      (:title IS NULL OR :title = '' OR LOWER(w.title) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%'))) AND \
      (:author IS NULL OR :author = '' OR LOWER(w.author.firstName) LIKE LOWER(CONCAT('%', CAST(:author AS string), '%')) \
      OR LOWER(w.author.lastName) LIKE LOWER(CONCAT('%', CAST(:author AS string), '%')))""")
  List<WorkEntity> query(@Param("title") String title, @Param("author") String author);

}
