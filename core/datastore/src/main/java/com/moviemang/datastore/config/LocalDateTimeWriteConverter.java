package com.moviemang.datastore.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@WritingConverter
public class LocalDateTimeWriteConverter implements Converter<LocalDateTime, LocalDateTime> {
    static final String DATE_TIME = "dateTime";
    static final String ZONE = "zone";

//    @Override
//    public Document convert(@Nullable ZonedDateTime zonedDateTime) {
//        if (zonedDateTime == null) return null;
//
//        Document document = new Document();
//        document.put(DATE_TIME, Date.from(zonedDateTime.plus(9, ChronoUnit.HOURS).toInstant()));
//        document.put(ZONE, zonedDateTime.getZone().getId());
//        document.put("offset", zonedDateTime.getOffset().toString());
//        return document;
//    }

    @Override
    public LocalDateTime convert(@Nullable LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.plus(9, ChronoUnit.HOURS);
    }
}