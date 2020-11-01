/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class LoginPanel extends JPanel implements ILoginPanel {
    
    private final String explanatoryMsg = 
            "The errors that could occur in the login process will be displayed here...";
    private JButton loginB;
    private JTextField username;
    private JPasswordField pwd;
    private JTextArea errorCommunicationField;
    private JScrollPane scroll;
    private JLabel info;
    
    public LoginPanel(int w, int h){
        initialize(w, h);
    }
    
    private void initialize(int w, int h){
        setPreferredSize(new Dimension(w+100, h));
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w+100, h));
        setName("");
        setOpaque(false);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
        
        // Init components
        initButton();
        initUsername();
        initPassword();
        initErrorCommunicationField();
        initInfoLabel();
        
        // Layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.info)
                      .addComponent(this.username)
                      .addComponent(this.pwd)
                      .addComponent(this.loginB)
                      .addComponent(this.scroll)
        );
        
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(20, 100)
                        .addComponent(this.info, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE)
                        .addGap(45)
                        .addComponent(this.username, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(this.pwd, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
                        .addComponent(this.loginB, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE)
                        .addGap(50)
                        .addComponent(this.scroll, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE, 
                                      GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, 100)      
        );
        
    }
    
    private void createFocusListener(UserProperty p, JTextComponent c){
        c.setFocusable(true);
        c.grabFocus();
        c.setFont(new Font("Times", Font.ITALIC, 14));
        c.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(c.getText().equals(p.toString()))
                    c.setText("");
                
                if(!c.getBorder().equals(Border.EMPTY))
                    c.setBorder(BorderFactory.createEmptyBorder());
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(c.getText().isEmpty() && p != UserProperty.PASSWORD)
                    c.setText(p.name());
            }
        });
    }
    
    private void initInfoLabel(){
        this.info = new JLabel("Enter your user credential...");
        this.info.setFont(new Font("Tahoma", 3, 15));
        this.info.setPreferredSize(new Dimension(75, 20));
    }
    
    private void initButton(){
        loginB = new JButton("Log In");
        loginB.setPreferredSize(new Dimension(20, 20));
    }

    private void initUsername(){
        this.username = new JTextField(UserProperty.USERNAME.toString(), 30);
        this.username.setPreferredSize(new Dimension(30, 20));
        createFocusListener(UserProperty.USERNAME, username);
    }
    
    private void initPassword(){
        this.pwd = new JPasswordField(30);
        this.pwd.setPreferredSize(new Dimension(30, 20));
    }
    
    private void initErrorCommunicationField(){
        this.errorCommunicationField = new JTextArea(explanatoryMsg, 20, 200);
        this.errorCommunicationField.setPreferredSize(new Dimension(200, 20));
        this.errorCommunicationField.setForeground(Color.red);
        this.errorCommunicationField.addFocusListener(new FocusListener() {
            String tempMsg = explanatoryMsg;
            
            @Override
            public void focusGained(FocusEvent e) {
                if(errorCommunicationField.getForeground() == Color.GREEN)
                    errorCommunicationField.setForeground(Color.red);
                errorCommunicationField.setText(tempMsg);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!errorCommunicationField.getText().equals(explanatoryMsg))
                    tempMsg = errorCommunicationField.getText();
                else
                    tempMsg = explanatoryMsg;
            }
        });
        
        this.scroll = new JScrollPane(this.errorCommunicationField);
        this.scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scroll.setPreferredSize(new Dimension(50, 40));
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
        return this.pwd.getText();
    }

    @Override
    public JTextArea getErrorArea() {
        return this.errorCommunicationField;
    }

    @Override
    public void highlightMissing(List<UserProperty> missing) {
        for(UserProperty p : missing)
            System.out.println("missing = " + p);

        if(missing.contains(UserProperty.USERNAME))
            this.username.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        if(missing.contains(UserProperty.PASSWORD))
            this.pwd.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED)); 
    }
    
}
