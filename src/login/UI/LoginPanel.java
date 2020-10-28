/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

/**
 *
 * @author davidecolombo
 */
public class LoginPanel extends JPanel implements ILoginPanel {
    
    private JButton loginB;
    private JTextField username;
    private JPasswordField pwd;
    
    public LoginPanel(int w, int h){
        initialize(w, h);
    }
    
    private void initialize(int w, int h){
        setPreferredSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        setName("");
        setOpaque(false);
        
        // Init components
        initButton();
        initUsername();
        initPassword();
        
        // Layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.username)
                      .addComponent(this.pwd)
                      .addComponent(this.loginB)
        );
        
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(20, 100)
                        .addComponent(this.username, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(this.pwd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(this.loginB)
                        .addContainerGap(20, 100)
        );
        
    }
    
    private void initButton(){
        loginB = new JButton("Log In");
        loginB.setForeground(Color.BLACK);
        loginB.setBackground(Color.GREEN);
        loginB.setFont(new Font("Times", 12, 15));
        loginB.setOpaque(true);
        loginB.setBorderPainted(false);
    }

    private void initUsername(){
        this.username = new JTextField(20);
        this.username.setPreferredSize(new Dimension(20, 20));
    }
    
    private void initPassword(){
        this.pwd = new JPasswordField(20);
        this.pwd.setPreferredSize(new Dimension(20, 20));
    }
    
    @Override
    public JButton getLoginButton() {
        return this.loginB;
    }

    @Override
    public String getUsernameField() {
        return this.username.getText();
    }

    @Override
    public String getPasswordField() {
        return Arrays.toString(this.pwd.getPassword());
    }
    
}
