package converters;

import domain.Paper;
import domain.Reviewer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.PaperRepository;
import repositories.ReviewerRepository;
import services.ReviewerService;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToReviewerConverter implements Converter<String, Reviewer> {

    @Autowired
    ReviewerRepository reviewerRepository;

    @Override
    public Reviewer convert(final String text) {
        Reviewer result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                String idText = text.substring(text.lastIndexOf('(') + 1, text.lastIndexOf(')'));
                id = Integer.valueOf(idText);
                result = this.reviewerRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
