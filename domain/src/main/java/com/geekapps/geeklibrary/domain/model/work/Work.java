package com.geekapps.geeklibrary.domain.model.work;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;
import com.geekapps.geeklibrary.domain.model.common.AggregateRoot;
import com.geekapps.geeklibrary.domain.model.common.Person;

public abstract class Work extends AggregateRoot {

  protected String title;

  protected String description;

  protected Person author;

  protected Person illustrator;

  @Default
  public Work(final UUID id, final String title, final String description, final Person author,
      final Person illustrator) {
    super(id);
    this.title = title;
    this.description = description;
    this.author = author;
    this.illustrator = illustrator;
  }

  public Work(final String title, final String description, final Person author,
      final Person illustrator) {
    this.title = title;
    this.description = description;
    this.author = author;
    this.illustrator = illustrator;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Person getAuthor() {
    return this.author;
  }

  public void setAuthor(final Person autor) {
    this.author = autor;
  }

  public Person getIllustrator() {
    return this.illustrator;
  }

  public void setIllustrator(final Person illustrator) {
    this.illustrator = illustrator;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
    result = prime * result + ((this.author == null) ? 0 : this.author.hashCode());
    result = prime * result + ((this.illustrator == null) ? 0 : this.illustrator.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final Work other = (Work) obj;
    if (this.title == null) {
      if (other.title != null)
        return false;
    } else if (!this.title.equals(other.title))
      return false;
    if (this.description == null) {
      if (other.description != null)
        return false;
    } else if (!this.description.equals(other.description))
      return false;
    if (this.author == null) {
      if (other.author != null)
        return false;
    } else if (!this.author.equals(other.author))
      return false;
    if (this.illustrator == null) {
      if (other.illustrator != null)
        return false;
    } else if (!this.illustrator.equals(other.illustrator))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Work [id=" + this.id + ", title=" + this.title + ", description=" + this.description
        + ", author=" + this.author + ", illustrator=" + this.illustrator + "]";
  }
}
