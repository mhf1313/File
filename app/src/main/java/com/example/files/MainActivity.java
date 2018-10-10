package com.example.files;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    Button btnReadfile , btnCreateFile;
    TextView tv;
    EditText inputFileName , inputFileContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWidget();

        File dir = getFilesDir();
        tv.setText(dir.getAbsolutePath());
    }

    private void setupWidget() {
        //btnCreateFile = findViewById(R.id.btn_create_file);
        //btnReadfile = findViewById(R.id.btn_read_file);
        tv = findViewById(R.id.tv);
        inputFileName = findViewById(R.id.edt_filename);
        inputFileContent = findViewById(R.id.edt_file_content);
    }

    public void onClick(View v){
        if (inputFileName.getText().toString().trim().isEmpty()){
            inputFileName.setError("Wrong File Name ...");
            return;
        }
        String filename = inputFileName.getText().toString().trim();
        if (v.getId() == R.id.btn_read_file){
            String content = readInternalFile(filename);
            inputFileContent.setText(content);
        }else if(v.getId() == R.id.btn_create_file){
            if (inputFileContent.getText().toString().isEmpty()){
                inputFileContent.setError("No Content");
                return;
            }
            String content = inputFileContent.getText().toString().trim();
            createInternalFile(filename , content.getBytes());
        }
    }

    private String readInternalFile(String filename) {
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) !=null){
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            Toast.makeText(this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private void createInternalFile(String filename, byte[] bytes) {
        try {
            FileOutputStream fos = openFileOutput(filename , MODE_PRIVATE);
            fos.write(bytes);
            fos.close();
            Toast.makeText(this, "File Created.", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}