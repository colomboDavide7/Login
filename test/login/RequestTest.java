/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import login.users.PropertyException;
import login.users.User;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class RequestTest {
    
    @Test 
    public void shouldCreateUserWithBirthday(){
        System.out.println("* User property: shouldCreateUserWithBirthday()\n");
        String username = "valid";
        String pwd      = "Test_1";
        LocalDate birth = LocalDate.of(1987, 5, 23);        
        User u = new User(username, pwd, birth);
        
        assertTrue(u.isMyBirthDay(birth));
        assertFalse(u.isMyBirthDay(LocalDate.of(1997, 2, 25)));
    }
    
    @Test
    public void shouldAddBirthdayProperty(){
        System.out.println("* User property: shouldAddBirthdayProperty()\n");
        String username = "valid";
        String pwd      = "Test_1";
        
        try{
            User u = new User(username, pwd);
            LocalDate birth = LocalDate.of(1976, 5, 23);
            DateTimeFormatter formatter = u.getFormatter();
            String formattedBirth = birth.format(formatter);
            u.addProperty(User.PropertyName.BIRTH, formattedBirth);
            
            String myBirth = u.getProperty(User.PropertyName.BIRTH);
            String myCity  = u.getProperty(User.PropertyName.CITY);
        }catch(PropertyException ex){
            assertEquals(PropertyException.ErrorCode.NOT_FOUND, ex.getErrorCode());
        }
    }
    
}