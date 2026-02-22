package com.geekapps.geeklibrary.application.common;

public interface UseCase<Input, Output> {
  Output execute(final Input input);

}
