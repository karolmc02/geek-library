package com.geekapps.geeklibrary.domain.model.common;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;

public class Person extends DomainEntity {

  private String firstName;
  private String lastName;

  public Person() {
  }

  @Default
  public Person(final UUID id, final String firstName, final String lastName) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Person(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
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
    int result = super.hashCode();
    result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
    result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final Person other = (Person) obj;
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
    return "Person [id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName
        + "]";
  }

}
