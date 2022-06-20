/*
 * April 2022
 */
package org.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="pwdValidator")
public class PasswordValidator implements Validator{
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object obj) 
            throws ValidatorException {
        System.out.println("pwdValidator aufgerufen");
        String pwd = obj.toString();
        FacesMessage fm;
        
        if(pwd.length()>=5)
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
