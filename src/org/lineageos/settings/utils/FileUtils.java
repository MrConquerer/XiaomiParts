package org.lineageos.settings.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    public static boolean fileWritable(String filename) {
        return new File(filename).canWrite();
    }

    public static boolean fileReadable(String filename) {
        return new File(filename).canRead();
    }

    public static String readLine(String filename) {
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            line = reader.readLine();
        } catch (IOException e) {
            Log.e(TAG, "Could not read from file " + filename, e);
        } finally {
            try (reader) {
                // Do nothing
            } catch (IOException e) {
                Log.e(TAG, "Could not close the reader", e);
            }
        }
        return line;
    }

    public static boolean writeLine(String filename, String value) {
        BufferedWriter writer = null;
        boolean success = false;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            writer.write(value);
            success = true;
        } catch (IOException e) {
            Log.e(TAG, "Could not write to file " + filename, e);
        } finally {
            try (writer) {
                // Do nothing
            } catch (IOException e) {
                Log.e(TAG, "Could not close the writer", e);
            }
        }
        return success;
    }
}
