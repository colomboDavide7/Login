/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

/**
 *
 * @author davidecolombo
 */
public class CustomerCreationException extends Exception {

    public enum ErrorCode{
        MISSING_MANDATORY;
    }
    
    private ErrorCode errorCode;
    
    public CustomerCreationException(ErrorCode code){
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
}
