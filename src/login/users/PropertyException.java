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
public class PropertyException extends Exception {

    public enum ErrorCode{
        NOT_FOUND;
    }
    
    private ErrorCode errorCode;
    
    public PropertyException(ErrorCode code){
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
}
