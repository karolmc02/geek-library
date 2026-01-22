package com.geekapps.geeklibrary.domain.model.common;

import java.util.Objects;
import java.util.UUID;

public abstract class AggregateRoot {

  protected UUID id;

  public AggregateRoot(UUID id) {
    this.id = Objects.requireNonNullElse(id, UUID.randomUUID());
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AggregateRoot other = (AggregateRoot) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AggregateRoot [getId()=" + getId() + "]";
  }
}