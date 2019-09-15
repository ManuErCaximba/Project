package converters;

import domain.Actor;
import domain.Administrator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import services.ActorService;
import services.AdministratorService;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToAdministratorConverter implements Converter<String, Administrator> {

    @Autowired
    AdministratorService administratorService;


    @Override
    public Administrator convert(final String text) {
        Administrator result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                String idText = text.substring(text.lastIndexOf('(') + 1, text.lastIndexOf(')'));
                id = Integer.valueOf(idText);
                result = this.administratorService.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
