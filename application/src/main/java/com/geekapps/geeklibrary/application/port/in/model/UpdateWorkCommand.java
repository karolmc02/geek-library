package com.geekapps.geeklibrary.application.port.in.model;

import java.util.UUID;
import com.geekapps.geeklibrary.domain.model.work.WorkType;

public record UpdateWorkCommand(UUID id, WorkType type, String title, String description,
    PersonTO author, PersonTO illustrator) {
}
