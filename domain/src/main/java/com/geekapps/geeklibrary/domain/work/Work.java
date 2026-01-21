package com.geekapps.geeklibrary.domain.work;

import java.util.UUID;

import com.geekapps.geeklibrary.domain.common.AggregateRoot;

public abstract class Work extends AggregateRoot {

  protected String title;

  protected String description;

  public Work(UUID id, String title, String description) {
    super(id);
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Work other = (Work) obj;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Work [id=" + id + ", title=" + title + ", description=" + description + "]";
  }

}
