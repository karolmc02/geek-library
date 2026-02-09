package com.geekapps.geeklibrary.application.port.in.model;

import java.util.UUID;

public record PersonTO(UUID id, String firstName, String lastName) {
}
