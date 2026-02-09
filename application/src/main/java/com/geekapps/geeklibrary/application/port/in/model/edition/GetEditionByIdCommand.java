package com.geekapps.geeklibrary.application.port.in.model.edition;

import java.util.UUID;

public record GetEditionByIdCommand(UUID workId, UUID editionId) {
}
