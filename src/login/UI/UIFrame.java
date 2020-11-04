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

/**
 *
 * @author davidecolombo
 */
public class UIFrame extends JFrame implements IAppUI {
    
    private ILoginPanel loginPanel;
    private ISignupPanel signupPanel;
    private ITitlePanel titlePanel;
    private ITimerPanel timerPanel;
    
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
        
        createTitlePanel();
        createLoginPanel();
        createSignupPanel();
    }
    
    private void createTitlePanel(){
        this.titlePanel = new TitlePanel("Home Page", widthInPixel, Math.round(this.heigthInPixel / 3));
        this.getContentPane().add((Component) titlePanel, BorderLayout.NORTH);
    }
    
    private void createLoginPanel(){
        this.loginPanel = new LoginPanel(Math.round(widthInPixel / 3), heigthInPixel);
        this.getContentPane().add((Component) this.loginPanel, BorderLayout.EAST);
    }
    
    private void createSignupPanel(){
        this.signupPanel = new SignupPanel(
                                    Math.round(2*widthInPixel / 3), 
                                    Math.round(2*heigthInPixel / 3));
        this.getContentPane().add((Component) signupPanel, BorderLayout.CENTER);
    }

    @Override
    public JButton getLogoutButton() {
        return new JButton("Log Out");
    }

    @Override
    public ILoginPanel getLoginPanel() {
        return this.loginPanel;
    }

    @Override
    public ITitlePanel getTitlePanel() {
        return this.titlePanel;
    }

    @Override
    public ISignupPanel getSignupPanel() {
        return this.signupPanel;
    }

    @Override
    public void appendTimerPanel(ITimerPanel p) {
        this.timerPanel = p;
        this.getContentPane().remove((Component) this.loginPanel);
        this.getContentPane().add((Component) this.timerPanel, BorderLayout.EAST);
        this.getContentPane().validate();
    }

    @Override
    public void appendLoginPanel() {
        this.getContentPane().remove((Component) this.timerPanel);
        this.getContentPane().add((Component) this.loginPanel, BorderLayout.EAST);
//        this.getContentPane().repaint();
        this.getContentPane().validate();
    }
    
}
