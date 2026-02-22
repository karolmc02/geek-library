package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "formats")
public class FormatEntity {

  @Id
  protected UUID id;

  protected String name;
  protected String description;
  protected Double widthCm;
  protected Double heightCm;

  public UUID getId() {
    return this.id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Double getWidthCm() {
    return this.widthCm;
  }

  public void setWidthCm(final Double widthCm) {
    this.widthCm = widthCm;
  }

  public Double getHeightCm() {
    return this.heightCm;
  }

  public void setHeightCm(final Double heightCm) {
    this.heightCm = heightCm;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
    result = prime * result + ((this.widthCm == null) ? 0 : this.widthCm.hashCode());
    result = prime * result + ((this.heightCm == null) ? 0 : this.heightCm.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final FormatEntity other = (FormatEntity) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.description == null) {
      if (other.description != null)
        return false;
    } else if (!this.description.equals(other.description))
      return false;
    if (this.widthCm == null) {
      if (other.widthCm != null)
        return false;
    } else if (!this.widthCm.equals(other.widthCm))
      return false;
    if (this.heightCm == null) {
      if (other.heightCm != null)
        return false;
    } else if (!this.heightCm.equals(other.heightCm))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return String.format("FormatEntity[id=%s, name=%s, width=%s, height=%s]", this.id, this.name,
        this.widthCm, this.heightCm);
  }

}
