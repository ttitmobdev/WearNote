package com.example.wearnote;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {

ListView listView;
    ArrayList<Note> notes;
    int rec = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
           if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO)){
               requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO},11);
           }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    displaySpeechScreen();
                } else {
                    Note note = (Note) parent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this,DeleteActivity.class);
                    intent.putExtra("id",note.getId());
                    startActivity(intent);
                }
            }
        });
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 11 & grantResults.length>0 ){
            Toast.makeText(this, "QWE", Toast.LENGTH_SHORT).show();
        }
    }

    public void displaySpeechScreen(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the title?");
        startActivityForResult(intent,rec);
    }
    public void updateUI(){
        notes = Helper.getAll(this);
        notes.add(0, new Note("",""));
        listView.setAdapter(new ListViewAdapter(this,0,notes));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == rec && resultCode == RESULT_OK){
            List<String > result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String message = result.get(0);
            Note note = new Note(message,null);
            Helper.saveNote(note,this);
            Helper.displayConf("Note saved",this);
            updateUI();
        }
    }
}
