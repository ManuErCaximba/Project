package converters;

import domain.Author;
import domain.Paper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.AuthorRepository;
import repositories.PaperRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToAuthorConverter implements Converter<String, Author> {

    @Autowired
    AuthorRepository authorRepository;


    @Override
    public Author convert(final String text) {
        Author result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                String idText = text.substring(text.lastIndexOf('(') + 1, text.lastIndexOf(')'));
                id = Integer.valueOf(idText);
                result = this.authorRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}