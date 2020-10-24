/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.repository.LoginException;
import login.tools.UserValidator;
import login.users.User;
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
        boolean login = UserValidator.isValidUsername(empty);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefuseUsernameThatDoesntBeginWithALetter() {
        System.out.println("* UserValidator: shouldRefuseUsernameThatDoesntBeginWithALetter()\n");
        String invalidUsername = "_1test_";
        boolean login = UserValidator.isValidUsername(invalidUsername);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefuseUsernameWithSpace() {
        System.out.println("* UserValidator: shouldRefuseUsernameWithSpace()\n");
        String invalidUsername = "test ";
        boolean login = UserValidator.isValidUsername(invalidUsername);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shoulAcceptUsernameWithSymbolNumberUpperCase() {
        System.out.println("* UserValidator: shoulAcceptUsernameWithSymbolNumberUpperCase()\n");
        String validUsername = "tEst1_";
        boolean login = UserValidator.isValidUsername(validUsername);
        Assert.assertEquals(true, login);
    }
    
// ================================================================================
    // Password Tests

    @Test
    public void shouldRefuseEmptyPassword(){
        System.out.println("* UserValidator: shouldRefuseEmptyPassword()\n");
        String invalidPwd = "";
        boolean login = UserValidator.isValidPassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutSymbols(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutSymbols()\n");
        String invalidPwd = "test1";
        boolean login = UserValidator.isValidPassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutNumbers(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutNumbers()\n");
        String invalidPwd = "test!_";
        boolean login = UserValidator.isValidPassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutLetters(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutLetters()\n");
        String invalidPwd = "!123#_";
        boolean login = UserValidator.isValidPassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
    @Test
    public void shouldRefusePasswordWithoutUpperCaseLetter(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutUpperCaseLetter()\n");
        String invalidPwd = "test!_2";
        boolean login = UserValidator.isValidPassword(invalidPwd);
        Assert.assertEquals(false, login);
    }
    
// ================================================================================
    // User registration
    @Test
    public void shouldAddNewUser() throws LoginException {
        System.out.println("* ApplicationRepository: shouldAddNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(validUsername, validPassword);
        Assert.assertTrue(true);
    }
    
    @Test (expected = LoginException.class)
    public void shouldRejectNewUser() throws LoginException{
        System.out.println("* ApplicationRepository: shouldRejectNewUser()\n");
        String invalidUsername = " rossiMario97";
        String invalidPassword = "test";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(invalidUsername, invalidPassword);
    }
    
    @Test
    public void shouldFindUserAfterAdding() throws LoginException, QueryException{
        System.out.println("* ApplicationRepository: shouldFindUserAfterAdding()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(validUsername, validPassword);
        User searchedUser = repo.findUser(validUsername);
        Assert.assertEquals(validUsername, searchedUser.username);
    }

    @Test (expected = LoginException.class)
    public void shouldRejectUserWithSameName() throws LoginException{
        System.out.println("* ApplicationRepository: shouldRejectUserWithSameName()\n");
        String sameUsername = "rossiMario97";
        ApplicationRepository repo = new ApplicationRepository();
        repo.addUser(sameUsername, "tEst!1");
        // This condition has the only purpose to provide an evidence that
        // the program steps over the first read user and so that is the second
        // call to addUser method to throw the LoginException
        Assert.assertTrue(true);
        repo.addUser(sameUsername, "anotherValid_23");
    }
    
}
