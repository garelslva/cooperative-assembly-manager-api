package br.com.assembly.converter;

import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.storage.postegres.entity.SessionEntity;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class Utils {

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static <T> Optional<Page<T>> response(Optional<Page<T>> pgObj, final String context, Logger log) {
        var message = MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(context);
        pgObj.map(pg -> {
                    if (pg == null || pg.isEmpty()){
                        log.info(message);
                        throw new NotFoundException(message);
                    }
                    return pg;
                })
                .orElseThrow(() -> new NotFoundException(message));
        return pgObj;
    }
}
