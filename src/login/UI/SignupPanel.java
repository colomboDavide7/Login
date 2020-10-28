/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

/**
 *
 * @author davidecolombo
 */
public class SignupPanel extends JPanel implements ISignupPanel {
    
    private JButton signupB;
    private JLabel info;
    private JTable form;
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
                    .addComponent(this.form)
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
                    .addComponent(this.form)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(this.signupB)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(this.scroll)
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
        this.errorCommunicationField = new JTextArea(20, 200);
        this.errorCommunicationField.setPreferredSize(new Dimension(200, 20));
        this.errorCommunicationField.setForeground(Color.red);
        
        this.scroll = new JScrollPane(this.errorCommunicationField);
        this.scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scroll.setPreferredSize(new Dimension(50, 40));
    }
    
    private void initFormTable(){
        this.form = new JTable(4, 3);
    }

    @Override
    public JButton getSignupButton() {
        return this.signupB;
    }

    @Override
    public JTextArea getErrorCommunicationField() {
        return this.errorCommunicationField;
    }
    
}
