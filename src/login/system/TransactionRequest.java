/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.system;

import java.time.LocalDateTime;
import java.util.List;
import login.tools.ParserScheme;
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class TransactionRequest {
    
    public enum TransactionType{
        SIGN_UP, LOGIN, LOGOUT;
    }
    
    public static TransactionRequest createRequestByType(IUser u, TransactionType t) {
        return new TransactionRequest(u, t);
    }
    
// ================================================================================
    private IUser u;
    private TransactionType t;
    private LocalDateTime requestDateTime;
    
    private TransactionRequest(IUser u, TransactionType t){
        this.u = u;
        this.t = t;
        this.requestDateTime = LocalDateTime.now();
    }
    
    public boolean matchType(TransactionType t){
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
    
//    public void addUserToList(List<IUser> list){
//        list.add(this.u);
//    }
    
    public String createUserRecord(){
        return this.u.createRecord();
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
