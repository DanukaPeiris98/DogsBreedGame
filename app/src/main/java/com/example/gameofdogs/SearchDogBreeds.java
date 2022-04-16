package com.example.gameofdogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchDogBreeds extends AppCompatActivity {

    ImageView selectedDog;
    TextView output;
    EditText input;
    ArrayList<String> imageList;
    AnimationDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog_breeds);

        output = (TextView) findViewById(R.id.selectedDisplay);
        input = (EditText) findViewById(R.id.givenBreed);
        Button submit = (Button) findViewById(R.id.submitL);
        selectedDog = (ImageView) findViewById(R.id.selectedDog);


    }

    //check breed is available
    private boolean checkBreed(String breed) {
        ArrayList<String> breedNames = getBreeds("dogs.json");
        if (breedNames.contains(breed)) {
            return true;
        } else {
            return false;
        }
    }

    //assign dog images to array list
    public ArrayList<String> getDogImages(String breed) {

        try {
            //get images names to array
            String[] images = getAssets().list("dogs");
            imageList = new ArrayList<String>(Arrays.asList(images));

            //get selected image name
            ArrayList<String> selectedImages = new ArrayList<String>();
            for (String s : imageList) {
                if (s.split("[_]")[0].equals(breed)) {
                    selectedImages.add(s);
                }
            }

            animation = new AnimationDrawable();
            for (String s : selectedImages
            ) {
                String img = s.substring(0, s.length() - 4);
                int resId = getResources().getIdentifier(img, "drawable", "com.example.gameofdogs");
                animation.addFrame(ContextCompat.getDrawable(this, resId), 5000);
            }

            selectedDog.setBackground(animation);
            animation.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onClickSearchDogBreed(View v) {
        boolean breedExists = checkBreed(input.getText().toString().toLowerCase());
        //check input breed exist or not
        if (breedExists == false) {
            output.setText("Breed Not Found");
            if (animation != null && animation.isRunning()) {
                animation.stop();
            }
        } else {
            output.setText(input.getText().toString().toLowerCase());
            getDogImages(input.getText().toString().toLowerCase());
        }
    }

    private ArrayList<String> getBreeds(String breedNames) {
        JSONArray jsonArray = null;
        ArrayList<String> bList = new ArrayList<String>();

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

        ArrayList<String> bListLower = new ArrayList<>();
        for (String str : bList) {
            bListLower.add(str.toLowerCase());
        }
        return bList;
    }

    //stop animation
    public void stopAnimation(View v) {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }
    }

}
