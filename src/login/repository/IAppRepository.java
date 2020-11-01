/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import login.tools.CredentialException;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public interface IAppRepository {
        
    void addNewCustomer(UserRequest r) throws CredentialException, TransactionException;
    
    void login(UserRequest r) throws TransactionException;
    
    void logout(UserRequest r) throws TransactionException;
    
    boolean isSignedUp(UserRequest r);
    
    boolean isLogged(UserRequest r);
    
}
