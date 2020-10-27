/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import login.controllers.RequestController;
import login.repository.ApplicationRepository;
import login.users.UserRequest;
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
    private ApplicationManager manager;
    private ApplicationRepository repo;
    
    @Before
    public void setup(){
        this.manager = new ApplicationManager();
        this.repo    = new ApplicationRepository();
        this.app = new Application(manager, repo);
    }
    
    @Test 
    public void shouldCreateUserWithBirthday(){
        System.out.println("* User property: shouldCreateUserWithBirthday()\n");
        String username = "valid";
        String pwd      = "Test_1";
        LocalDate birth = LocalDate.of(1987, 5, 23);        
        IUser u = new User(username, pwd, birth);
        
        assertTrue(u.isMyBirthDay(birth));
        assertFalse(u.isMyBirthDay(LocalDate.of(1997, 2, 25)));
    }
    
    @Test
    public void shouldAddBirthdayProperty(){
        System.out.println("* User property: shouldAddBirthdayProperty()\n");
        String username = "valid";
        String pwd      = "Test_1";
        
            IUser u = new User(username, pwd);
            LocalDate birth = LocalDate.of(1976, 5, 23);
            DateTimeFormatter formatter = u.getFormatter();
            String formattedBirth = birth.format(formatter);
            u.addProperty(User.PropertyName.BIRTH, formattedBirth);
            
            String myBirth = u.getProperty(User.PropertyName.BIRTH);
            String myCity  = u.getProperty(User.PropertyName.CITY);
            
            assertFalse(u.isEmptyProperty(User.PropertyName.BIRTH, myBirth));
            assertTrue(u.isEmptyProperty(User.PropertyName.CITY, myCity));
    }
    
    @Test
    public void shouldBeSignUpRequest(){
        System.out.println("* User property: shouldBeSignUpRequest()\n");
        String username = "valid";
        String pwd      = "Test_1";
        
        IUser u       = new User(username, pwd);
        UserRequest r = UserRequest.createRequestByType(u, UserRequest.RequestType.SIGN_UP);
        assertTrue(r.matchType(UserRequest.RequestType.SIGN_UP));
    }
    
// ================================================================================
    // Controllers 
    
}
