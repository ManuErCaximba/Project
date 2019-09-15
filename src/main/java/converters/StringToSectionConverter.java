package converters;

import domain.Section;
import domain.Sponsor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.SectionRepository;
import repositories.SponsorRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToSectionConverter implements Converter<String, Section> {

    @Autowired
    SectionRepository sectionRepository;


    @Override
    public Section convert(final String text) {
        Section result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.sectionRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}