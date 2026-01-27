package port.common;

public interface UseCase<Input, Output> {
  Output execute(final Input input);

}
