package kerberos.spring.management.helper;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter extends BidirectionalConverter<LocalDateTime, String> {
    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss";
    @Override
    public String convertTo(LocalDateTime date, Type<String> destinationType, MappingContext mappingContext) {
        if (date == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        return formatter.format(date);
    }

    @Override
    public LocalDateTime convertFrom(String dateString, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        if (dateString == null || "".equals(dateString)) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        LocalDateTime date = LocalDateTime.parse(dateString, formatter);

        return date;
    }
}
