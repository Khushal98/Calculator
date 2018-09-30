package com.example.dell.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConverterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> list_of_unit=new ArrayList<>();
    Spinner unitSpr, firstUnitSpr,secondUnitSpr;
    public EditText firstUnitEt, secondUnitEt;
    ArrayAdapter<String>  adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Converter");

        unitSpr=findViewById(R.id.unit);
        firstUnitSpr =findViewById(R.id.from_unit);
        secondUnitSpr =findViewById(R.id.to_unit);
        firstUnitEt =findViewById(R.id.text_unit);
        secondUnitEt =findViewById(R.id.text_to_unit);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),
                 R.layout.dropdown_list,getResources().getStringArray(R.array.list_unit));
        adapter.setDropDownViewResource(R.layout.simple_layout);
        adapter1=new ArrayAdapter<>(getApplicationContext(),
                R.layout.unit_textlayout,list_of_unit);
        adapter1.setDropDownViewResource(R.layout.simple_layout);
        unitSpr.setAdapter(adapter);
        firstUnitSpr.setAdapter(adapter1);
        secondUnitSpr.setAdapter(adapter1);
        unitSpr.setOnItemSelectedListener(this);
        firstUnitSpr.setOnItemSelectedListener(this);
        secondUnitSpr.setOnItemSelectedListener(this);
        firstUnitEt.addTextChangedListener(firstTextWatcher);
        secondUnitEt.addTextChangedListener(secondTextWatcher);
    }

    TextWatcher firstTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            firstUnitEt.removeTextChangedListener(firstTextWatcher);
            if(firstUnitEt.getText().toString().contains("E")){
                firstUnitEt.setText("");
            }
            firstUnitEt.addTextChangedListener(firstTextWatcher);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            secondUnitEt.removeTextChangedListener(secondTextWatcher);
            if(firstUnitEt.getText().toString().length()>0){
                convert(unitSpr.getSelectedItemPosition(),0);
            }
            else{
                secondUnitEt.setText("");
            }
            secondUnitEt.addTextChangedListener(secondTextWatcher);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher secondTextWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            secondUnitEt.removeTextChangedListener(secondTextWatcher);
            if(secondUnitEt.getText().toString().contains("E")){
                secondUnitEt.setText("");
            }
            secondUnitEt.addTextChangedListener(secondTextWatcher);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            firstUnitEt.removeTextChangedListener(firstTextWatcher);
            if(secondUnitEt.getText().toString().length()>0)
                convert(unitSpr.getSelectedItemPosition(),1);
            else{
                firstUnitEt.setText("");
            }
            firstUnitEt.addTextChangedListener(firstTextWatcher);
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.move_right_in,R.anim.move_right_out);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int unitPos=unitSpr.getSelectedItemPosition();
        switch (parent.getId()) {
            case R.id.unit:
                firstUnitEt.setText("");
                secondUnitEt.setText("");
                firstUnitSpr.setSelection(0);
                secondUnitSpr.setSelection(0);
                adapter1.clear();
                switch (position) {
                    case 0:
                        adapter1.add("Kilometer");
                        adapter1.add("Meter");
                        adapter1.add("Centimeter");
                        adapter1.add("Millimeter");
                        adapter1.add("Micrometer");
                        break;
                    case 1:
                        adapter1.add("Celsius");
                        adapter1.add("Fahrenheit");
                        break;
                    case 2:
                        adapter1.add("Kilogram");
                        adapter1.add("Gram");
                        adapter1.add("MilliGram");
                        adapter1.add("Tonne");
                        adapter1.add("Quintal");
                        break;
                    case 3:
                        adapter1.add("Meter"+"\n"+"cube");
                        adapter1.add("Centimeter"+"\n"+"cube");
                        adapter1.add("Liter");
                        adapter1.add("Milliliter");
                        break;
                    case 4:
                        adapter1.add("meter"+"\n"+"square");
                        adapter1.add("centimeter"+"\n"+"square");
                        adapter1.add("acres");
                        adapter1.add("hectares");
                        break;
                }
                adapter1.notifyDataSetChanged();
                break;
            case R.id.from_unit:
                secondUnitEt.removeTextChangedListener(secondTextWatcher);
                if(firstUnitEt.getText().toString().length()>0)
                    convert(unitPos,0);
                secondUnitEt.addTextChangedListener(secondTextWatcher);
                break;
            case R.id.to_unit:
                firstUnitEt.removeTextChangedListener(firstTextWatcher);
                if(firstUnitEt.getText().toString().length()>0)
                    convert(unitPos,1);
                firstUnitEt.addTextChangedListener(firstTextWatcher);
                break;
        }
    }

    public void convert(int unitPos,int id) {
        int fromUnitPos ,toUnitPos;
        double value;
        try {
            switch (id) {
                case 0:
                    value = Double.parseDouble(firstUnitEt.getText().toString());
                    fromUnitPos = firstUnitSpr.getSelectedItemPosition();
                    toUnitPos = secondUnitSpr.getSelectedItemPosition();
                    secondUnitEt.setText(getResult(unitPos, fromUnitPos, toUnitPos, value));
                    break;
                case 1:
                    value = Double.parseDouble(secondUnitEt.getText().toString());
                    fromUnitPos = secondUnitSpr.getSelectedItemPosition();
                    toUnitPos = firstUnitSpr.getSelectedItemPosition();
                    firstUnitEt.setText(getResult(unitPos, fromUnitPos, toUnitPos, value));
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    double getSiUnit(int u,int un){
        switch(u){
            case 0:
                switch(un){
                    case 0:
                        return Math.pow(10,5);
                    case 1:
                        return 100.0;
                    case 2:
                        return 1;
                    case 3:
                        return 1/10.0;
                    case 4:
                        return Math.pow(10,-4);
                }
                break;
            case 2:
                switch(un){
                    case 0:
                        return 1.0;
                    case 1:
                        return 1/1000.0;
                    case 2:
                        return Math.pow(10,-6);
                    case 3:
                        return 1000.0;
                    case 4:
                        return 100.0;
                }
                break;
            case 3:
                switch(un){
                    case 0:
                        return 1.0;
                    case 1:
                        return Math.pow(10,-6);
                    case 2:
                        return 1000.0;
                    case 3:
                        return Math.pow(10,-6);
                }
                break;
            case 4:
                switch(un){
                    case 0:
                        return 1;
                    case 1:
                        return Math.pow(10,-4);
                    case 2:
                        return 4046.8564224;
                    case 3:
                        return Math.pow(10,4);
                }
                break;
        }
        return -1;
    }

    public String getResult(int unitPos, int fromUnitPos, int toUnitPos, double value) {
        double res;
        if(unitPos==1){
            if(fromUnitPos==toUnitPos){
                res=value;
            }
            else if(fromUnitPos==1){
                res =value*9/5+32;
            }
            else{
                res =(value-32)*5/9;
            }
        }
        else {
            double unitValue=  getSiUnit(unitPos, fromUnitPos) / getSiUnit(unitPos, toUnitPos);
            res = value * unitValue ;
        }
        DecimalFormat format=new DecimalFormat("0.##########");
        BigDecimal bigDecimal = new BigDecimal(res);
        if(Double.toString(res).contains("E")) {
            format = new DecimalFormat("0.#####E0");
         }
        return format.format(bigDecimal);
    }
}