package kerberos.spring.management.helper;

import kerberos.spring.management.dto.UserDto;
import kerberos.spring.management.entity.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserCustomMapper extends CustomMapper<User, UserDto> {

    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void mapAtoB(User user, UserDto userDto, MappingContext context) {
        super.mapAtoB(user, userDto, context);

        LocalDateTime date = user.getBirthDate();
        if (date == null) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        String isoDate = formatter.format(date);
        userDto.setBirthDate(isoDate);
    }


    @Override
    public void mapBtoA(UserDto userDto, User user, MappingContext context) {
        super.mapBtoA(userDto, user, context);

        if (userDto.getBirthDate() == null) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM_SS_Z);
        LocalDateTime date = LocalDateTime.parse(userDto.getBirthDate(), formatter);

        user.setBirthDate(date);
    }
}
