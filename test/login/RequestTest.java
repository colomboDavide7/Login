/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.repository.AppRepository;
import login.tools.UserProperty;
import login.users.CustomerCreationException;
import login.users.IUser;
import login.users.User;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class RequestTest {
    
    private Application app;
    private AppManager manager;
    
    @Before
    public void setup(){
        this.manager = new AppManager();
        this.app = new Application(manager);
    }
    
    @Test 
    public void shouldCreateUserWithBirthday(){
        System.out.println("* User property: shouldCreateUserWithBirthday()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        
        LocalDate birth = LocalDate.of(1987, 5, 23);
        String formatter = birth.format(DateTimeFormatter.ISO_LOCAL_DATE);
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
            basicProperties.put(UserProperty.BIRTH, formatter);

        try {
            IUser customer = User.getBasicUser(basicProperties);
            assertTrue(customer.isMyBirthDay(birth));
            assertFalse(customer.isMyBirthDay(LocalDate.of(1997, 2, 25)));
        } catch (CustomerCreationException ex) {
            Logger.getLogger(RequestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @Test
    public void shouldAddBirthdayProperty() throws CustomerCreationException {
        System.out.println("* User property: shouldAddBirthdayProperty()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        IUser customer = User.getBasicUser(basicProperties);

        LocalDate birth = LocalDate.of(1976, 5, 23);
        DateTimeFormatter formatter = customer.getFormatter();
        String formattedBirth = birth.format(formatter);
        customer.addProperty(UserProperty.BIRTH, formattedBirth);

        String myBirth = customer.getProperty(UserProperty.BIRTH);
        String myCity  = customer.getProperty(UserProperty.CITY);

        assertFalse(customer.isEmptyProperty(UserProperty.BIRTH, myBirth));
        assertTrue(customer.isEmptyProperty(UserProperty.CITY, myCity));
    }
        
// ================================================================================
    // Controllers 
    
}
