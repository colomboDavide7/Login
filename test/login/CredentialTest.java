/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.tools.CredentialException;
import login.tools.PropertyValidator;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class CredentialTest {
    
    
// ================================================================================
// Username Tests
    @Test
    public void shouldRefuseEmptyUsername() {
        System.out.println("* UserValidator: shouldRefuseEmptyUsername()\n");
        String empty = "";
        
        try {
            PropertyValidator.isValidUsername(empty);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldRefuseUsernameThatDoesntBeginWithALetter() {
        System.out.println("* UserValidator: shouldRefuseUsernameThatDoesntBeginWithALetter()\n");
        String invalidUsername = "_1test_";
        
        try {
            PropertyValidator.isValidUsername(invalidUsername);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseUsernameWithSpace() {
        System.out.println("* UserValidator: shouldRefuseUsernameWithSpace()\n");
        String invalidUsername = "test ";
        try {
            PropertyValidator.isValidUsername(invalidUsername);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shoulAcceptUsernameWithSymbolNumberUpperCase() throws CredentialException {
        System.out.println("* UserValidator: shoulAcceptUsernameWithSymbolNumberUpperCase()\n");
        String validUsername = "tEst1_";
        boolean login = PropertyValidator.isValidUsername(validUsername);
        Assert.assertEquals(true, login);
    }
    
// ================================================================================
    // Password Tests

    @Test 
    public void shouldRefuseEmptyPassword(){
        System.out.println("* UserValidator: shouldRefuseEmptyPassword()\n");
        String invalidPwd = "";
        
        try {
            PropertyValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutSymbols(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutSymbols()\n");
        String invalidPwd = "test1";
        
        try {
            PropertyValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutNumbers(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutNumbers()\n");
        String invalidPwd = "test!_";
        
        try {
            PropertyValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test 
    public void shouldRefusePasswordWithoutLetters(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutLetters()\n");
        String invalidPwd = "!123#_";
        
        try {
            PropertyValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutUpperCaseLetter(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutUpperCaseLetter()\n");
        String invalidPwd = "test!_2";
        
        try {
            PropertyValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(CredentialException.ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
}
