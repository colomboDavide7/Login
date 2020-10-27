    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.tools.CredentialException;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class Application {
    
    private ApplicationRepository repository;
    private ApplicationManager manager;
       
    public Application(ApplicationManager manager, ApplicationRepository repo){
        this.manager    = manager;
        this.repository = repo;
    }
    
    public boolean sendSignUpRequest(String username, String pwd) throws CredentialException{
        this.manager.parseSignUpRequest(UserRequest.signupRequest(username, pwd));
        return true;
    }
    
    public boolean sendLoginRequest(String username, String pwd) throws QueryException{
        this.manager.parseLoginRequest(UserRequest.signupRequest(username, pwd));
        return true;
    }
    
    public boolean sendLogoutRequest(String username, String pwd) throws QueryException{
        this.manager.parseLogoutRequest(UserRequest.signupRequest(username, pwd));
        return true;
    }
 
}
