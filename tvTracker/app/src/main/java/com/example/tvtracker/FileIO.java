package com.example.tvtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;


public class FileIO {

    public static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("episodeList.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public static String readFromFile(Context context) {
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("episodeList.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public static String serialize(Serializable o) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static Object deserialize(String s) {
        Object o = null;
        try {
            byte[] data = Base64.getDecoder().decode(s);
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(data));
            o = ois.readObject();
            ois.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return o;
    }

    public static void serialize2(HashMap<Integer, Boolean> map, Context context) {
        try {
            FileOutputStream myFileOutStream
                    = context.openFileOutput("episodeList.txt", Context.MODE_PRIVATE);

            ObjectOutputStream myObjectOutStream
                    = new ObjectOutputStream(myFileOutStream);

            myObjectOutStream.writeObject(map);

            // closing FileOutputStream and
            // ObjectOutputStream
            myObjectOutStream.close();
            myFileOutStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static HashMap<Integer, Boolean> deserialize2(Context context) {
        HashMap<Integer, Boolean> result = null;

        try {
            FileInputStream fileInput = context.openFileInput("episodeList.txt");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            result = (HashMap) objectInput.readObject();

            objectInput.close();
            fileInput.close();
            return result;
        } catch (IOException obj1) {
            Log.e("Exception", "obj1 " + obj1.toString());
            return null;
        } catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
            Log.e("Exception", "obj2: " + obj2.toString());
            return null;
        }
    }

}
