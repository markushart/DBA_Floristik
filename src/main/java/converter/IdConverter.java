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
 * Name:            IdConverter
 * Aufgabe:         Umwandlung von Usernamen in "ID"-Darstellung
 * Version:         2.0
 * Letzte Änderung: 24.06.2022
 * Realisierung     Markus Hartlage und Sascha Nickel
 */
@Named(value = "idConverter")
@RequestScoped
public class IdConverter implements Converter {

    /**
     * Creates a new instance of EuroConverter
     */
    public IdConverter() {
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        int x = (int) o;
        String s = String.valueOf(x);
        return s;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String s) {
        int i = Integer.valueOf(s);
        return (Object) i;
    }
}