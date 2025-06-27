/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author abdurraihan
 */
public class LanguageManager {

    private static Locale currentLocale = Locale.ENGLISH;
    private static ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);

    public static void setLanguage(String langCode) {
        switch (langCode) {
            case "id":
                currentLocale = new Locale("id");
                break;
            default:
                currentLocale = Locale.ENGLISH;
        }
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static String getCurrentLangCode() {
        return currentLocale.getLanguage();
    }

      public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
    }

    // ✅ Perbaikan: kembalikan locale aktif saat ini
    public static Locale getLocale() {
        return currentLocale;
    }
}
