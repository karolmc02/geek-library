package com.geekapps.geeklibrary.domain.port.out;

import com.geekapps.geeklibrary.domain.model.work.Work;

public interface WorkRepository {

  Work save(Work work);

}
