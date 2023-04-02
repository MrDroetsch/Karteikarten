package unittests;

import me.bunnykick.db.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {

    @Test
    void testCheckPW() {
        String firstName = "Cedric";
        String lastName = "Riechers";
        String userName = "admin";
        String password = "admin";
        User user = new User(firstName, lastName, userName, password);

        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getUserName(), userName);
        assertEquals(user.getPassword(), password);

        assertTrue(user.isCorrectPW(password));
        assertFalse(user.isCorrectPW("WrongPW"));
    }

}
