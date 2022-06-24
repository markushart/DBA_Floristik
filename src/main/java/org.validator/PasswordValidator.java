/**
 * Name:            PasswordValidator
 * Aufgabe:         Validator für Passwörter beim Registrieren
 * Version:         2.0
 * Letzte Änderung: 24.06.2022
 * Realisierung     Markus Hartlage und Sascha Nickel
 */
package org.validator;

import static java.util.regex.Pattern.matches;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="pwdValidator")
public class PasswordValidator implements Validator{
    
    @Override
    /**
     * Validiert die Passwortlänge
     *
     * 
     */
    public void validate(FacesContext fc, UIComponent uic, Object obj) 
            throws ValidatorException {
        String pwd = obj.toString();
        FacesMessage fm;
        
        String regexpwd = "^[A-Za-z0-9ÄÖÜäöü/!/§/$/%/&/(/)=/?]{5,}$";
        boolean matches = matches(regexpwd,pwd);
        
        if (!matches )
        //if(pwd.length()>=5)
        {
           fm = new FacesMessage("Passwort formal korrekt!");
           FacesContext.getCurrentInstance().addMessage(uic.getClientId(), fm);
        }
        else
        {
            fm = new FacesMessage("Hinweis: min. 5 Zeichen!");
            throw new ValidatorException(fm);
        }
        
    }

}
