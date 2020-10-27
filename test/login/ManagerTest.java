/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.users.UserRequest;
import login.tools.CredentialException;
import login.users.IUser;
import login.users.User;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author davidecolombo
 */
public class ManagerTest {
    
    private ApplicationRepository repo;
    private ApplicationManager manager;
    private Application application;
    
    // This snippet of code is always executed before running every single test
    @Before
    public void setup(){
        this.repo = new ApplicationRepository();
        this.manager = new ApplicationManager();
        this.application = new Application(manager, repo);
    }

    // ================================================================================
    // Application Manager
    @Test
    public void shouldSendSignUpRequest() throws CredentialException{
        System.out.println("* Application Manager: shouldSendSignUpRequest()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
        boolean ans = this.application.sendSignUpRequest(r);
        Assert.assertTrue(ans);
    }
    
    @Test
    public void shouldSendLoginRequest() throws QueryException, CredentialException{
        System.out.println("* Application Manager: shouldSendLoginRequest()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
        
        boolean ans = this.application.sendSignUpRequest(r);
        Assert.assertTrue(ans);
        
        r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
        boolean sended = this.application.sendLoginRequest(r);
        Assert.assertTrue(sended);
    }
    
    @Test
    public void shouldSendLogoutRequest() throws CredentialException, QueryException{
        System.out.println("* Application Manager: shouldSendLoginRequest()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
        boolean ans = this.application.sendSignUpRequest(r);
        Assert.assertTrue(ans);
        
        r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
        boolean sended = this.application.sendLoginRequest(r);
        Assert.assertTrue(sended);
        
        r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGOUT);
        sended = this.application.sendLogoutRequest(r);
        Assert.assertTrue(sended);
    }
}
