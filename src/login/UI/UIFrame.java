/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author davidecolombo
 */
public class UIFrame extends JFrame implements IAppUI {
    
    private ILoginPanel loginPanel;
    private JPanel signupPanel;
    private JPanel descPanel;
    private JPanel titlePanel;
            
    private final int heigthInPixel = 500;
    private final int widthInPixel  = 500;
            
    public UIFrame(){
        initialize();
        javax.swing.SwingUtilities.invokeLater(() -> {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Home Page");
            pack();
            setVisible(true);
        });
    }
    
    private void initialize(){
        setLayout(new BorderLayout());
        
        createLoginPanel();
        
    }
    
    private void createLoginPanel(){
        this.loginPanel = new LoginPanel(Math.round(widthInPixel / 3), heigthInPixel);
        this.getContentPane().add((Component) this.loginPanel, BorderLayout.EAST);
    }
    
    @Override
    public JButton getSignupButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JButton getLogoutButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ILoginPanel getLoginPanel() {
        return this.loginPanel;
    }
    
}
