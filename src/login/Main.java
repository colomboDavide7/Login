/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.UI.IAppUI;
import login.UI.UIFrame;
import login.controllers.UIController;
import login.repository.AppRepository;

/**
 *
 * @author davidecolombo
 */
public class Main {
    
    public static void main(String[] args){
        IAppUI frame = new UIFrame();
        
        IAppModel app = new Application(
                new AppManager(), new AppRepository()
        );
        
        UIController uiController = new UIController(app, frame);
    }
    
}
