    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repository.TransactionException;
import login.tools.CredentialException;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class Application implements IAppModel {
    
    private AppManager manager;
      
    public Application(AppManager manager){
        this.manager    = manager;
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
