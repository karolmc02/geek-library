package port.in.model;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.common.Person;

public record UpdateWorkCommand(UUID id, WorkType type, String title, String description,
    Person author, Person illustrator) {
}
