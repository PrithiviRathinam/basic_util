package com.pritz.android.cloudadicmutiutility.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HP-PC on 04-06-2017.
 */


 public class FileSaver {

        private String directoryName = "cloudadic";
        private String fileName;
        private Context context;
        private boolean external;
        private String filePath;

        public FileSaver(Context context) {
            this.context = context;
        }

        public FileSaver setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FileSaver setExternal(boolean external) {
            this.external = external;
            return this;
        }

        public FileSaver setDirectoryName(String directoryName) {
            this.directoryName = directoryName;
            return this;
        }

        public void save(Bitmap bitmapImage) {
            FileOutputStream fileOutputStream = null;
            try {
                File file = createFile();
                filePath = file.getAbsolutePath();
                fileOutputStream = new FileOutputStream(file);
                Log.d("CAMERAPR","file saved");
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("CAMERAPR","file saved");
        }

        @NonNull
        private File createFile() {
            File directory;
            if(external){
                directory = getAlbumStorageDir(directoryName);
            }
            else {
                directory = context.getDir(directoryName, Context.MODE_PRIVATE);
            }

            directory = context.getExternalFilesDir(null);

            return new File(directory, fileName);
        }

        private File getAlbumStorageDir(String albumName) {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), albumName);
            if (!file.mkdirs()) {
                Log.e("FileSaver", "Directory not created");
            }
            return file;
        }

        public static boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        public static boolean isExternalStorageReadable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state) ||
                    Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
        }

        public Bitmap load() {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(createFile());
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    public String getFilePath() {
        return filePath;
    }
}

