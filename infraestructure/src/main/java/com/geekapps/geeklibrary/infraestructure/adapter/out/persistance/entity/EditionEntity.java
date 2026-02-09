package com.geekapps.geeklibrary.infraestructure.adapter.out.persistance.entity;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.edition.ColorMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "editions")
public class EditionEntity {

  @Id
  protected UUID id;

  @ManyToOne
  @JoinColumn(name = "work_id", nullable = false)
  protected WorkEntity work;

  protected String publisher;

  protected String languageIsoCode;

  protected String countryName;
  protected String countryIsoCode;

  protected Boolean isOriginal;

  @ManyToOne
  @JoinColumn(name = "format_id")
  protected FormatEntity format;

  @Enumerated(EnumType.STRING)
  protected ColorMode colorMode;

  public UUID getId() {
    return this.id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public WorkEntity getWork() {
    return this.work;
  }

  public void setWork(final WorkEntity work) {
    this.work = work;
  }

  public String getPublisher() {
    return this.publisher;
  }

  public void setPublisher(final String publisher) {
    this.publisher = publisher;
  }

  public String getLanguageIsoCode() {
    return this.languageIsoCode;
  }

  public void setLanguageIsoCode(final String languageIsoCode) {
    this.languageIsoCode = languageIsoCode;
  }

  public String getCountryName() {
    return this.countryName;
  }

  public void setCountryName(final String countryName) {
    this.countryName = countryName;
  }

  public String getCountryIsoCode() {
    return this.countryIsoCode;
  }

  public void setCountryIsoCode(final String countryIsoCode) {
    this.countryIsoCode = countryIsoCode;
  }

  public Boolean getIsOriginal() {
    return this.isOriginal;
  }

  public void setIsOriginal(final Boolean isOriginal) {
    this.isOriginal = isOriginal;
  }

  public FormatEntity getFormat() {
    return this.format;
  }

  public void setFormat(final FormatEntity format) {
    this.format = format;
  }

  public ColorMode getColorMode() {
    return this.colorMode;
  }

  public void setColorMode(final ColorMode colorMode) {
    this.colorMode = colorMode;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.publisher == null) ? 0 : this.publisher.hashCode());
    result =
        prime * result + ((this.languageIsoCode == null) ? 0 : this.languageIsoCode.hashCode());
    result = prime * result + ((this.countryName == null) ? 0 : this.countryName.hashCode());
    result = prime * result + ((this.isOriginal == null) ? 0 : this.isOriginal.hashCode());
    result = prime * result + ((this.colorMode == null) ? 0 : this.colorMode.hashCode());
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
    final EditionEntity other = (EditionEntity) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.publisher == null) {
      if (other.publisher != null)
        return false;
    } else if (!this.publisher.equals(other.publisher))
      return false;
    if (this.languageIsoCode == null) {
      if (other.languageIsoCode != null)
        return false;
    } else if (!this.languageIsoCode.equals(other.languageIsoCode))
      return false;
    if (this.countryName == null) {
      if (other.countryName != null)
        return false;
    } else if (!this.countryName.equals(other.countryName))
      return false;
    if (this.isOriginal == null) {
      if (other.isOriginal != null)
        return false;
    } else if (!this.isOriginal.equals(other.isOriginal))
      return false;
    if (this.colorMode != other.colorMode)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return String.format("EditionEntity[id=%s, publisher=%s, language=%s, country=%s]", this.id,
        this.publisher, this.languageIsoCode, this.countryName);
  }

}
