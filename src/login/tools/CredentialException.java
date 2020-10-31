/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.awt.Color;

/**
 *
 * @author davidecolombo
 */
public class CredentialException extends Exception {
    
    public enum ErrorCode{
        USERNAME_ALREADY_USED, INVALID_PASSWORD, INVALID_USERNAME;
    }
    
    private ErrorCode errorCode;
    
    public CredentialException(ErrorCode code){
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
    public String getErrorMessage(){
        switch(this.errorCode){
            case INVALID_PASSWORD:
                return String.format("Invalid password: "
                        + "password must contains at least one lowercase letter, "
                        + "one uppercase letter, one number and one symbol");
            case INVALID_USERNAME:
                return String.format("Invalid username: "
                        + "username must not contains blank space and "
                        + "must have at least one lowercase letter and "
                        + "one uppercase letter");
            case USERNAME_ALREADY_USED:
                return String.format("Username already used");
            default:
                return String.format("ErrorCode \"%s\" not found", this.errorCode.name());
        }
    }
    
}
