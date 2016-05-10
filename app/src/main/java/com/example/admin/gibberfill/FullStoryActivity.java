package com.example.admin.gibberfill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullStoryActivity extends AppCompatActivity {

    BufferedReader reader;
    String line;
    Matcher m;
    String story = "";
    String temp;

    int i = 0;
    ArrayList<String> myList;
    TextView txtFullStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Gibbr Fill");

        txtFullStory = (TextView) findViewById(R.id.txtFullStory);
        myList = (ArrayList<String>) getIntent().getSerializableExtra("myWordsList");


        try {
            final InputStream file = getAssets().open("text.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            line = reader.readLine();
            while (line != null) {
                System.out.println("Line : " + line);
                temp = line;
                Pattern p = Pattern.compile("\\<(.*?)\\>");
                m = p.matcher(line);
                while (m.find()) {

                    temp = temp.replace(m.group(1), myList.get(i));
                    i++;
                }
                story = story + temp;
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        story = story.replaceAll("[<>]", "");
        txtFullStory.setText(story.toString());

    }



}
