/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package converter;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author marku
 */
@Named(value = "euroConverter")
@RequestScoped
public class EuroConverter implements Converter {

    /**
     * Creates a new instance of EuroConverter
     */
    public EuroConverter() {
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        String s = o.toString();
        if (s == null) {
            s = "0,00";
        }
        s = s.replace(".", ",") + "€";
        return s;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String s) {
        float f = Float.valueOf(s);
        return (Object) f;
    }
}
