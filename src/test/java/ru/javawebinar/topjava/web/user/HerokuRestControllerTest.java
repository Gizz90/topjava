package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.Profiles.HEROKU;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.util.exception.ModificationRestrictionException.EXCEPTION_MODIFICATION_RESTRICTION;

@ActiveProfiles({HEROKU})
class HerokuRestControllerTest extends AbstractControllerTest {

    public HerokuRestControllerTest() {
        super(AdminRestController.REST_URL);
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(USER_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_MODIFICATION_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        perform(doPut(USER_ID).jsonUserWithPassword(USER).basicAuth(ADMIN))
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_MODIFICATION_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }
}