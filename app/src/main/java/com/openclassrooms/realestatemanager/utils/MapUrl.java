package com.openclassrooms.realestatemanager.utils;

import android.content.Context;

/**
 * Created by Anne-Charlotte Vivant on 09/05/2019.
 */

public class MapUrl {
    private String base = "https://maps.googleapis.com/maps/api/staticmap?";
    private String mapType = "&maptype=";
    private String mapTypeInfos = "roadmap";
    private String marker = "&markers=";
    private String markerSize = "size:";
    private String markerSizeInfos = "mid|";
    private String markerColor = "color:";
    private String markerColorInfos = "red|";
    private String centerInfos;
    private String size = "&size=";
    private String sizeInfos = "300x300";
    private String language = "&language=";
    private String lanquageInfos = "french";
    private String key = "&key=";
    private String keyInfos;
    Context mContext;

public String formatAddressFieldsForMap(String input) {
    String output= input.replace("-", "&");
    output = output.replace(" ", "&");
    return output;
}

public String extractSmallNameOfStreet(String input) { // Sinon GoogleMap est vraiment très fantaisiste
    int index = input.lastIndexOf("&");
    String output = input.substring(index+1);
    return  output;
}

public void createAddressUrlField(String number, String street, String zipcode, String town, String country) {
    String allAdress = number + "," + extractSmallNameOfStreet(formatAddressFieldsForMap(street)) + ',' +zipcode + ',' + formatAddressFieldsForMap(town) + ',' + formatAddressFieldsForMap(country);
    centerInfos = allAdress;
}

public String createUrl(String number, String street, String zipcode, String town, String country, String myKey) {
    keyInfos =  myKey;
    createAddressUrlField(number, street, zipcode, town, country);
    String url = base + size + sizeInfos + mapType + mapTypeInfos + marker + markerSize + markerSizeInfos + markerColor + markerColorInfos + centerInfos + language + lanquageInfos + key + keyInfos;
    return url;
}
}
