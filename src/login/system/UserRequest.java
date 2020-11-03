/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.system;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import login.tools.FileParser;
import login.tools.ParserScheme;
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class UserRequest {
    
    public enum RequestType{
        SIGN_UP, LOGIN, LOGOUT;
    }
    
    public static UserRequest createRequestByType(IUser u, RequestType t){
        return new UserRequest(u, t);
    }
    
// ================================================================================
    private IUser u;
    private RequestType t;
    private LocalDateTime requestDateTime;
    
    private UserRequest(IUser u, RequestType t){
        this.u = u;
        this.t = t;
        this.requestDateTime = LocalDateTime.now();
    }
    
    public boolean matchType(RequestType t){
        return this.t == t;
    }
    
    public String getUserProperty(UserProperty prop) {
        return this.u.getProperty(prop);
    }
    
    public boolean matchUser(IUser u){
        return this.u.equals(u);
    }
    
    public boolean matchUserProperty(UserProperty key, String value){
        return this.u.matchProperty(key, value);
    }
    
    public void addUserToList(List<IUser> list){
        list.add(this.u);
    }
    
    public void processSignupRequest(File file, boolean appendMode){
        FileParser.appendRecord(file, this.u.createRecord(), appendMode);
    }
    
    public String createTransaction(){
        StringBuilder sb = new StringBuilder();
        sb.append(UserProperty.USERNAME.name())
          .append(ParserScheme.VALID.getKeyValueSeparator())
          .append(u.getProperty(UserProperty.USERNAME))
          .append(ParserScheme.VALID.getPropertySeparator())
          .append(SystemProperty.TRANSACTION_DATE_TIME)
          .append(ParserScheme.VALID.getKeyValueSeparator())
          .append(this.requestDateTime)
          .append(ParserScheme.VALID.getPropertySeparator())
          .append(SystemProperty.TRANSACTION_TYPE)
          .append(ParserScheme.VALID.getKeyValueSeparator())
          .append(this.t)
          .append(ParserScheme.VALID.getPropertySeparator());
        return sb.toString();
    }

}
