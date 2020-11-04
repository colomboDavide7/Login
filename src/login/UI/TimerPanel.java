/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.UI;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 *
 * @author davidecolombo
 */
public class TimerPanel extends JPanel implements ITimerPanel {
    
    private JButton timerExpired;
    private JLabel description;
    private JLabel seconds;
    private JLabel minutes;
    private JLabel hours;
    private JLabel timeH;
    private JLabel timeM;
    private JLabel timeS;
    
    public TimerPanel(int w, int h){
        initialize(w, h);
    }
   
    private void initialize(int w, int h){
        setName("");
        setOpaque(false);
        setPreferredSize(new Dimension(w+150, h));
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w+100, h));
        
        initDescription();
        initTimeLabels();
        initTimeUnitLabels();
        initButton();
        
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(this.description)
                    .addGroup(layout.createSequentialGroup()
                                .addComponent(timeH, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(hours, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(timeM, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(minutes, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(timeS, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(seconds, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE, 
                                              GroupLayout.PREFERRED_SIZE))
                    .addComponent(timerExpired)
        );
        
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addContainerGap(20, 100)
                    .addComponent(description, 
                                  GroupLayout.PREFERRED_SIZE, 
                                  GroupLayout.PREFERRED_SIZE, 
                                  GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(timeH)
                                .addComponent(hours)
                                .addComponent(timeM)
                                .addComponent(minutes)
                                .addComponent(timeS)
                                .addComponent(seconds))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(timerExpired,  
                                  GroupLayout.PREFERRED_SIZE, 
                                  GroupLayout.PREFERRED_SIZE, 
                                  GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, 100)
        );
        
    }
    
    private void initDescription(){
        this.description = new JLabel("Please, wait timer expires to Log again...");
        this.description.setFont(new Font("Tahoma", 3, 15));
        this.description.setPreferredSize(new Dimension(75, 40));
    }
    
    private void initTimeLabels(){
        this.timeH = new JLabel("00");
            this.timeH.setFont(new Font("Times", 3, 15));
        this.timeM = new JLabel("00");
            this.timeM.setFont(new Font("Times", 3, 15));
        this.timeS = new JLabel("00");
            this.timeS.setFont(new Font("Times", 3, 15));
    }
    
    private void initTimeUnitLabels(){
        this.hours = new JLabel("hours");
            this.hours.setFont(new Font("Times", 3, 15));
        this.minutes = new JLabel("minutes");
            this.minutes.setFont(new Font("Times", 3, 15));
        this.seconds = new JLabel("seconds");
            this.seconds.setFont(new Font("Times", 3, 15));
    }
    
    private void initButton(){
        this.timerExpired = new JButton("back");
        timerExpired.setPreferredSize(new Dimension(20, 20));
    }

    @Override
    public void setStartTime(int hours, int minutes, int seconds) {
        this.timeH.setText(Integer.toString(hours));
        this.timeM.setText(Integer.toString(minutes));
        this.timeS.setText(Integer.toString(seconds));
    }

    @Override
    public void setHours(int hours) {
        this.timeH.setText(Integer.toString(hours));
    }

    @Override
    public void setMinutes(int minutes) {
        this.timeM.setText(Integer.toString(minutes));
    }

    @Override
    public void setSeconds(int seconds) {
        this.timeS.setText(Integer.toString(seconds));
    }

    @Override
    public JButton getTimerExpiredButton() {
        return this.timerExpired;
    }
    
}
