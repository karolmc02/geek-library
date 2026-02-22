package com.geekapps.geeklibrary.application.port.in.model.to;

import java.util.UUID;

public record FormatTO(UUID id, String name, String description, Double widthCm, Double heightCm) {
}
