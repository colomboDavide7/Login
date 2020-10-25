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
public class LogoutRequest extends UserRequest{
    
    public LogoutRequest(String username, String pwd) {
        super(username, pwd);
    }
    
}
