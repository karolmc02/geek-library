package com.geekapps.geeklibrary.domain.model.work;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;
import com.geekapps.geeklibrary.domain.model.common.AggregateRoot;
import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.edition.Edition;

public class Work extends AggregateRoot {

  protected WorkType type;

  protected String title;

  protected String description;

  protected Person author;

  protected Person illustrator;

  protected List<Edition> editions;

  public Work() {
    this.editions = new ArrayList<>();
  }

  @Default
  public Work(final UUID id, final WorkType type, final String title, final String description,
      final Person author, final Person illustrator) {
    super(id);
    this.type = type;
    this.title = title;
    this.description = description;
    this.author = author;
    this.illustrator = illustrator;
    this.editions = new ArrayList<>();
  }

  public Work(final WorkType type, final String title, final String description,
      final Person author, final Person illustrator) {
    this.type = type;
    this.title = title;
    this.description = description;
    this.author = author;
    this.illustrator = illustrator;
    this.editions = new ArrayList<>();
  }

  public WorkType getType() {
    return this.type;
  }

  public void setType(final WorkType type) {
    this.type = type;
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

  public List<Edition> getEditions() {
    return List.copyOf(this.editions);
  }

  public void addEdition(final Edition edition) {
    if (edition != null) {
      this.editions.add(edition);
    }
  }

  public void removeEdition(final Edition edition) {
    if (edition != null) {
      this.editions.remove(edition);
    }
  }

  public void setEditions(final List<Edition> editions) {
    this.editions = editions != null ? List.copyOf(editions) : List.of();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
    result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
    result = prime * result + ((this.author == null) ? 0 : this.author.hashCode());
    result = prime * result + ((this.illustrator == null) ? 0 : this.illustrator.hashCode());
    result = prime * result + ((this.editions == null) ? 0 : this.editions.hashCode());
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
    if (this.type != other.type)
      return false;
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
    if (this.editions == null) {
      if (other.editions != null)
        return false;
    } else if (!this.editions.equals(other.editions))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Work [id=" + this.id + ", type=" + this.type + ", title=" + this.title
        + ", description=" + this.description + ", author=" + this.author + ", illustrator="
        + this.illustrator + ", editions=" + this.editions + "]";
  }
}
