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
public class Application implements IAppModel {
    
    private ApplicationRepository repository;
    private ApplicationManager manager;
       
    public Application(ApplicationManager manager, ApplicationRepository repo){
        this.manager    = manager;
        this.repository = repo;
    }
    
    @Override
    public boolean sendSignUpRequest(UserRequest r) throws CredentialException {
        this.manager.parseSignUpRequest(r);
        return true;
    }
    
    @Override
    public boolean sendLoginRequest(UserRequest r) throws QueryException{
        this.manager.parseLoginRequest(r);
        return true;
    }
    
    @Override
    public boolean sendLogoutRequest(UserRequest r) throws QueryException{
        this.manager.parseLogoutRequest(r);
        return true;
    }
 
}
