package port.usecase;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.DeleteWorkByIdUseCase;

@Service
public class DeleteWorkByIdUseCaseImpl implements DeleteWorkByIdUseCase {

  private final WorkRepository workRepository;

  public DeleteWorkByIdUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public Void execute(final UUID id) {
    this.workRepository.deleteById(id);
    return null;
  }

}
