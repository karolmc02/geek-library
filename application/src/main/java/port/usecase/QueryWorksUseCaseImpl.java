package port.usecase;

import java.util.List;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.WorkRepository;
import port.in.QueryWorksUseCase;
import port.in.model.QueryWorksCommand;

@Service
public class QueryWorksUseCaseImpl implements QueryWorksUseCase {

  private final WorkRepository workRepository;

  public QueryWorksUseCaseImpl(final WorkRepository workRepository) {
    this.workRepository = workRepository;
  }

  @Override
  public List<Work> execute(final QueryWorksCommand input) {
    return this.workRepository.query(input.title(), input.author());
  }

}
