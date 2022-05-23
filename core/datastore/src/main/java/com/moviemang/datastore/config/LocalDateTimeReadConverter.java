package com.moviemang.datastore.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ReadingConverter
public class LocalDateTimeReadConverter implements Converter<Date, LocalDateTime> {

    @Override
    public LocalDateTime convert(@Nullable Date date) {
        if (date == null) return null;
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().minusHours(9);

    }
}
