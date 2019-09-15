package converters;

import domain.Reviewer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ReviewerToStringConverter implements Converter<Reviewer, String> {

    @Override
    public String convert(final Reviewer a) {
        String result;

        if (a == null)
            result = null;
        else
            result = a.getName() + " (" + a.getId() + ")";

        return result;

    }
}
