package com.geekapps.geeklibrary.domain.port.out;

import java.util.List;
import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.work.Work;

public interface WorkRepository {

  Work save(Work work);

  List<Work> query(String title, String author);

  Work findById(UUID id);

  void deleteById(UUID id);

}
