package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messageSource;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(UserTo userTo) {
        log.info("create from to {}", userTo);
        return create(UserUtil.createNewFromTo(userTo));
    }

    public User create(User user) {
        try {
            log.info("create {}", user);
            checkNew(user);
            return service.create(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(messageSource.getMessage("user.duplicateEmail", null, LocaleContextHolder.getLocale()));
        }
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        try {
            log.info("update {} with id={}", user, id);
            assureIdConsistent(user, id);
            service.update(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(messageSource.getMessage("user.duplicateEmail", null, LocaleContextHolder.getLocale()));
        }
    }

    public void update(UserTo userTo, int id) {
        try {
            log.info("update {} with id={}", userTo, id);
            assureIdConsistent(userTo, id);
            service.update(userTo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(messageSource.getMessage("user.duplicateEmail", null, LocaleContextHolder.getLocale()));
        }
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }
}