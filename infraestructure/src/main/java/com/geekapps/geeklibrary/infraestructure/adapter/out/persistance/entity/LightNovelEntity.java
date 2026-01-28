package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(WorkEntityType.LIGHT_NOVEL)
public class LightNovelEntity extends WorkEntity {
  // No extra fields for now
}
