package com.geekapps.geeklibrary.domain.model.volume;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;
import com.geekapps.geeklibrary.domain.model.common.DomainEntity;
import com.geekapps.geeklibrary.domain.model.common.Money;

public class Volume extends DomainEntity {

  private String title;
  private Integer number;
  private Money price;
  private LocalDate publicationDate;
  private String isbn;
  private Integer pages;

  public Volume() {}

  @Default
  public Volume(final UUID id, final String title, final Integer number, final Money price,
      final LocalDate publicationDate, final String isbn, final Integer pages) {
    super(id);
    this.title = title;
    this.number = number;
    this.price = price;
    this.publicationDate = publicationDate;
    this.isbn = isbn;
    this.pages = pages;
  }

  public Volume(final String title, final Integer number, final Money price,
      final LocalDate publicationDate, final String isbn, final Integer pages) {
    this.title = title;
    this.number = number;
    this.price = price;
    this.publicationDate = publicationDate;
    this.isbn = isbn;
    this.pages = pages;
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

  public Money getPrice() {
    return this.price;
  }

  public void setPrice(final Money price) {
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
  public boolean equals(final Object obj) {
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final Volume volume = (Volume) obj;
    return Objects.equals(this.title, volume.title) && Objects.equals(this.number, volume.number)
        && Objects.equals(this.price, volume.price)
        && Objects.equals(this.publicationDate, volume.publicationDate)
        && Objects.equals(this.isbn, volume.isbn) && Objects.equals(this.pages, volume.pages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.title, this.number, this.price, this.publicationDate,
        this.isbn, this.pages);
  }

  @Override
  public String toString() {
    return "Volume [id=" + this.id + ", title=" + this.title + ", number=" + this.number
        + ", price=" + this.price + ", publicationDate=" + this.publicationDate + ", isbn="
        + this.isbn + ", pages=" + this.pages + "]";
  }
}
