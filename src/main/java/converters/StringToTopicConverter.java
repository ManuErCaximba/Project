package converters;

import domain.Sponsor;
import domain.Topic;
import domain.Tutorial;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.SponsorRepository;
import repositories.TopicRepository;
import repositories.TutorialRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToTopicConverter implements Converter<String, Topic> {

    @Autowired
    TopicRepository topicRepository;


    @Override
    public Topic convert(final String text) {
        Topic result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.topicRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}