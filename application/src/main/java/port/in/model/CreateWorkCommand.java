package port.in.model;

import com.geekapps.geeklibrary.domain.model.common.Person;
import com.geekapps.geeklibrary.domain.model.work.WorkType;

public record CreateWorkCommand(WorkType type, String title, String description, Person author,
                Person illustrator) {
}
