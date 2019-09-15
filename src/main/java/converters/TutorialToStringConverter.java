package converters;

import domain.Sponsor;
import domain.Tutorial;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import javax.transaction.Transactional;

@Component
@Transactional
public class TutorialToStringConverter implements Converter<Tutorial, String> {

    @Override
    public String convert(final Tutorial a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;

    }
}