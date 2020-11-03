    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.UI.IAppUI;
import login.UI.UIFrame;
import login.controllers.UIController;

/**
 *
 * @author davidecolombo
 */
public class Application {
    
    public static void main(String[] args){
        IAppUI frame  = new UIFrame();
        ISystemManager app = new SystemManager();
        
        UIController uiController = new UIController(app, frame);
    }
 
}
