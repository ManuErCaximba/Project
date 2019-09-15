package converters;

import domain.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AuthorToStringConverter implements Converter<Author, String> {

    @Override
    public String convert(final Author a) {
        String result;

        if (a == null)
            result = null;
        else
            result = a.getName() + " (" + a.getId() + ")";

        return result;

    }
}
