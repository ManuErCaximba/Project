package converters;

import domain.Section;
import domain.Sponsor;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import javax.transaction.Transactional;

@Component
@Transactional
public class SectionToStringConverter implements Converter<Section, String> {

    @Override
    public String convert(final Section a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;

    }
}