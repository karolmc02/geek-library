package com.geekapps.geeklibrary.domain.work;

import java.util.UUID;

import com.geekapps.geeklibrary.domain.common.AggregateRoot;
import com.geekapps.geeklibrary.domain.common.Person;

public abstract class Work extends AggregateRoot {

  protected String title;

  protected String description;

  protected Person autor;

  protected Person illustrator;

  public Work(UUID id, String title, Person autor, Person illustrator, String description) {
    super(id);
    this.title = title;
    this.autor = autor;
    this.illustrator = illustrator;
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

  public Person getAutor() {
    return autor;
  }

  public void setAutor(Person autor) {
    this.autor = autor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((autor == null) ? 0 : autor.hashCode());
    result = prime * result + ((illustrator == null) ? 0 : illustrator.hashCode());
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
    if (autor == null) {
      if (other.autor != null)
        return false;
    } else if (!autor.equals(other.autor))
      return false;
    if (illustrator == null) {
      if (other.illustrator != null)
        return false;
    } else if (!illustrator.equals(other.illustrator))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Work [id=" + id + ", title=" + title + ", description=" + description + ", autor=" + autor
        + ", illustrator=" + illustrator + "]";
  }

}
