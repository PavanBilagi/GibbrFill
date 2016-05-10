package com.example.admin.gibberfill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillWordsActivity extends AppCompatActivity {

    BufferedReader reader;
    EditText edtWord;
    Button btnOk;
    TextView txtWordsLeft;
    String line;
    Matcher m;
    int i;

    ArrayList<String> words = new ArrayList<>();
    ArrayList<String> replacedWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_words);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Gibbr Fill");

        edtWord = (EditText) findViewById(R.id.edtWord);
        btnOk = (Button) findViewById(R.id.btnOk);
        txtWordsLeft =(TextView) findViewById(R.id.txtWordsLeft);

        try {
            final InputStream file = getAssets().open("text.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            line = reader.readLine();
            while (line != null) {
                System.out.println("Line : " + line);
                Pattern p = Pattern.compile("\\<(.*?)\\>");
                m = p.matcher(line);
                while (m.find()) {
                    words.add(m.group(1));
                }

                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        edtWord.setHint(words.get(0).toString());
        txtWordsLeft.setText((words.size())+" Word(s) left");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(edtWord.getText().toString().trim().equals(""))) {

                    txtWordsLeft.setText((words.size()- i-1)+" Word(s) left");

                    if (i < words.size()) {
                        int j = i;
                        replacedWords.add(edtWord.getText().toString().trim());
                        edtWord.setText("");
                        i++;
                        if (j + 1 < words.size()) {
                            edtWord.setHint(words.get(j + 1));
                        } else {
                            Intent i = new Intent(FillWordsActivity.this, FullStoryActivity.class);
                            i.putExtra("myWordsList", replacedWords);
                            startActivity(i);
                        }
                    }
                }else {
                    Toast.makeText(FillWordsActivity.this,"Please Enter Proper Word",Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
