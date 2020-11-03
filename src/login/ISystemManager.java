/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repositories.AuthorizationException;
import login.repositories.TransactionException;
import login.tools.CredentialException;
import login.system.TransactionRequest;

/**
 *
 * @author davidecolombo
 */
public interface ISystemManager {
    
    public void parseSignUpRequest(TransactionRequest r) throws CredentialException;
    
    public void parseLoginRequest(TransactionRequest r) throws TransactionException, AuthorizationException;
    
    public void parseLogoutRequest(TransactionRequest r) throws TransactionException;
    
}
