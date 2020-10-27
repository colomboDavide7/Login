/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author davidecolombo
 */
public interface IUserDate {
    
    DateTimeFormatter getFormatter();
    
    boolean isMyBirthDay(LocalDate date);
    
}
