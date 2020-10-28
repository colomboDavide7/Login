/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author davidecolombo
 */
public class TitlePanel extends JPanel implements ITitlePanel {
    
    private JLabel title;
    
    public TitlePanel(String title, int w, int h){
        initialize(title, w, h);
    }
    
    private void initialize(String title, int w, int h){
        setName("");
        setLayout(new FlowLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
        //setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
        
        this.title = new JLabel(title);
        this.title.setFont(new Font("Aria", 4, 25));
        this.title.setHorizontalAlignment(JLabel.CENTER);
        this.title.setVerticalAlignment(JLabel.CENTER);
        this.title.setPreferredSize(new Dimension(200, 166));
        add(this.title);
    }

    @Override
    public JLabel getTitleLabel() {
        return this.title;
    }
    
}
