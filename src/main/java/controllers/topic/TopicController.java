package controllers.topic;


import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.TopicService;

import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("topic")
public class TopicController extends AbstractController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private ActorService actorService;

    @RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView result;
        Actor actor = this.actorService.getActorLogged();
        try{
            Assert.isTrue(actor instanceof Administrator);
            Collection<Topic> topics = this.topicService.findAll();
            final String language = LocaleContextHolder.getLocale().getLanguage();
            result = new ModelAndView("topic/administrator/list");
            result.addObject("topics", topics);
            result.addObject("requestURI", "topic/administrator/list.do");
            result.addObject("lang", language);
        }catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int topicId){
        ModelAndView result;
        try{
            Topic topic = this.topicService.findOne(topicId);
            final String language = LocaleContextHolder.getLocale().getLanguage();
            Assert.notNull(topic);
            result = new ModelAndView("topic/show");
            result.addObject("topic", topic);
            result.addObject("requestURI", "topic/show.do");
            result.addObject("lang", language);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
    public ModelAndView create(){
        ModelAndView result;
        Actor actor = this.actorService.getActorLogged();
        try{
            Assert.isTrue(actor instanceof Administrator);
            Topic topic = this.topicService.create();
            result = new ModelAndView("topic/administrator/create");
            result.addObject("topic", topic);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int topicId){
        ModelAndView result;
        Actor actor = this.actorService.getActorLogged();
        try{
            Assert.isTrue(actor instanceof Administrator);
            Topic topic;
            topic = this.topicService.findOne(topicId);
            Assert.notNull(topic);
            Assert.isTrue(!topic.getNameEs().equals("OTRO") && !topic.getNameEn().equals("OTHER"));
            result = new ModelAndView("topic/administrator/edit");
            result.addObject("topic", topic);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(Topic topic, BindingResult binding){
        ModelAndView result;
        Actor actor = this.actorService.getActorLogged();
        try{
            Assert.isTrue(actor instanceof Administrator);
            Assert.notNull(topic);
            topic = this.topicService.reconstruct(topic, binding);
            topic = this.topicService.save(topic);
            result = new ModelAndView("redirect:list.do");
        }catch (ValidationException e){
            result = this.createEditModelAndView(topic);
            result.addObject("topic", topic);
        } catch (Throwable oops){
            result = this.createEditModelAndView(topic, "topic.commit.error");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int topicId){
        ModelAndView result;
        try{
            Topic topic = this.topicService.findOne(topicId);
            Assert.notNull(topic);
            Assert.isTrue(!topic.getNameEs().equals("OTRO") && !topic.getNameEn().equals("OTHER"));
            this.topicService.delete(topic);
            result = new ModelAndView("redirect:list.do");
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    private ModelAndView createEditModelAndView(final Topic topic) {
        ModelAndView result;
        result = this.createEditModelAndView(topic, null);
        return result;
    }

    private ModelAndView createEditModelAndView(final Topic topic, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("topic/administrator/edit");
        result.addObject("topic", topic);
        result.addObject("message", messageCode);
        return result;
    }

}
