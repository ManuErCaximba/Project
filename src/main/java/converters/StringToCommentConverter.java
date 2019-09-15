package converters;

import domain.Comment;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.CommentRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToCommentConverter implements Converter<String, Comment> {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment convert(String text){
        Comment result;
        int id;

        try{
            if(StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = this.commentRepository.findOne(id);
            }
        } catch (Throwable oops){
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}