/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repositories.TransactionException;
import login.tools.CredentialException;
import login.system.UserRequest;

/**
 *
 * @author davidecolombo
 */
public interface ISystemManager {
    
    public void parseSignUpRequest(UserRequest r) throws CredentialException;
    
    public void parseLoginRequest(UserRequest r) throws TransactionException;
    
    public void parseLogoutRequest(UserRequest r) throws TransactionException;
    
}
