/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

/**
 *
 * @author davidecolombo
 */
public interface ILogin {
    
    public boolean isLogged();
    
    public boolean isLoggedOut();
    
    public IUser login();
    
    public IUser logout();
    
}
