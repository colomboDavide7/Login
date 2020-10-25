    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.tools.CredentialException;
import login.tools.UserValidator;

/**
 *
 * @author davidecolombo
 */
public class Application {
    
    private String username = "";
    private String password = "";
    
    private final String FORCED = "stop";
    
    public static void main(String[] args) throws CredentialException {
        Application app = new Application();
    }
        
    public Application() throws CredentialException {
        
        printText("Choose a Username\n");
        
        // Apertura stream in lettura
        BufferedReader reader = null;
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            while((line = reader.readLine()) != null){
                if(line.isEmpty())
                    continue;
                
                // Usermname validation
                if(UserValidator.isValidUsername(line)){
                    this.username = line;
                    System.err.println("Valid username!");
                    System.out.println("Username = " + username);
                }
                
                // Closing the stream
                if(line.equals(FORCED)){
                    reader.close();
                    return;
                }
                
                
            }
                
                
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(reader != null)
                try {
                    reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    private void printText(String text){
        System.out.println(text);
    }
    
    
}
