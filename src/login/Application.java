    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.UI.IAppUI;
import login.UI.TimerPanel;
import login.UI.UIFrame;
import login.controllers.TimerController;
import login.controllers.TimerControllerInterface;
import login.controllers.UIController;
import login.controllers.UIControllerInterface;

/**
 *
 * @author davidecolombo
 */
public class Application {
    
    public static void main(String[] args){
        IAppUI frame  = new UIFrame();
        ISystemManager app = new SystemManager();
        
        UIControllerInterface uiController = new UIController(app, frame);
        TimerControllerInterface timerController = new TimerController(
                new TimerPanel(Math.round(500 / 3), 500), uiController
        );
        
        uiController.setTimerController(timerController);
    }
 
}
