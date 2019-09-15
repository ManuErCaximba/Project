package converters;

import domain.Sponsor;
import domain.Submission;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.SponsorRepository;
import services.SubmissionService;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToSubmissionConverter implements Converter<String, Submission> {

    @Autowired
    SubmissionService submissionService;


    @Override
    public Submission convert(final String text) {
        Submission result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                String idText = text.substring(text.lastIndexOf('(') + 1, text.lastIndexOf(')'));
                id = Integer.valueOf(idText);
                result = this.submissionService.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}