package com.geekapps.geeklibrary.domain.model.common;

import java.util.Objects;
import java.util.UUID;

public class DomainEntity {

  protected UUID id;

  public DomainEntity() {
    this.id = UUID.randomUUID();
  }

  public DomainEntity(final UUID id) {
    this.id = Objects.requireNonNullElse(id, UUID.randomUUID());
  }

  public UUID getId() {
    return id;
  }

  public void setId(final UUID id) {
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
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final DomainEntity other = (DomainEntity) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "DomainEntity [id=" + id + "]";
  }

}
