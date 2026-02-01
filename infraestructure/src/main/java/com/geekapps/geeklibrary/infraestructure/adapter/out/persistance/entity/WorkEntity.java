package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.work.WorkType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "works")
public class WorkEntity {

  @Id
  protected UUID id;

  @Enumerated(EnumType.STRING)
  protected WorkType type;

  protected String title;
  protected String description;

  @ManyToOne
  @JoinColumn(name = "author_id")
  protected PersonEntity author;

  @ManyToOne
  @JoinColumn(name = "illustrator_id")
  protected PersonEntity illustrator;

  public UUID getId() {
    return this.id;
  }

  public void setId(final UUID id) {
    this.id = id;
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

  public PersonEntity getAuthor() {
    return this.author;
  }

  public void setAuthor(final PersonEntity author) {
    this.author = author;
  }

  public PersonEntity getIllustrator() {
    return this.illustrator;
  }

  public void setIllustrator(final PersonEntity illustrator) {
    this.illustrator = illustrator;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
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
    if (this.getClass() != obj.getClass())
      return false;
    final WorkEntity other = (WorkEntity) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
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
    return true;
  }

  @Override
  public String toString() {
    return "WorkEntity [id=" + this.id + ", title=" + this.title + ", description="
        + this.description + ", author=" + this.author + ", illustrator=" + this.illustrator + "]";
  }
}
