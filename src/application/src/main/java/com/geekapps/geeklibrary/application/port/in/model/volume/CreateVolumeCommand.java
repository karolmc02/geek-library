package com.geekapps.geeklibrary.application.port.in.model.volume;

import java.time.LocalDate;
import java.util.UUID;
import com.geekapps.geeklibrary.application.port.in.model.to.MoneyTO;

public record CreateVolumeCommand(UUID workId, UUID editionId, String title, Integer number,
    MoneyTO price, LocalDate publicationDate, String isbn, Integer pages) {
}
