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
import org.apache.commons.codec.binary.Hex;
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
    public String readFromUrl(String s) throws IOException, NoSuchAlgorithmException {
        URL url = new URL(s);
        InputStream is = url.openStream();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String digest = getDigest(is, md, 2048);
        return digest;

    }

    public static String getDigest(InputStream is, MessageDigest md, int byteArraySize)
            throws NoSuchAlgorithmException, IOException {

        md.reset();
        byte[] bytes = new byte[byteArraySize];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            md.update(bytes, 0, numBytes);
        }
        byte[] digest = md.digest();
        String result = new String(Hex.encodeHex(digest));
        return result;
    }

/*
    public  String readFromUrl(String url)  {
        String emaitza = "";
        try {
            URL api = new URL(url);
            URLConnection yc = api.openConnection();
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine = in.readLine(); // raw data
            in.close();
            emaitza =  getMD5(inputLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emaitza;
    }

    private String getMD5(String input) {
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

     */
}