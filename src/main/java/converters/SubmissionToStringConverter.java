package converters;

import domain.Author;
import domain.Submission;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class SubmissionToStringConverter implements Converter<Submission, String> {

    @Override
    public String convert(final Submission a) {
        String result;

        if (a == null)
            result = null;
        else
            result = a.getTicker() + " (" + a.getId() + ")";

        return result;

    }
}
