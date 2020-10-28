/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import javax.swing.JButton;

/**
 *
 * @author davidecolombo
 */
public class LoginPanel implements IAppUI{
    
    private JButton loginB;
    private JButton logoutB;
    private JButton signupB;
    
    public LoginPanel(){
        initialize();
    }
    
    private void initialize(){
        loginB = new JButton("Log In");
        logoutB = new JButton("Log Out");
        signupB = new JButton("Sign Up");
    }

    @Override
    public JButton getLoginButton() {
        return this.loginB;
    }

    @Override
    public JButton getSignupButton() {
        return this.signupB;
    }

    @Override
    public JButton getLogoutButton() {
        return this.logoutB;
    }
    
}
