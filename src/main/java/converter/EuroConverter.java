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
 * Name:            EuroConverter
 * Aufgabe:         Umwandlung von Doublewerten in "Preis"-Darstellung
 * Version:         2.0
 * Letzte Änderung: 24.06.2022
 * Realisierung     Markus Hartlage und Sascha Nickel
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
        float x = (float) o;
        String s = String.valueOf(x);
        if (s == null) {
            s = "0,00€";
        } else {
            s = String.format("%.2f€", x).replace(".", ",");
        }
        return s;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String s) {
        float f = Float.valueOf(s);
        return (Object) f;
    }
}
