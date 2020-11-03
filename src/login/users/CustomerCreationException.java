/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import java.util.List;
import login.system.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class CustomerCreationException extends Exception {

    public enum ErrorCode {
        MISSING_MANDATORY;
    }
    
    private ErrorCode errorCode;
    private List<UserProperty> missing;
    
    public CustomerCreationException(List<UserProperty> missing, ErrorCode code){
        this.errorCode = code;
        this.missing = missing;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
    public List<UserProperty> getMissingMandatoryList(){
        return this.missing;
    }
    
}
