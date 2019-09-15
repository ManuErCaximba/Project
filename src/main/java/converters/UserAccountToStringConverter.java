package converters;

import domain.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import security.UserAccount;

import javax.transaction.Transactional;

@Component
@Transactional
public class UserAccountToStringConverter implements Converter<UserAccount, String> {

    @Override
    public String convert(final UserAccount a) {
        String result;

        if (a == null)
            result = null;
        else
            result = String.valueOf(a.getId());

        return result;

    }
}
