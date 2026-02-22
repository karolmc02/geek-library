package com.geekapps.geeklibrary.application.port.in.model.edition;

import java.util.UUID;

public record DeleteEditionCommand(UUID workId, UUID editionId) {
}
