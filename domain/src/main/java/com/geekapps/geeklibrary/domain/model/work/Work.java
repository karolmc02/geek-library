package com.geekapps.geeklibrary.domain.model.work;

import java.util.UUID;

import com.geekapps.geeklibrary.domain.model.common.AggregateRoot;
import com.geekapps.geeklibrary.domain.model.common.Person;

public abstract class Work extends AggregateRoot {

  protected String title;

  protected String description;

  protected Person author;

  protected Person illustrator;

  public Work(UUID id, String title, String description, Person author, Person illustrator) {
    super(id);
    this.title = title;
    this.description = description;
    this.author = author;
    this.illustrator = illustrator;
  }

  public Work(String title, String description, Person author, Person illustrator) {
    this.title = title;
    this.author = author;
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

  public Person getAuthor() {
    return author;
  }

  public void setAuthor(Person autor) {
    this.author = autor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((author == null) ? 0 : author.hashCode());
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
    if (author == null) {
      if (other.author != null)
        return false;
    } else if (!author.equals(other.author))
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
    return "Work [id=" + id + ", title=" + title + ", description=" + description + ", author=" + author
        + ", illustrator=" + illustrator + "]";
  }

}
