/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repository.QueryException;
import login.tools.CredentialException;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public interface IAppModel {
    
    public boolean sendSignUpRequest(UserRequest r) throws CredentialException;
    
    public boolean sendLoginRequest(UserRequest r) throws QueryException;
    
    public boolean sendLogoutRequest(UserRequest r) throws QueryException;
    
}
