/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.repository.AppRepository;
import login.repository.IAppRepository;
import login.repository.TransactionException;
import login.tools.CredentialException;
import login.tools.FileParser;
import login.tools.ParserSchemeException;
import login.tools.UserProperty;
import login.users.CustomerCreationException;
import login.users.IUser;
import login.users.User;
import login.users.UserRequest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davidecolombo
 */
public class FileParserTest {
    
    private IAppRepository repo;
    
    public FileParserTest(){
        this.repo = new AppRepository();
    }
    
    @Test
    public void shouldReturnParserSchemeException() throws FileNotFoundException{
        System.out.println("* File Parser: shouldReturnParserSchemeException()\n");
        File f = new File("shouldReturnParserSchemeException.txt");
        try{
            List<String> lines = this.openAndReadedTextFile(f);
            for(String l : lines)
                FileParser.validateRecordScheme(l);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_SCHEME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldReturnWrongKeyValueSeparatorError() throws FileNotFoundException {
        System.out.println("* File Parser: shouldReturnWrongKeyValueSeparatorError()\n");
        File f = new File("shouldReturnWrongKeyValueSeparatorError.txt");
        
        try{
            List<String> lines = this.openAndReadedTextFile(f);
            for(String l : lines)
                FileParser.validateRecordScheme(l);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldValidateUserRecord(){
        System.out.println("* File Parser: shouldValidateUserRecord()\n");
        try {
            IUser newCustomer = this.createValidUser();
            boolean isValid = FileParser.validateRecordScheme(newCustomer.createRecord());
            assertTrue(isValid);
        } catch (CustomerCreationException | ParserSchemeException ex) {
            assertFalse(true);
        }
        
    }
       
    @Test
    public void shouldExistsAfterSignup(){
        System.out.println("* File Parser: shouldExistsAfterSignup()\n");            
        try {
            IUser newCustomer = this.createValidUser();
            UserRequest r = UserRequest.createRequestByType(newCustomer, UserRequest.RequestType.SIGN_UP);
            this.repo.addNewCustomer(r);
            
            boolean exists = repo.isSignedUp(r);
            assertTrue(exists);
        } catch (CustomerCreationException | TransactionException | CredentialException ex) {
            assertFalse(true);
        }
        
    }
    
    @Test
    public void shouldReturnUsernameAlreadyUsedError(){
        System.out.println("* File Parser: shouldReturnUsernameAlreadyUsedError()\n");
        try {
            IUser newCustomer = this.createValidUser();
            this.repo.addNewCustomer(
                    UserRequest.createRequestByType(
                            newCustomer, UserRequest.RequestType.SIGN_UP
                    )
            );
            
            this.repo.addNewCustomer(
                    UserRequest.createRequestByType(
                            newCustomer, UserRequest.RequestType.SIGN_UP
                    )
            );
            
            assertTrue(false);
        } catch (CustomerCreationException | TransactionException ex) {
            assertTrue(false);
        } catch (CredentialException ex) {
            assertEquals(CredentialException.ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldContainTwoUsersInDatabase(){
        System.out.println("* File Parser: shouldContainTwoUsersInDatabase()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            UserRequest req1   = UserRequest.createRequestByType(newCustomer1, UserRequest.RequestType.SIGN_UP);
            this.repo.addNewCustomer(req1);
            
            IUser newCustomer2 = this.createSecondValidUser();
            UserRequest req2   = UserRequest.createRequestByType(newCustomer2, UserRequest.RequestType.SIGN_UP);
            this.repo.addNewCustomer(req2);
            
            boolean exists1 = this.repo.isSignedUp(req1);
            boolean exists2 = this.repo.isSignedUp(req2);
            assertTrue(exists1 && exists2);
            
        } catch (CustomerCreationException | TransactionException ex) {
            assertTrue(false);
        } catch (CredentialException ex) {
            assertEquals(CredentialException.ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldCreateUserRepository(){
        System.out.println("* File Parser: shouldCreateUserRepository()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            UserRequest r = UserRequest.createRequestByType(
                    newCustomer1, UserRequest.RequestType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            boolean exists = this.repo.isSignedUp(r);
            assertTrue(exists);
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
        
    @Test
    public void shouldHaveSignupTransaction(){
        System.out.println("* File Parser: shouldHaveOneTransactionAtCreation()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            UserRequest r = UserRequest.createRequestByType(
                    newCustomer1, UserRequest.RequestType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            boolean signedUp = this.repo.isSignedUp(r);
            assertTrue(signedUp);
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldSendLoginTransaction(){
        System.out.println("* File Parser: shouldSendLoginTransaction()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            UserRequest r = UserRequest.createRequestByType(
                    newCustomer1, UserRequest.RequestType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            UserRequest loginRequest = 
                    UserRequest.createRequestByType(newCustomer1, UserRequest.RequestType.LOGIN);
            this.repo.login(loginRequest);
            boolean logged = this.repo.isLogged(loginRequest);
            assertTrue(logged);
            
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldThrowTransactionException(){
        System.out.println("* File Parser: shouldSendLoginTransaction()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            UserRequest r = UserRequest.createRequestByType(
                    newCustomer1, UserRequest.RequestType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            UserRequest loginRequest = 
                    UserRequest.createRequestByType(newCustomer1, UserRequest.RequestType.LOGIN);
            this.repo.login(loginRequest);
            this.repo.login(loginRequest);
            
            assertTrue(false);
            
        } catch (CustomerCreationException | CredentialException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.ALREADY_LOGGED_IN, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseWrongTransaction(){
        System.out.println("* File Parser: shouldRefuseWrongTransaction()\n");
        try {
            // Signup
            IUser newCustomer1 = this.createValidUser();
            UserRequest r = UserRequest.createRequestByType(
                    newCustomer1, UserRequest.RequestType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            // Login
            UserRequest wrong = 
                    UserRequest.createRequestByType(newCustomer1, UserRequest.RequestType.LOGOUT);
            this.repo.login(wrong);
            
        } catch (CustomerCreationException | CredentialException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.WRONG_REQUEST, ex.getErrorCode());
        }
    }
    
        
// ====================================================================================
    // Stream opening logic
    private List<String> openAndReadedTextFile(File f){
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        String line;
        
        try{
            reader = new BufferedReader(new FileReader(f));
            while(true){
                line = reader.readLine();
                if(line == null){
                    reader.close();
                    break;
                }
                
                lines.add(line);
            }
            
        }catch(IOException ex){
            System.err.println("Error reading the file\n");
        }finally{
            if(reader != null)
                try {
                    reader.close();
            } catch (IOException ex) {
                    System.err.println("Error closing the file\n");
            }
        }
        
        return lines;
    }
        
// ====================================================================================
    // User creation logic
    private IUser createValidUser() throws CustomerCreationException{
        String username  = "newCustomer";
        String pwd       = "Test1!";
        String firstName = "new";
        String lastName  = "customer";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
        return User.getBasicUser(basicProperties);
    }
    
    private IUser createSecondValidUser() throws CustomerCreationException{
        String username  = "marioRossi99";
        String pwd       = "Test1!";
        String firstName = "Mario";
        String lastName  = "Rossi";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
        return User.getBasicUser(basicProperties);
    }
    
    private IUser createInvalidUser() throws CustomerCreationException{
        String username  = "newCustomer";
        String pwd       = "Test";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
        return User.getBasicUser(basicProperties);
    }
    
// ====================================================================================

}
