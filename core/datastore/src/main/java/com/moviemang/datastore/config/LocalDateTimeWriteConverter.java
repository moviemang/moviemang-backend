package com.moviemang.datastore.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@WritingConverter
public class LocalDateTimeWriteConverter implements Converter<LocalDateTime, Date> {

    @Override
    public Date convert(@Nullable LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime
                .plus(9, ChronoUnit.HOURS)
                .toInstant(ZoneOffset.of("+09:00")));
    }
}