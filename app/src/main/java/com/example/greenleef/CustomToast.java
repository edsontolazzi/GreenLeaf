package com.example.greenleef;

import android.content.Context;
import android.widget.Toast;

public class CustomToast {
    public static void showToast(String message, Context context) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
