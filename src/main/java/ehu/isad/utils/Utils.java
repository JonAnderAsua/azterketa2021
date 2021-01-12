package ehu.isad.utils;

import com.google.gson.Gson;
import ehu.isad.model.Orrialde;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import  java.security.MessageDigest;

public class Utils {

    public static Properties getProperties()  {
        Properties properties = null;

        try (InputStream in = Utils.class.getResourceAsStream("/setup.properties")) {
            properties = new Properties();
            properties.load(in);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static String readFromUrl(String url) throws IOException {
        URL api = new URL(url);
        URLConnection yc = api.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine = in.readLine(); // raw data
        in.close();
        return getMD5(inputLine);
    }
    private static String getMD5(String input) {
        try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] messageDigest = md.digest(input.getBytes());
         BigInteger number = new BigInteger(1, messageDigest);
         String hashtext = number.toString(16);

         while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
         }
         return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    private Orrialde readFromUrl(String value) throws IOException {
        // get JSON
        URL api = new URL("website.com?id="+value+"");
        URLConnection yc = api.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine = in.readLine(); // raw data
        in.close();
        // process if needed
        Gson gson = new Gson();
        return gson.fromJson(inputLine, Orrialde.class);
    }

     */
}