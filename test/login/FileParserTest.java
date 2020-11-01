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
import login.repository.AppRepository;
import login.repository.IAppRepository;
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
import org.junit.Before;

/**
 *
 * @author davidecolombo
 */
public class FileParserTest {
    
    private File f;
    private IAppRepository repo;
    
    public FileParserTest(){
        this.f = new File("customer.txt");
        this.repo = new AppRepository();
    }
    
    @Before
    public void setupCustomerFile(){
        clearFile();
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
    
//    @Test
//    public void shouldMatchUserNameProperty() {
//        System.out.println("* File Parser: shouldMatchUserNameProperty()\n");
//        String toMatch = "marioRossi";
//        File f = new File("user1.txt");
//        
//        try{
//            boolean match = FileParser.existsProperty(f, UserProperty.USERNAME, toMatch);
//            assertTrue(match);
//            match = FileParser.existsProperty(f, UserProperty.USERNAME, "notPresent");
//            assertFalse(match);
//        }catch(ParserSchemeException | FileNotFoundException ex){
//            assertTrue(false);
//        }
//    }
    
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
        } catch (CustomerCreationException ex) {
            assertFalse(true);
        } catch (CredentialException ex) {
            assertTrue(false);
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
        } catch (CustomerCreationException ex) {
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
            
        } catch (CustomerCreationException ex) {
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
        } catch (CustomerCreationException | CredentialException ex) {
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
        } catch (CustomerCreationException | CredentialException ex) {
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
        } catch (CustomerCreationException | CredentialException ex) {
            assertTrue(false);
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
    // Clear file logic
    private void clearFile(){
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(this.f));
            writer.append("");
            writer.close();
        }catch(IOException ex){
            System.err.println("Error reading the file\n");
        }finally{
            if(writer != null)
                try {
                    writer.close();
            } catch (IOException ex) {
                    System.err.println("Error closing the file\n");
            }
        }
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
