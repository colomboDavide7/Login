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
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.text.JTextComponent;
import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class SignupPanel extends JPanel implements ISignupPanel {
    
    private final String explanatoryMsg = 
            "The errors that could occur in the signup process will be displaied here...";
    
    private JButton signupB;
    private JLabel info;
    private ISignupForm form;
    private JTextArea errorCommunicationField;
    private JScrollPane scroll;
    
    public SignupPanel(int w, int h){
        this.initialize(w, h);
    }
    
    private void initialize(int w, int h){
        setPreferredSize(new Dimension(w+200, h));
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w+200, h));
        setName("");
        setOpaque(false);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));

        initButton();
        initInfoLabel();
        initFormTable();
        initErrorCommunicationArea();
                
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(this.info)
                .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(this.form.getPropertyField(UserProperty.USERNAME))
                                        .addComponent(this.form.getPropertyField(UserProperty.FIRST_NAME))
                                        .addComponent(this.form.getPropertyField(UserProperty.AGE))
                                        .addComponent(this.form.getPropertyField(UserProperty.MAIN_PHONE)))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(this.form.getPropertyField(UserProperty.LAST_NAME))
                                        .addComponent(this.form.getPropertyField(UserProperty.GENDER))
                                        .addComponent(this.form.getPropertyField(UserProperty.SECOND_PHONE)))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(this.form.getPropertyField(UserProperty.PASSWORD))
                                        .addComponent(this.form.getPropertyField(UserProperty.BIRTH))
                                        .addComponent(this.form.getPropertyField(UserProperty.CITY))
                                        .addComponent(this.form.getPropertyField(UserProperty.E_MAIL)))
                )
               .addComponent(this.signupB)
               .addComponent(this.scroll)
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                    .addContainerGap(30, 50)
                    .addComponent(this.info, 
                                  GroupLayout.PREFERRED_SIZE, 
                                  GroupLayout.PREFERRED_SIZE,
                                  GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                                .addComponent(this.form.getPropertyField(UserProperty.USERNAME), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.PASSWORD), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                                .addComponent(this.form.getPropertyField(UserProperty.FIRST_NAME), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.LAST_NAME), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.BIRTH), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                                .addComponent(this.form.getPropertyField(UserProperty.AGE), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.GENDER), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.CITY), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                                .addComponent(this.form.getPropertyField(UserProperty.MAIN_PHONE), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.SECOND_PHONE), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.form.getPropertyField(UserProperty.E_MAIL), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(this.signupB)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(this.scroll,
                                  GroupLayout.PREFERRED_SIZE, 
                                  GroupLayout.PREFERRED_SIZE,
                                  GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, 100)
        );
    }
    
    private void initButton(){
        this.signupB = new JButton("Sign Up");
        this.signupB.setPreferredSize(new Dimension(20, 15));
    }
    
    private void initInfoLabel(){
        this.info = new JLabel("Please, compile the form below to sign up...");
        this.info.setFont(new Font("Tahoma", 3, 15));
        this.info.setPreferredSize(new Dimension(75, 30));
    }
    
    private void initErrorCommunicationArea(){
        this.errorCommunicationField = new JTextArea(explanatoryMsg, 20, 200);
        this.errorCommunicationField.setFont(new Font("Times", Font.ITALIC, 14));
        this.errorCommunicationField.setPreferredSize(new Dimension(200, 20));
        this.errorCommunicationField.setForeground(Color.red);
        this.errorCommunicationField.addFocusListener(new FocusListener() {
            String tempMsg = explanatoryMsg;
            
            @Override
            public void focusGained(FocusEvent e) {
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
    
    private void initFormTable(){
        this.form = new SignupForm().setCommonDimension(new Dimension(30, 20));
    }

    @Override
    public JButton getSignupButton() {
        return this.signupB;
    }

    @Override
    public JTextArea getErrorCommunicationField() {
        return this.errorCommunicationField;
    }

    @Override
    public JTextComponent getPropertyField(UserProperty p) {
        return this.form.getPropertyField(p);
    }

    @Override
    public Map<UserProperty, String> getInsertedProperties() {
        return this.form.getInsertedProperties();
    }
    
}
