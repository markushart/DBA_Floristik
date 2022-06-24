/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package converter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Name:            PasswordConverter
 * Aufgabe:         Umwandlung von Passwörtern in Hashwerte
 * Version:         2.0
 * Letzte Änderung: 24.06.2022
 * Realisierung     Markus Hartlage und Sascha Nickel
 */
@Named(value = "passwordConverter")
@RequestScoped
public class PasswordConverter implements Converter {

    private static final Logger logger
            = Logger.getLogger(PasswordConverter.class.getName());

    /**
     * Creates a new instance of PasswordConverter
     */
    public PasswordConverter() {
    }

    /**
     * Erzeugt für ein jeweiliges Passwort einen Hashwert
     *
     * @param pwd
     * @return
     */
    public String getPwdHash(String pwd) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] digest = md.digest(pwd.getBytes());
            hash = byArrayToHex(digest);
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return hash;
    }

    private String byArrayToHex(byte[] digest) {
        /*
        jedes Byte (-128...127) von digest wird in eine 2-stellige Hex-Zahl
        umgewandelt.
         */
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return getPwdHash(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        return t.toString();
    }

}
