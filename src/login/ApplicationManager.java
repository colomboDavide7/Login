/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.ArrayList;
import java.util.List;
import login.repository.ApplicationRepository;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class ApplicationManager {
    
    private ApplicationRepository repo;
    private List<UserRequest> requests;
    
    public ApplicationManager(){
        this.requests = new ArrayList<>();
        this.repo     = new ApplicationRepository();
    }
    
}
