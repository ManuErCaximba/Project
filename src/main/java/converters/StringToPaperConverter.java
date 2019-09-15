package converters;

import domain.Paper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.PaperRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToPaperConverter implements Converter<String, Paper> {

    @Autowired
    PaperRepository paperRepository;


    @Override
    public Paper convert(final String text) {
        Paper result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                String idText = text.substring(text.lastIndexOf('(') + 1, text.lastIndexOf(')'));
                id = Integer.valueOf(idText);
                result = this.paperRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}