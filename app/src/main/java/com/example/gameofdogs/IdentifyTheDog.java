package com.example.gameofdogs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class IdentifyTheDog extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> dogImagesList;
    String randomDogOneName;
    String randomDogTwoName;
    String randomDogThreeName;
    int count = 10;
    TextView countdownView2;
    boolean CountdownStatus;
    ImageView dog1;
    ImageView dog2;
    ImageView dog3;
    TextView answer;
    TextView breedName;
    ArrayList<String> randomiseAnswer = new ArrayList<>();
    String correctBreedName;
    int num1;
    int num2;
    int num3;
    int correctNumber;

    private Button nextBtn;
    private String item1;
    private String item2;
    private String item3;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_dog);

        nextBtn = (Button) findViewById(R.id.next2);
        nextBtn.setOnClickListener(this);
        nextBtn.setEnabled(false);
        countdownView2 = (TextView) findViewById(R.id.countdown2);

        //assign images views to variables
        dog1 = findViewById(R.id.dog1);
        dog2 = findViewById(R.id.dog2);
        dog3 = findViewById(R.id.dog3);

        breedName = (TextView) findViewById(R.id.breedName);

        getDogs();
        Bundle bundle = getIntent().getExtras();
        CountdownStatus = bundle.getBoolean("Timerstatus");
        if (CountdownStatus == true) {
            counter();
            //start count
            countDownTimer.start();
        }

    }

    String s1 = "Submit";
    String s2 = "Next";

    public void onClick(View view) {

        getDogs();
        String clear = "";
        answer = (TextView) findViewById(R.id.answer2);
        //clear the text view
        answer.setText(clear);

        //ImageButtons enable
        dog1.setEnabled(true);
        dog2.setEnabled(true);
        dog3.setEnabled(true);
        nextBtn.setEnabled(false);
        if (CountdownStatus == true) {
            count = 10;
            counter();
            countDownTimer.start();
        }

    }

    public ArrayList<String> getDogs() {

        try {
            //get breed names
            String[] images = getAssets().list("dogs");
            dogImagesList = new ArrayList<String>(Arrays.asList(images));

            //Randomize the dog array list
            Random randImg = new Random();

            //get a random number
            num1 = randImg.nextInt(dogImagesList.size());
            item1 = dogImagesList.get(num1);
            randomDogOneName = item1.split("[_]")[0];
            //Verify the same dog image won't be repeated
            do {
                num2 = randImg.nextInt(dogImagesList.size());
                item2 = dogImagesList.get(num2);
                randomDogTwoName = item2.split("[_]")[0];
            } while (num2 == num1 || randomDogOneName.equals(randomDogTwoName));

            do {
                num3 = randImg.nextInt(dogImagesList.size());
                item3 = dogImagesList.get(num3);
                randomDogThreeName = item3.split("[_]")[0];
            } while ((num3 == num1) || (num3 == num2) || randomDogOneName.equals(randomDogThreeName) || randomDogTwoName.equals(randomDogThreeName));


            //Extract breed name
            randomDogOneName = item1.substring(0, item1.length() - 4);
            randomDogTwoName = item2.substring(0, item2.length() - 4);
            randomDogThreeName = item3.substring(0, item3.length() - 4);


            randomiseAnswer.add(randomDogOneName);
            randomiseAnswer.add(randomDogTwoName);
            randomiseAnswer.add(randomDogThreeName);

            int resId1 = getResources().getIdentifier(randomDogOneName, "drawable", "com.example.gameofdogs");
            int resId2 = getResources().getIdentifier(randomDogTwoName, "drawable", "com.example.gameofdogs");
            int resId3 = getResources().getIdentifier(randomDogThreeName, "drawable", "com.example.gameofdogs");
            System.out.println(item1);
            //set random images
            dog1.setImageResource(resId1);
            dog2.setImageResource(resId2);
            dog3.setImageResource(resId3);


            correctNumber = randImg.nextInt(randomiseAnswer.size());
            correctBreedName = randomiseAnswer.get(correctNumber).split("[_]")[0];

            //set selected breed name
            breedName.setText(correctBreedName);

            randomiseAnswer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void imageOneDog(View view) {
        //compare the image number
        int indexOneViews = 0;
        imageClicked();
        if (correctNumber == indexOneViews) {

            String out = "CORRECT!";
            answer = (TextView) findViewById(R.id.answer2);
            answer.setTextColor(Color.GREEN);
            answer.setText(out);

            dog1.setEnabled(false);
            dog2.setEnabled(false);
            dog3.setEnabled(false);
            nextBtn.setEnabled(true);

        } else {
            String out = "WRONG!";
            answer = (TextView) findViewById(R.id.answer2);
            answer.setTextColor(Color.RED);
            answer.setText(out);

            dog1.setEnabled(false);
            dog2.setEnabled(false);
            dog3.setEnabled(false);
            nextBtn.setEnabled(true);
        }
    }

    public void imageTwoDog(View view) {
        imageClicked();
        int indexOneViews = 1;
        if (correctNumber == indexOneViews) {

            String out = "CORRECT!";
            answer = (TextView) findViewById(R.id.answer2);
            answer.setTextColor(Color.GREEN);
            answer.setText(out);

            dog1.setEnabled(false);
            dog2.setEnabled(false);
            dog3.setEnabled(false);
            nextBtn.setEnabled(true);
        } else {

            String out = "WRONG!";
            answer = (TextView) findViewById(R.id.answer2);
            answer.setTextColor(Color.RED);
            answer.setText(out);

            dog1.setEnabled(false);
            dog2.setEnabled(false);
            dog3.setEnabled(false);
            nextBtn.setEnabled(true);
        }
    }

    public void imageThreeDog(View view) {
        imageClicked();
        int indexOneViews = 2;
        if (correctNumber == indexOneViews) {


            String c = "CORRECT!";
            answer = (TextView) findViewById(R.id.answer2);
            answer.setTextColor(Color.GREEN);
            answer.setText(c);

            dog1.setEnabled(false);
            dog2.setEnabled(false);
            dog3.setEnabled(false);
            nextBtn.setEnabled(true);
        } else {

            String w = "WRONG!";
            answer = (TextView) findViewById(R.id.answer2);
            answer.setTextColor(Color.RED);
            answer.setText(w);

            dog1.setEnabled(false);
            dog2.setEnabled(false);
            dog3.setEnabled(false);
            nextBtn.setEnabled(true);
        }
    }

    private void counter() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownView2.setText(String.valueOf(count));
                count--;
            }

            public void onFinish() {
                count = 10;
                dog1.performClick();
            }
        };

    }

    private void imageClicked() {
        if (countDownTimer != null && CountdownStatus == true) {
            countDownTimer.cancel();
        }

    }

}
