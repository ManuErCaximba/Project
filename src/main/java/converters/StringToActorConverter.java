package converters;

import domain.Actor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import services.ActorService;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

    @Autowired
    ActorService actorService;


    @Override
    public Actor convert(final String text) {
        Actor result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                String idText = text.substring(text.lastIndexOf('(') + 1, text.lastIndexOf(')'));
                id = Integer.valueOf(idText);
                result = this.actorService.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}