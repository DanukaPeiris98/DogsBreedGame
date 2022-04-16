package com.example.gameofdogs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class IdentifyTheBreed extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> bList;
    ArrayList<String> breedNameList;
    String ranDogName;
    CountDownTimer countDownTimer;
    ImageView dogImage;
    TextView answer;
    int count = 10;
    TextView correctAnswerName;
    TextView countdownView;

    private Spinner spinner;
    private Button btn1;
    private boolean countdownStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_breed);

        spinner = (Spinner) findViewById(R.id.spinner);
        //get breed names
        ArrayList<String> breedNames = getBreeds("dogs.json");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, breedNames);
        spinner.setAdapter(adapter);
        countdownView = (TextView) findViewById(R.id.countdown);
        dogImage = findViewById(R.id.imageView);

        btn1 = findViewById(R.id.ok1);
        btn1.setOnClickListener(this);
        btn1.setText("Submit");

        getDogs();
        Bundle bundle = getIntent().getExtras();
        countdownStatus = bundle.getBoolean("Timerstatus");
        //check switch on or off
        if (countdownStatus == true) {
            counter();
            //count start
            countDownTimer.start();
        }
    }

    String s = "Submit";
    String n = "Next";

    public void onClick(View view) {


        if (s.equals(btn1.getText())) {
            try {
                getSpinnerValue(dogImage);
                btn1.setText("Next");

                if (countdownStatus == true) {
                    countDownTimer.cancel();
                    count = 10;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            btn1.setText("Submit");

            getDogs();

            if (countdownStatus == true) {
                countDownTimer.start();
            }

            String clear = "";
            answer = (TextView) findViewById(R.id.answer1);
            //clear the text
            answer.setText(clear);

            correctAnswerName = (TextView) findViewById(R.id.correctDogBreed);
            correctAnswerName.setText("");
        }
    }

    //get the breed name list from json
    public ArrayList<String> getBreeds(String breedNames) {
        JSONArray jsonArray = null;
        bList = new ArrayList<>();

        try {
            InputStream is = getResources().getAssets().open(breedNames);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    bList.add(jsonArray.getJSONObject(i).getString("breed"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return bList;
    }

    //get the breed name list
    public ArrayList<String> getDogs() {

        try {
            //get breed image names to array
            String[] images = getAssets().list("dogs");
            breedNameList = new ArrayList<String>(Arrays.asList(images));

            //create a random generator
            Random randImg = new Random();
            //get random number within the size of array
            int num = randImg.nextInt(breedNameList.size());
            //get random image name
            String item = breedNameList.get(num);

            //extract breed names
            ranDogName = item.substring(0, item.length() - 4);
            //get resource id
            int resId = getResources().getIdentifier(ranDogName, "drawable", "com.example.gameofdogs");
            dogImage.setImageResource(resId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getSpinnerValue(View v) throws JSONException {
        //get image name
        String correctBreeds = ranDogName.split("[_]")[0];
        String text = spinner.getSelectedItem().toString();

        if (bList.contains(text)) {


            if (text.equals(correctBreeds)) {

                String c = "CORRECT!";
                answer = (TextView) findViewById(R.id.answer1);
                answer.setTextColor(Color.GREEN);
                answer.setText(c);

                correctAnswerName = (TextView) findViewById(R.id.correctDogBreed);
                correctAnswerName.setText(correctBreeds);


            } else {
                String w = "WRONG!";
                answer = (TextView) findViewById(R.id.answer1);
                answer.setText(w);
                answer.setTextColor(Color.RED);

                correctAnswerName = (TextView) findViewById(R.id.correctDogBreed);
                correctAnswerName.setText(correctBreeds);
            }


        } else {
            System.out.println(text + " not in the list");
        }

    }

    private void counter() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownView.setText(String.valueOf(count));
                count--;
            }

            public void onFinish() {
                //click button
                btn1.performClick();
                countDownTimer.cancel();
                count = 10;
            }
        };

    }

}
