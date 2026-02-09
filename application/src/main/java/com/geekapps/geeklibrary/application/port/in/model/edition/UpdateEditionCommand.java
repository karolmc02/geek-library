package com.geekapps.geeklibrary.application.port.in.model.edition;

import java.util.UUID;
import com.geekapps.geeklibrary.application.port.in.model.to.CountryTO;
import com.geekapps.geeklibrary.application.port.in.model.to.FormatTO;
import com.geekapps.geeklibrary.application.port.in.model.to.LanguageTO;
import com.geekapps.geeklibrary.domain.model.edition.ColorMode;

public record UpdateEditionCommand(UUID workId, UUID editionId, String publisher,
        LanguageTO language, CountryTO country, Boolean isOriginal, FormatTO format,
        ColorMode colorMode) {
}
