package com.geekapps.geeklibrary.domain.model.edition;

import java.util.Objects;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.annotation.Default;
import com.geekapps.geeklibrary.domain.model.common.DomainEntity;
import com.geekapps.geeklibrary.domain.validation.DomainValidator;

public class Format extends DomainEntity {

  private String name;
  private String description;
  private Double widthCm;
  private Double heightCm;

  @Default
  public Format(final UUID id, final String name, final String description, final Double widthCm,
      final Double heightCm) {
    super(id);
    this.validateDimensions(widthCm, heightCm);
    this.name = name;
    this.description = description;
    this.widthCm = widthCm;
    this.heightCm = heightCm;
  }

  public Format(final String name, final String description, final Double widthCm,
      final Double heightCm) {
    this.validateDimensions(widthCm, heightCm);
    this.name = name;
    this.description = description;
    this.widthCm = widthCm;
    this.heightCm = heightCm;
  }

  private void validateDimensions(final Double width, final Double height) {
    DomainValidator.validatePositiveDimension(width, "Width");
    DomainValidator.validatePositiveDimension(height, "Height");
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Double getWidthCm() {
    return this.widthCm;
  }

  public void setWidthCm(final Double widthCm) {
    DomainValidator.validatePositiveDimension(widthCm, "Width");
    this.widthCm = widthCm;
  }

  public Double getHeightCm() {
    return this.heightCm;
  }

  public void setHeightCm(final Double heightCm) {
    DomainValidator.validatePositiveDimension(heightCm, "Height");
    this.heightCm = heightCm;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!super.equals(obj))
      return false;
    if (this.getClass() != obj.getClass())
      return false;
    final Format format = (Format) obj;
    return Objects.equals(this.name, format.name) && Objects.equals(this.widthCm, format.widthCm)
        && Objects.equals(this.heightCm, format.heightCm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.name, this.widthCm, this.heightCm);
  }

  @Override
  public String toString() {
    return this.name + " (" + this.widthCm + "x" + this.heightCm + " cm)";
  }
}
