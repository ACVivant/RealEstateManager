package com.openclassrooms.realestatemanager.utils;

/**
 * Format Url for using Geocoder and Static Lite Map Api
 */

public class MapUrl {
    private String centerInfos;

    public String createUrl(String number, String street, String zipcode, String town, String country, String myKey) {
        String base = "https://maps.googleapis.com/maps/api/staticmap?";
        String mapType = "&maptype=";
        String mapTypeInfos = "roadmap";
        String marker = "&markers=";
        String markerSize = "size:";
        String markerSizeInfos = "mid|";
        String markerColor = "color:";
        String markerColorInfos = "red|";
        String size = "&size=";
        String sizeInfos = "300x300";
        String language = "&language=";
        String lanquageInfos = "french";
        String key = "&key=";
        String keyInfos;

        keyInfos =  myKey;
        createAddressUrlField(number, street, zipcode, town, country);

        return base + size + sizeInfos + mapType + mapTypeInfos + marker + markerSize + markerSizeInfos + markerColor + markerColorInfos + centerInfos + language + lanquageInfos + key + keyInfos;
    }

    private void createAddressUrlField(String number, String street, String zipcode, String town, String country) {
        centerInfos = number + "," + extractSmallNameOfStreet(formatAddressFieldsForMap(street)) + ',' +zipcode + ',' + formatAddressFieldsForMap(town) + ',' + formatAddressFieldsForMap(country);
    }

    private String formatAddressFieldsForMap(String input) {
        String output= input.replace("-", "&");
        output = output.replace(" ", "&");
        return output;
    }

    private String extractSmallNameOfStreet(String input) {
        // We control that the las character is not &
        int inputLenght = input.length();
        if(input.charAt(inputLenght-1) =='&') {
            input = input.substring(0, inputLenght-1);
        }

        int index = input.lastIndexOf("&");
        return input.substring(index+1);
    }





    public String createGeocoderUrl(String number, String street, String zipcode, String town, String country) {
        return  number + "+" + street + ",+" + zipcode + "+" + town + ",+" +country;
    }
}
