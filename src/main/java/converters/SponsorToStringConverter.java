package converters;

import domain.Sponsor;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import javax.transaction.Transactional;

@Component
@Transactional
public class SponsorToStringConverter implements Converter<Sponsor, String> {

    @Override
    public String convert(final Sponsor a) {
        String result;

        if (a == null)
            result = null;
        else
            result = a.getName() + " (" + a.getId() + ")";

        return result;

    }
}