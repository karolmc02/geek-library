package com.geekapps.geeklibrary.application.port.in.model;

import com.geekapps.geeklibrary.domain.model.work.WorkType;

public record CreateWorkCommand(WorkType type, String title, String description, PersonTO author,
        PersonTO illustrator) {
}
