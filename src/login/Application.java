    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repository.AppRepository;
import login.repository.TransactionException;
import login.tools.CredentialException;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class Application implements IAppModel {
    
    private AppRepository repository;
    private ApplicationManager manager;
      
    public Application(ApplicationManager manager, AppRepository repo){
        this.manager    = manager;
        this.repository = repo;
    }
    
    @Override
    public boolean sendSignUpRequest(UserRequest r) throws CredentialException {
        this.manager.parseSignUpRequest(r);
        return true;
    }
    
    @Override
    public boolean sendLoginRequest(UserRequest r) throws TransactionException{
        this.manager.parseLoginRequest(r);
        return true;
    }
    
    @Override
    public boolean sendLogoutRequest(UserRequest r) throws TransactionException{
        this.manager.parseLogoutRequest(r);
        return true;
    }
 
}
