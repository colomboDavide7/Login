/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repositories;

import login.system.RepositoryInfoRequest;
import login.tools.CredentialException;
import login.system.TransactionRequest;

/**
 *
 * @author davidecolombo
 */
public interface ISystemRepository {
        
    void addNewCustomer(TransactionRequest r) throws CredentialException, TransactionException;
    
    void login(TransactionRequest r) throws TransactionException, AuthorizationException;
    
    void logout(TransactionRequest r) throws TransactionException;
    
    boolean isSignedUp(TransactionRequest r);
    
    boolean isLogged(TransactionRequest r);
    
    boolean matchRepositoryInfo(RepositoryInfoRequest r);
    
    boolean matchRepositoryInfoByValue(RepositoryInfoRequest r, String toMatch);
    
}
