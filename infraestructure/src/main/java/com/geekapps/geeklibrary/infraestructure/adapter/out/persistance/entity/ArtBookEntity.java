package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(WorkEntityType.ARTBOOK)
public class ArtBookEntity extends WorkEntity {
  // No extra fields for now
}
