package com.example.tippingcalculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private double Total = 0.0;

    private int NumPeople = 0;

    private double TipPercent = 0.0;

    private Double TipAmount = 0.0;

    private double PerPerson = 0.0;

    private EditText BillText;

    private EditText TipAmountText;

    private EditText TotalWithTipText;

    private EditText NumPeopleText;

    private EditText TotalPerPersonText;

    private RadioGroup TipButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BillText = findViewById(R.id.BillTotalWithTax);

        TipAmountText = findViewById(R.id.DisplayTipAmount);

        TotalWithTipText = findViewById(R.id.DisplayTotalWithTip);

        NumPeopleText = findViewById(R.id.NumPeople);

        TotalPerPersonText = findViewById(R.id.DisplayTotalPerPerson);

        TipButtons = findViewById(R.id.radioGroup);
    }

    //String.Format($"%.2F",value);

    public void GoOnClick(View v) {
        if(BillText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter the Bill amount with Tax", Toast.LENGTH_LONG).show();
            TipButtons.clearCheck();
            return;
        }
        else if(TipPercent == 0.0){
            Toast.makeText(this, "Please choose a tip percentage", Toast.LENGTH_LONG).show();
            TipButtons.clearCheck();
            return;
        }
        else if(NumPeopleText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter the number of people paying", Toast.LENGTH_LONG).show();
            TipButtons.clearCheck();
            return;
        }
        else if(BillText.getText().toString().contains(".") && BillText.getText().toString().split("\\.")[1].length() > 2){
            Toast.makeText(this, "Only enter an amount up to 2 decimal places", Toast.LENGTH_LONG).show();
            TipButtons.clearCheck();
            return;
        }
        else if(NumPeopleText.getText().toString().contains("\\.")){
            Toast.makeText(this, "Please enter a whole number", Toast.LENGTH_LONG).show();
            TipButtons.clearCheck();
            return;
        }

        Total = Double.parseDouble(BillText.getText().toString());

        NumPeople = Integer.parseInt(NumPeopleText.getText().toString());

        TipAmount = Total * TipPercent;

        Total += TipAmount;

        PerPerson = Total/NumPeople;

        TipAmountText.setText(String.format("$%.2f",TipAmount));

        TotalWithTipText.setText(String.format("$%.2f",Total));

        TotalPerPersonText.setText(String.format("$%.2f",PerPerson));
    }

    public void TipChosen(View v){

        if(v.getId() == R.id.radioPercent1 || v.getId() == R.id.radioPercent2 || v.getId() == R.id.radioPercent3 || v.getId() == R.id.radioPercent4){
            TipPercent = Double.parseDouble(((RadioButton) v).getText().toString().substring(0,2)) / 100;
        }
        else
        {
            Toast.makeText(this, "ERROR ID 1: Something went wrong when trying to grab the ID of the tip percent radio buttons", Toast.LENGTH_LONG).show();
        }
    }

    public void ClearTexts(View v){
        BillText.setText("");
        TipAmountText.setText("");
        TotalWithTipText.setText("");
        NumPeopleText.setText("");
        TotalPerPersonText.setText("");
        TipButtons.clearCheck();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("SaveBillWithTax", BillText.getText().toString());
        outState.putString("SaveTipAmount", TipAmountText.getText().toString());
        outState.putString("SaveTotalWithTip", TotalWithTipText.getText().toString());
        outState.putString("SaveNumPeople", NumPeopleText.getText().toString());
        outState.putString("SaveTotalPerPerson", TotalPerPersonText.getText().toString());
        outState.putInt("SaveTipChosen", TipButtons.getCheckedRadioButtonId());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        BillText.setText(savedInstanceState.getString("SaveBillWithTax"));
        TipAmountText.setText(savedInstanceState.getString("SaveTipAmount"));
        TotalWithTipText.setText(savedInstanceState.getString("SaveTotalWithTip"));
        NumPeopleText.setText(savedInstanceState.getString("SaveNumPeople"));
        TotalPerPersonText.setText(savedInstanceState.getString("SaveTotalPerPerson"));
        TipButtons.check(savedInstanceState.getInt("SaveTipChosen"));

    }
}