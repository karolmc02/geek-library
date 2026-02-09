package com.geekapps.geeklibrary.application.usecase;

import java.util.List;
import org.springframework.stereotype.Service;
import com.geekapps.geeklibrary.application.port.in.model.QueryWorksCommand;
import com.geekapps.geeklibrary.application.port.in.work.QueryWorksUseCase;
import com.geekapps.geeklibrary.domain.model.work.Work;
import com.geekapps.geeklibrary.domain.port.out.work.WorkRepository;

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
