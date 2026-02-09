package com.geekapps.geeklibrary.application.port.in.model.to;

import java.util.UUID;

public record PersonTO(UUID id, String firstName, String lastName) {
}
