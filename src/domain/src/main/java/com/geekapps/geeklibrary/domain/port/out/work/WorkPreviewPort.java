package com.geekapps.geeklibrary.domain.port.out.work;

import java.net.URI;
import com.geekapps.geeklibrary.domain.model.work.Work;

public interface WorkPreviewPort {

  Work previewWork(URI url);

}
