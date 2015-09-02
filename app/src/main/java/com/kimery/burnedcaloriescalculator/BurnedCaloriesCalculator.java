package com.kimery.burnedcaloriescalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DecimalFormat;

public class BurnedCaloriesCalculator extends AppCompatActivity {

    private EditText weightEditText;
    private TextView milesRanView;
    private SeekBar milesRanProgress;
    private TextView caloriesBurnedView;
    private Spinner feet;
    private Spinner inches;
    private TextView bmiView;
    private EditText nameEditText;
    private int progress;
    private String weightAmountString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burned_calories_calculator);

        //Get references
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        milesRanView = (TextView) findViewById(R.id.mileRanView);
        milesRanProgress = (SeekBar) findViewById(R.id.milesRanProgress);
        caloriesBurnedView = (TextView) findViewById(R.id.caloriesBurnedView);
        feet = (Spinner) findViewById(R.id.feet);
        inches = (Spinner) findViewById(R.id.inches);
        bmiView = (TextView) findViewById(R.id.bmiView);
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        progress = milesRanProgress.getProgress();

        //Get and set spinner arrays
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.feet_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        feet.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.inches_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        inches.setAdapter(adapter2);


        weightEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    calculateCaloriesBurned();
                }
                return false;
            }
        });


        milesRanProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                milesRanView.setText(progress + "mi");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calculateCaloriesBurned();
            }
        });
    }

    public void calculateCaloriesBurned() {
        double weight;
        int milesRan;
        double bmi;
        int feetNum;
        int inchesNum;
        double caloriesBurned;
        String feetStr;
        String inchStr;

        weightAmountString = weightEditText.getText().toString();
        if (weightAmountString.equals("")) {
            weight = 0;
            feetStr = feet.getSelectedItem().toString();
            feetNum = Integer.parseInt(feetStr);
            inchStr = inches.getSelectedItem().toString();
            inchesNum = Integer.parseInt(inchStr);
            milesRan = milesRanProgress.getProgress();
        }
        else{
            weight = Double.parseDouble(weightAmountString);
            feetStr = feet.getSelectedItem().toString();
            feetNum = Integer.parseInt(feetStr);
            inchStr = inches.getSelectedItem().toString();
            inchesNum = Integer.parseInt(inchStr);
            milesRan = milesRanProgress.getProgress();
        }
        caloriesBurned = 0.75 * weight * milesRan;

        bmi = (weight * 703)/((12*feetNum+inchesNum)*(12*feetNum + inchesNum));

        DecimalFormat dec = new DecimalFormat("#0.00");
        caloriesBurnedView.setText(dec.format(caloriesBurned));
        bmiView.setText(dec.format(bmi));
    }
}
