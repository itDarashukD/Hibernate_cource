package org.example.converter;

import java.sql.Date;
import java.util.Optional;
import javax.persistence.AttributeConverter;
import org.example.entity.CustomBirthday;


/*convert values which going from app to DB and from DB to app*/
public class CustomBirthdayConverter implements  AttributeConverter<CustomBirthday, Date>{

    @Override
    public Date convertToDatabaseColumn(CustomBirthday customBirthday) {
        return Optional.ofNullable(customBirthday)
	       .map(CustomBirthday::birthdate)
	       .map(Date::valueOf)
	       .orElse(null);

    }

    @Override
    public CustomBirthday convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
	       .map(Date::toLocalDate)
	       .map(CustomBirthday::new)
	       .orElse(null);
    }


}
