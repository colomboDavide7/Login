/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import login.tools.CredentialException;
import login.tools.UserProperty;
import login.users.UserRequest;


/**
 *
 * @author davidecolombo
 */
public class AppRepository implements IAppRepository {

    private final String SUBSCRIPTION_FILE = "subscriptions.txt";
    
    private File subscriptions;
    private boolean IS_APPEND_MODE = true;
    private List<UserRepository> usersRepo;
    
    public AppRepository(){
        subscriptions = new File(SUBSCRIPTION_FILE);
        usersRepo     = new ArrayList<>();
    }
            
    @Override
    public void addNewCustomer(UserRequest r) throws CredentialException {
        if(isSignedUp(r))
            throw new CredentialException(CredentialException.ErrorCode.USERNAME_ALREADY_USED);
        r.processSignupRequest(subscriptions, IS_APPEND_MODE);
        this.usersRepo.add(UserRepository.createUserRepository(r));
    }
    
    @Override
    public boolean isSignedUp(UserRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                return repo.verifyExistanceOfOneAndOnlyOneSignupTransaction();
        return false;
    }

    @Override
    public void login(UserRequest r) {
        if(isLogged(r)){
            System.err.println("already logged");
            return;
        }
        
        this.usersRepo.stream()
                      .filter((repo) -> (repo.matchOwner(r.getUserProperty(UserProperty.USERNAME))))
                      .forEachOrdered(repo -> repo.addTransaction(r));
    }

    @Override
    public boolean isLogged(UserRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                return repo.verifyLastLoginTransactionDateAndTime();
        return false;
    }
    
}
