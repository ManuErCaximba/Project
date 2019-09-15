package converters;

import domain.Conference;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import javax.transaction.Transactional;

@Component
@Transactional
public class ConferenceToStringConverter implements Converter<Conference, String> {

    @Override
    public String convert(final Conference a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;

    }
}
