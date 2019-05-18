package com.example.wearnote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.wear.activity.ConfirmationActivity;

import java.util.ArrayList;
import java.util.Map;

public class Helper {
    public static String saveNote(Note note, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        String id  = String.valueOf(System.currentTimeMillis());

        editor.putString(id, note.getTitle());
        editor.commit();

        return id;
    }

    public static ArrayList<Note> getAll(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<Note> notes = new ArrayList<>();
        Map<String ,?> key = preferences.getAll();
        for(Map.Entry<String ,?> entry : key.entrySet()){
            String savedData = (String)entry.getValue();
            if (savedData!=null){
                Note note = new Note(savedData,entry.getKey());
                notes.add(note);
            }
        }
        return notes;

    }

    public static void removeNote(String id,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(id);
        editor.commit();
    }
    public static void displayConf(String message, Context context){
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,message);
        context.startActivity(intent);
    }
}
