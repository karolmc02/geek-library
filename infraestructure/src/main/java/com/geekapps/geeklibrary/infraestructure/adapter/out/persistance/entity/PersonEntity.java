package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class PersonEntity {

  @Id
  protected UUID id;

  protected String firstName;
  protected String lastName;

  public UUID getId() {
    return this.id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
    result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
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
    final PersonEntity other = (PersonEntity) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!this.firstName.equals(other.firstName))
      return false;
    if (this.lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!this.lastName.equals(other.lastName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PersonEntity [id=" + this.id + ", firstName=" + this.firstName + ", lastName="
        + this.lastName + "]";
  }

}
