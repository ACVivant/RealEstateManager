package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euros){
        return (int) Math.round(euros / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
//    public static String getTodayDate(){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/MM");
//        return dateFormat.format(new Date());
//    }

    public static String getTodayDate() {
        return getFormatDate(new Date());
    }

    public static String getFormatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String convertStringToDate(String textDate){
        if (textDate.length()==8) {
            String formattedDate = textDate.substring(6) + "/" + textDate.substring(4, 6) + "/" + textDate.substring(0, 4);
            return formattedDate;
        } else {
            return "N/A";
        }
    }

    public static int convertStringDateToIntDate(String textDate){
        String formattedDate = textDate.substring(6) + textDate.substring(3,4) + textDate.substring(0,1);
        int intDate = Integer.parseInt(formattedDate);
        return intDate;
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        //WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        //return wifi.isWifiEnabled();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
