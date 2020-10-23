/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.tools.UserValidator;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class ApplicationTest {

// ================================================================================
// Username Tests
    @Test 
    public void shouldRefuseEmptyUsername(){
        System.out.println("* UserValidator: shouldRefuseEmptyUsername()\n");
        String empty = "";
        boolean login = UserValidator.validateUsername(empty);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefuseUsernameThatDoesntBeginWithALetter(){
        System.out.println("* UserValidator: shouldRefuseUsernameThatDoesntBeginWithALetter()\n");
        String invalidUsername = "_1test_";
        boolean login = UserValidator.validateUsername(invalidUsername);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefuseUsernameWithSpace(){
        System.out.println("* UserValidator: shouldRefuseUsernameWithSpace()\n");
        String invalidUsername = "test ";
        boolean login = UserValidator.validateUsername(invalidUsername);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shoulAcceptUsernameWithSymbolNumberUpperCase(){
        System.out.println("* UserValidator: shoulAcceptUsernameWithSymbolNumberUpperCase()\n");
        String validUsername = "tEst1_";
        boolean login = UserValidator.validateUsername(validUsername);
        Assert.assertEquals(true, login);
    }
    
// ================================================================================
    // Password Tests

    @Test
    public void shouldRefuseEmptyPassword(){
        System.out.println("* UserValidator: shouldRefuseEmptyPassword()\n");
        String invalidPwd = "";
        boolean login = UserValidator.validatePassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutSymbols(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutSymbols()\n");
        String invalidPwd = "test1";
        boolean login = UserValidator.validatePassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutNumbers(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutNumbers()\n");
        String invalidPwd = "test!_";
        boolean login = UserValidator.validatePassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutLetters(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutLetters()\n");
        String invalidPwd = "!123#_";
        boolean login = UserValidator.validatePassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutUpperCaseLetter(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutUpperCaseLetter()\n");
        String invalidPwd = "test!_2";
        boolean login = UserValidator.validatePassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
}
