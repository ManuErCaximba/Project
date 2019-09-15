package converters;

import domain.Administrator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AdministratorToStringConverter implements Converter<Administrator, String> {

    @Override
    public String convert(final Administrator a) {
        String result;

        if (a == null)
            result = null;
        else
            result = a.getName() + " (" + a.getId() + ")";

        return result;

    }
}
