package com.ng.jaguda;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Created by Aderemi.Okeowo on 5/19/2018.
 */
public class Utils {

    public static boolean isMusicExtension(String currentHref) {

        Pattern pattern = Pattern.compile(".*\\.(mp3|m4a|wma)$");
        return pattern.matcher(currentHref).matches();
    }

    public static boolean hasExtension(String currentHref) {
        Pattern pattern = Pattern.compile(".*\\.\\w{2,4}$");
        return pattern.matcher(currentHref).matches();
    }

    public static byte[] convertStreamToByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = stream.read(buffer)) > 0) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static String getProperSongDestinationName(String dirname,String name){
        String[] names = name.split("/");
        String lastName = names[names.length - 1];
        return String.format("/data/mp3/%s/%s",dirname,lastName);
    }

    public static boolean isExist(String filename){
        File file = new File(filename);
        return file.exists();
    }


}
