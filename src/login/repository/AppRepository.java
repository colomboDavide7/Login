/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import login.tools.CredentialException;
import login.tools.FileParser;
import login.tools.ParserSchemeException;
import login.tools.UserProperty;
import login.users.UserRequest;


/**
 *
 * @author davidecolombo
 */
public class AppRepository implements IAppRepository {

    private final String CUSTOMER_FILE = "customer.txt";
    
    private List<File> files;
    private List<UserRepository> usersRepo;
    
    public AppRepository(){
        files     = new ArrayList<>();
        usersRepo = new ArrayList<>();
        setup();
    }
    
    private void setup(){
        this.files.add(new File(CUSTOMER_FILE));
    }
    
//    @Override
//    public boolean existsCustomerProperty(UserProperty p, String value) {
//        try {
//            return FileParser.existsProperty(getFileByName(CUSTOMER_FILE), p, value);
//        } catch (FileNotFoundException ex) {
//            System.err.println("File: \"" + CUSTOMER_FILE + "\" not found");
//            System.exit(-1);
//        } catch (ParserSchemeException ex) {
//            System.err.println("Invalid parser scheme: " + ex.getErrorCode());
//            System.exit(-1);
//        }
//        return false;
//    }
    
    @Override
    public boolean addNewCustomer(UserRequest r) throws CredentialException {
        try {
            if(existsCustomerRepository(r))
                throw new CredentialException(CredentialException.ErrorCode.USERNAME_ALREADY_USED);
            r.addNewCustomerToCustomerFile(getFileByName(CUSTOMER_FILE));
            this.usersRepo.add(UserRepository.createUserRepository(r));
        } catch (FileNotFoundException ex) {
            System.err.println("File: \"" + CUSTOMER_FILE + "\" not found");
            System.exit(-1);
        }
        
        return true;
    }
    
    private File getFileByName(String filename) throws FileNotFoundException{
        for(File f : files)
            if(f.getName().equals(filename))
                return f;
        throw new FileNotFoundException(filename);
    }

    @Override
    public boolean existsCustomerRepository(UserRequest req) {
        return this.usersRepo.stream()
                             .anyMatch((r) -> (r.matchOwner(req.getUserProperty(UserProperty.USERNAME))));
    }
        
}
