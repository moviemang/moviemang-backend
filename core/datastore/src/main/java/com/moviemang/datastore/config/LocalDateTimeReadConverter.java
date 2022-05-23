package com.moviemang.datastore.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@ReadingConverter
public class LocalDateTimeReadConverter implements Converter<LocalDateTime, LocalDateTime> {

//    @Override
//    public ZonedDateTime convert(@Nullable Document document) {
//        if (document == null) return null;
//
//        Date dateTime = document.getDate(LocalDateTimeWriteConverter.DATE_TIME);
//        String zoneId = document.getString(LocalDateTimeWriteConverter.ZONE);
//        ZoneId zone = ZoneId.of(zoneId);
//
//        return ZonedDateTime.ofInstant(dateTime.toInstant(), zone);
//    }

    @Override
    public LocalDateTime convert(@Nullable LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.minusHours(9);
    }
}
