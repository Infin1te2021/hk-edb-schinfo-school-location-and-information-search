package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactInfo {
    public static String NAME = "name";
    public static String ID = "id";
    public static String ADDRESS = "address";
    public static String TELEPHONE = "telephone";
    public static String WEBSITE = "website";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";

    public static ArrayList<HashMap<String, String>> contactList = new ArrayList<>();

    // Creates and add contact to contact list
    public static void addContact(String name, String id, String address, String telephone,String website, String latitude, String longitude) {
        // Create contact
        HashMap<String, String> contact = new HashMap<>();
        contact.put(NAME, name);
        contact.put(ID, id);
        contact.put(ADDRESS, address);
        contact.put(TELEPHONE, telephone);
        contact.put(WEBSITE, website);
        contact.put(LATITUDE, latitude);
        contact.put(LONGITUDE, longitude);

        // Add contact to contact list
        contactList.add(contact);
    }

}
