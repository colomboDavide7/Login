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
        
    boolean addNewCustomer(UserRequest r) throws CredentialException;
    
    boolean existsCustomerRepository(UserRequest r) throws QueryException;
    
    boolean isSignedUp(UserRequest r);
    
}
