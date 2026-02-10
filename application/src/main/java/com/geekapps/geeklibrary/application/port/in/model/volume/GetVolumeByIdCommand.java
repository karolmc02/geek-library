package com.geekapps.geeklibrary.application.port.in.model.volume;

import java.util.UUID;

public record GetVolumeByIdCommand(UUID workId, UUID editionId, UUID volumeId) {
}
