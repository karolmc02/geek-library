package com.geekapps.geeklibrary.domain.model.edition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;
import com.geekapps.geeklibrary.domain.model.common.DomainEntity;
import com.geekapps.geeklibrary.domain.model.volume.Volume;


public class Edition extends DomainEntity {

  private String publisher;
  private Language language;
  private Country country;
  private Boolean isOriginal;
  private Format format;
  private ColorMode colorMode;
  private final List<Volume> volumes;

  public Edition() {
    this.volumes = new ArrayList<>();
  }

  @Default
  public Edition(final UUID id, final String publisher, final Language language,
      final Country country, final Boolean isOriginal, final Format format,
      final ColorMode colorMode) {
    super(id);
    this.publisher = publisher;
    this.language = language;
    this.country = country;
    this.isOriginal = isOriginal;
    this.format = format;
    this.colorMode = colorMode;
    this.volumes = new ArrayList<>();
  }

  public Edition(final String publisher, final Language language, final Country country,
      final Boolean isOriginal, final Format format, final ColorMode colorMode) {
    this.publisher = publisher;
    this.language = language;
    this.country = country;
    this.isOriginal = isOriginal;
    this.format = format;
    this.colorMode = colorMode;
    this.volumes = new ArrayList<>();
  }

  public String getPublisher() {
    return this.publisher;
  }

  public void setPublisher(final String publisher) {
    this.publisher = publisher;
  }

  public Language getLanguage() {
    return this.language;
  }

  public void setLanguage(final Language language) {
    this.language = language;
  }

  public Country getCountry() {
    return this.country;
  }

  public void setCountry(final Country country) {
    this.country = country;
  }

  public Boolean getIsOriginal() {
    return this.isOriginal;
  }

  public void setIsOriginal(final Boolean isOriginal) {
    this.isOriginal = isOriginal;
  }

  public Format getFormat() {
    return this.format;
  }

  public void setFormat(final Format format) {
    this.format = format;
  }

  public ColorMode getColorMode() {
    return this.colorMode;
  }

  public void setColorMode(final ColorMode colorMode) {
    this.colorMode = colorMode;
  }

  public List<Volume> getVolumes() {
    return List.copyOf(this.volumes);
  }

  public void addVolume(final Volume volume) {
    if (volume != null) {
      this.volumes.add(volume);
    }
  }

  public void removeVolume(final Volume volume) {
    if (volume != null) {
      this.volumes.remove(volume);
    }
  }

  @Override
  public boolean equals(final Object obj) {
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final Edition edition = (Edition) obj;
    return Objects.equals(this.publisher, edition.publisher)
        && Objects.equals(this.language, edition.language)
        && Objects.equals(this.country, edition.country)
        && Objects.equals(this.isOriginal, edition.isOriginal)
        && Objects.equals(this.format, edition.format) && this.colorMode == edition.colorMode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.publisher, this.language, this.country,
        this.isOriginal, this.format, this.colorMode);
  }

  @Override
  public String toString() {
    return String.format("Edition[publisher=%s, language=%s, country=%s, original=%s, format=%s]",
        this.publisher, this.language, this.country, this.isOriginal, this.format);
  }
}
