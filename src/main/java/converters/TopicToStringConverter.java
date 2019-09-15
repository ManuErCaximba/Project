package converters;

import domain.Topic;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import javax.transaction.Transactional;

@Component
@Transactional
public class TopicToStringConverter implements Converter<Topic, String> {

    @Override
    public String convert(final Topic a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;

    }
}