package controllers.message;

import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.MessageService;
import services.TopicService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;

@Controller
@RequestMapping("message")
public class MessageController extends AbstractController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private TopicService topicService;

    @RequestMapping(value = "/administrator,author,reviewer,sponsor/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView result;
        Collection<Message> messages;
        String language = LocaleContextHolder.getLocale().getLanguage();
        try {
            Actor actor = this.actorService.getActorLogged();
            Assert.notNull(actor);

            messages = this.messageService.findAllByActor(actor.getId());
            Assert.notNull(messages);

            result = new ModelAndView("message/administrator,author,reviewer,sponsor/list");
            result.addObject("messages", messages);
            result.addObject("lang", language);
            result.addObject("requestURI", "message/administrator,author,reviewer,sponsor/list.do");
        } catch (Exception e){
            result = new ModelAndView("redirect:/");
        }
        return result;

    }

    @RequestMapping(value = "/administrator,author,reviewer,sponsor/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam int messageId){
        ModelAndView result;
        Message mesage;
        String language = LocaleContextHolder.getLocale().getLanguage();
        try {
            mesage = this.messageService.findOne(messageId);
            Assert.notNull(mesage);

            Actor actor = this.actorService.getActorLogged();
            Assert.isTrue(mesage.getSender().getId() == actor.getId() || mesage.getRecipient().getId() == actor.getId());
            result = new ModelAndView("message/administrator,author,reviewer,sponsor/show");
            result.addObject("mesage", mesage);
            result.addObject("lang", language);
        } catch (final Exception e) {
            result = new ModelAndView("redirect:/");
        }

        return result;
    }

    @RequestMapping(value = "/administrator,author,reviewer,sponsor/create", method = RequestMethod.GET)
    public ModelAndView create(){
        ModelAndView result;
        Collection<Actor> actors = this.actorService.findAll();
        Collection<Topic> topics = this.topicService.findAll();
        String language = LocaleContextHolder.getLocale().getLanguage();
        try{
            Assert.notNull(actors);
            Assert.notNull(topics);
            Message message = this.messageService.create();
            result = new ModelAndView("message/administrator,author,reviewer,sponsor/create");
            result.addObject("mesage", message);
            result.addObject("actors", actors);
            result.addObject("topics", topics);
            result.addObject("lang", language);
        } catch (Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/administrator,author,reviewer,sponsor/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@ModelAttribute("mesage") Message message, @RequestParam String recipient, BindingResult binding){
        ModelAndView result;
        Collection<Topic> topics = this.topicService.findAll();
        String language = LocaleContextHolder.getLocale().getLanguage();
        try{
            Assert.notNull(topics);
            String s = recipient.replace(",", "");
            Actor actor = this.actorService.findByUsername(s);
            message.setRecipient(actor);
            message.setSender(this.actorService.getActorLogged());
            message = this.messageService.reconstruct(message, binding);
            message = this.messageService.save(message);
            result = new ModelAndView("redirect:list.do");
        }catch (ValidationException e){
            result = this.createEditModelAndView(message);
            result.addObject("mesage", message);
            result.addObject("topics", topics);
            result.addObject("lang", language);
        } catch (Throwable oops){
            result = this.createEditModelAndView(message, "message.commit.error");
            result.addObject("topics", topics);
            result.addObject("lang", language);
        }
        return result;
    }

    @RequestMapping(value = "/administrator/broadcast", method = RequestMethod.GET)
    public ModelAndView broadcast() {
        ModelAndView result;
        Message mesage;
        Collection<Topic> topics = this.topicService.findAll();
        String language = LocaleContextHolder.getLocale().getLanguage();
        try {
            Assert.notNull(topics);
            mesage = this.messageService.create();

            result = new ModelAndView("message/administrator/broadcast");
            result.addObject("mesage", mesage);
            result.addObject("topics", topics);
            result.addObject("lang", language);
        } catch(Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    @RequestMapping(value = "/administrator/broadcastAuthors", method = RequestMethod.GET)
    public ModelAndView broadcastAuthors() {
        ModelAndView result;
        Message mesage;
        Collection<Topic> topics = this.topicService.findAll();
        String language = LocaleContextHolder.getLocale().getLanguage();
        try {
            Assert.notNull(topics);
            mesage = this.messageService.create();

            result = new ModelAndView("message/administrator/broadcastAuthors");
            result.addObject("mesage", mesage);
            result.addObject("topics", topics);
            result.addObject("lang", language);
        } catch(Throwable oops){
            result = new ModelAndView("redirect:/");
        }
        return result;
    }


    // Send Broadcast  -------------------------------------------------------------
    @RequestMapping(value = "/administrator/broadcast", method = RequestMethod.POST, params = "send")
    public ModelAndView sendBroadcast(@ModelAttribute("mesage") Message mesage, final BindingResult binding) {
        ModelAndView result;
        Collection<Topic> topics = this.topicService.findAll();
        String language = LocaleContextHolder.getLocale().getLanguage();
        try {
            Assert.notNull(topics);
            mesage = this.messageService.reconstruct(mesage, binding);
            this.messageService.broadcast(mesage);
            result = new ModelAndView("redirect:/message/administrator,author,reviewer,sponsor/list.do");
        } catch (final ValidationException e) {
            result = this.createBroadcastModelAndView(mesage, null);
            result.addObject("topics", topics);
            result.addObject("lang", language);
        } catch (final Throwable oops) {
            result = this.createBroadcastModelAndView(mesage, "message.commit.error");
            result.addObject("topics", topics);
            result.addObject("lang", language);
        }
        return result;
    }

    @RequestMapping(value = "/administrator/broadcastAuthors", method = RequestMethod.POST, params = "sendAuthors")
    public ModelAndView sendBroadcastAuthors(@ModelAttribute("mesage") Message mesage, final BindingResult binding) {
        ModelAndView result;
        Collection<Topic> topics = this.topicService.findAll();
        String language = LocaleContextHolder.getLocale().getLanguage();
        try {
            Assert.notNull(topics);
            mesage = this.messageService.reconstruct(mesage, binding);
            this.messageService.broadcastAuthors(mesage);
            result = new ModelAndView("redirect:/message/administrator,author,reviewer,sponsor/list.do");
        } catch (final ValidationException e) {
            result = this.createBroadcastModelAndView(mesage, null);
            result.addObject("topics", topics);
            result.addObject("lang", language);
        } catch (final Throwable oops) {
            result = this.createBroadcastModelAndView(mesage, "message.commit.error");
            result.addObject("topics", topics);
            result.addObject("lang", language);
        }
        return result;
    }

    @RequestMapping(value = "/administrator,author,reviewer,sponsor/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam int messageId) {
        ModelAndView result;
        try {
            Message message = this.messageService.findOne(messageId);
            Assert.notNull(message);
            this.messageService.delete(message);
            result = new ModelAndView("redirect:list.do");
        } catch (Throwable oops) {
            result = new ModelAndView("redirect:/");
        }
        return result;
    }

    private ModelAndView createEditModelAndView(final Message message) {
        ModelAndView result;
        result = this.createEditModelAndView(message, null);
        return result;
    }

    private ModelAndView createEditModelAndView(final Message message, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("message/administrator,author,reviewer,sponsor/create");
        result.addObject("mesage", message);
        result.addObject("message", messageCode);
        return result;
    }

    protected ModelAndView createBroadcastModelAndView(final Message mesage) {
        ModelAndView result;

        result = this.createBroadcastModelAndView(mesage, null);

        return result;
    }

    protected ModelAndView createBroadcastModelAndView(final Message mesage, final String message) {
        ModelAndView result;

        result = new ModelAndView("message/administrator/broadcast");
        result.addObject("mesage", mesage);
        result.addObject("message", message);

        return result;
    }
}
