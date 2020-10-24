/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.tools.LoginException;
import login.tools.UserValidator;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class ApplicationTest {

// ================================================================================
// Username Tests
    @Test (expected = LoginException.class)
    public void shouldRefuseEmptyUsername() throws LoginException{
        System.out.println("* UserValidator: shouldRefuseEmptyUsername()\n");
        String empty = "";
        UserValidator.isValidUsername(empty);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRefuseUsernameThatDoesntBeginWithALetter() throws LoginException {
        System.out.println("* UserValidator: shouldRefuseUsernameThatDoesntBeginWithALetter()\n");
        String invalidUsername = "_1test_";
        UserValidator.isValidUsername(invalidUsername);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRefuseUsernameWithSpace() throws LoginException {
        System.out.println("* UserValidator: shouldRefuseUsernameWithSpace()\n");
        String invalidUsername = "test ";
        UserValidator.isValidUsername(invalidUsername);
    }
    
    @Test
    public void shoulAcceptUsernameWithSymbolNumberUpperCase() throws LoginException {
        System.out.println("* UserValidator: shoulAcceptUsernameWithSymbolNumberUpperCase()\n");
        String validUsername = "tEst1_";
        UserValidator.isValidUsername(validUsername);
        Assert.assertTrue(true);
    }
    
// ================================================================================
    // Password Tests

    @Test (expected = LoginException.class)
    public void shouldRefuseEmptyPassword() throws LoginException{
        System.out.println("* UserValidator: shouldRefuseEmptyPassword()\n");
        String invalidPwd = "";
        UserValidator.isValidPassword(invalidPwd);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRefusePasswordWithoutSymbols() throws LoginException{
        System.out.println("* UserValidator: shouldRefusePasswordWithoutSymbols()\n");
        String invalidPwd = "test1";
        UserValidator.isValidPassword(invalidPwd);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRefusePasswordWithoutNumbers() throws LoginException{
        System.out.println("* UserValidator: shouldRefusePasswordWithoutNumbers()\n");
        String invalidPwd = "test!_";
        UserValidator.isValidPassword(invalidPwd);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRefusePasswordWithoutLetters() throws LoginException{
        System.out.println("* UserValidator: shouldRefusePasswordWithoutLetters()\n");
        String invalidPwd = "!123#_";
        UserValidator.isValidPassword(invalidPwd);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRefusePasswordWithoutUpperCaseLetter() throws LoginException{
        System.out.println("* UserValidator: shouldRefusePasswordWithoutUpperCaseLetter()\n");
        String invalidPwd = "test!_2";
        UserValidator.isValidPassword(invalidPwd);
    }
    
// ================================================================================
    // User registration
    @Test
    public void shouldAddNewUser() throws LoginException {
        System.out.println("* UserValidator: shouldAddNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(validUsername, validPassword);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRejectNewUser() throws LoginException{
        System.out.println("* UserValidator: shouldRejectNewUser()\n");
        String invalidUsername = " rossiMario97";
        String invalidPassword = "test";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(invalidUsername, invalidPassword);
    }
    
    @Test
    public void shouldFindNewUserAfterAdding() throws LoginException, QueryException{
        System.out.println("* UserValidator: shouldFindNewUserAfterAdding()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(validUsername, validPassword);
        Assert.assertTrue(repo.findUser(validUsername));
    }
    
// ================================================================================
    // Equal username
//    @Test
//    public void shouldReturnTrueOnSameUsername(){
//        System.out.println("* UserValidator: shouldReturnTrueOnSameUsername()\n");
//        String validUsername = "rossiMario97";
//        boolean equalUsername = UserValidator.equalsUsername(validUsername);
//        Assert.assertEquals(true, equalUsername);
//    }
    
    
}
