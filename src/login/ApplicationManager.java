/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.ArrayList;
import java.util.List;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.tools.CredentialException;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class ApplicationManager {
    
    private ApplicationRepository repo;
    private List<UserRequest> requests;
    
    public ApplicationManager(){
        this.requests = new ArrayList<>();
        this.repo     = new ApplicationRepository();
    }
    
    public boolean sendLoginRequest(String username, String pwd) throws QueryException {
        this.repo.parseLoginRequest(UserRequest.loginRequest(username, pwd));
        return true;
    }
    
    public boolean sendSignUpRequest(String username, String pwd) throws CredentialException {
        this.repo.parseSignUpRequest(UserRequest.signupRequest(username, pwd));
        return true;
    }
    
    public boolean sendLogoutRequest(String username, String pwd) throws QueryException {
        this.repo.parseLogoutRequest(UserRequest.logoutRequest(username, pwd));
        return true;
    }
    
}
