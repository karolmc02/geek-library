package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "volumes")
public class VolumeEntity {

  @Id
  protected UUID id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "edition_id", nullable = false)
  protected EditionEntity edition;

  protected String title;

  protected Integer number;

  @Embedded
  protected MoneyEmbeddable price;

  protected LocalDate publicationDate;

  protected String isbn;

  protected Integer pages;

  public UUID getId() {
    return this.id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public EditionEntity getEdition() {
    return this.edition;
  }

  public void setEdition(final EditionEntity edition) {
    this.edition = edition;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(final Integer number) {
    this.number = number;
  }

  public MoneyEmbeddable getPrice() {
    return this.price;
  }

  public void setPrice(final MoneyEmbeddable price) {
    this.price = price;
  }

  public LocalDate getPublicationDate() {
    return this.publicationDate;
  }

  public void setPublicationDate(final LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public void setIsbn(final String isbn) {
    this.isbn = isbn;
  }

  public Integer getPages() {
    return this.pages;
  }

  public void setPages(final Integer pages) {
    this.pages = pages;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
    result = prime * result + ((this.number == null) ? 0 : this.number.hashCode());
    result = prime * result + ((this.isbn == null) ? 0 : this.isbn.hashCode());
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
    final VolumeEntity other = (VolumeEntity) obj;
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
    if (this.number == null) {
      if (other.number != null)
        return false;
    } else if (!this.number.equals(other.number))
      return false;
    if (this.isbn == null) {
      if (other.isbn != null)
        return false;
    } else if (!this.isbn.equals(other.isbn))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return String.format("VolumeEntity[id=%s, title=%s, number=%s, isbn=%s]", this.id, this.title,
        this.number, this.isbn);
  }

}
