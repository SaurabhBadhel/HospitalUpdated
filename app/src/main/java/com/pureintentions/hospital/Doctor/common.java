package com.pureintentions.hospital.Doctor;

import android.content.Context;
import android.os.Environment;

import com.pureintentions.hospital.R;

import java.io.File;

public class common {
    public static String getAppPath(Context context){
        File dir =new File(android.os.Environment.getExternalStorageDirectory()
                +File.separator
                +context.getResources().getString(R.string.app_name)
                +File.separator);
    if (!dir.exists())dir.mkdir();
    return dir.getPath()+File.separator;
    }

}
