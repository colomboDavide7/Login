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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class SignupForm implements ISignupForm {
    
    private Map<UserProperty, JTextComponent> form;
    
    public SignupForm(){
        initialize();
    }
    
    private void initialize(){
        createFormField();
        this.form.keySet().forEach(p -> createFocusListener(p, this.form.get(p)));
    }

    private void createFormField(){
        this.form = new HashMap<>();
        for(UserProperty p : UserProperty.values())
            if(p == UserProperty.PASSWORD)
                form.put(p, new JPasswordField(30));
            else
                form.put(p, new JTextField(p.toString(), 30));
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
                if(c.getText().isEmpty())
                    c.setText(p.name());
            }
        });
    }
    
    @Override
    public JTextComponent getPropertyField(UserProperty p) {
        return this.form.get(p);
    }

    SignupForm setCommonDimension(Dimension d) {
        for(UserProperty p : UserProperty.values())
            this.form.get(p).setPreferredSize(d);
        return this;
    }

    @Override
    public Map<UserProperty, String> getInsertedProperties() {
        Map<UserProperty, String> properties = new HashMap<>();
        form.keySet().stream()
                     .filter(p -> (!form.get(p).getText().equals(p.toString())))
                     .forEach(p -> properties.put(p, form.get(p).getText())); 
        return properties;
    }

    @Override
    public void highlightMissing(List<UserProperty> missing) {
        form.keySet().stream()
                     .filter(p -> (missing.contains(p)))
                     .forEach(p -> form.get(p).setBorder(
                                BorderFactory.createEtchedBorder(Color.RED, Color.RED)));
    }

    @Override
    public void clear() {
        this.form.keySet().stream()
                          .filter(p -> p != UserProperty.PASSWORD)
                          .forEach(p -> this.form.get(p).setText(p.toString()));
        this.form.keySet().stream()
                          .filter(p -> p == UserProperty.PASSWORD)
                          .forEach(p -> this.form.get(p).setText(""));
    }
    
}
