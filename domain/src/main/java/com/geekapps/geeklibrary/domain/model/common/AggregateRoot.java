package com.geekapps.geeklibrary.domain.model.common;

import java.util.UUID;

public abstract class AggregateRoot extends DomainEntity {

  public AggregateRoot() {
    super();
  }

  public AggregateRoot(UUID id) {
    super(id);
  }

}