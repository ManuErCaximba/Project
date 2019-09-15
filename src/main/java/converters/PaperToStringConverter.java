package converters;

import domain.Author;
import domain.Paper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PaperToStringConverter implements Converter<Paper, String> {

    @Override
    public String convert(final Paper a) {
        String result;

        if (a == null)
            result = null;
        else
            result = a.getTitle() + " (" + a.getId() + ")";

        return result;

    }
}
