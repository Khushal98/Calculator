package com.example.dell.calculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentListener, View.OnClickListener, GestureDetector.OnGestureListener {
    GestureDetectorCompat detector;
    Button btnShowHistory, btnCopy, btnConverter;
    TextView textView;
    Equation equation;
    FragmentManager fm;
    FragmentTransaction ft;
    DatabaseHistory databaseHistory;
    int index = 0;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        frameLayout =findViewById(R.id.number_container);
        btnShowHistory = findViewById(R.id.history);
        btnCopy = findViewById(R.id.copy);
        btnConverter = findViewById(R.id.converter);
        databaseHistory = new DatabaseHistory(getApplicationContext());
        index = databaseHistory.getMaxIndex();
        textView = findViewById(R.id.text);
        fm = getSupportFragmentManager();
        btnShowHistory.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnConverter.setOnClickListener(this);

        if (findViewById(R.id.activity_main_portrait) != null) {
            addNumber();
        } else if (findViewById(R.id.activity_main_landscape) != null) {
            addNumber();
            addScientific();
        }
        equation = new Equation();
        detector =new GestureDetectorCompat(getApplicationContext(),this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textView = null;
    }

    void addNumber() {

        ft = fm.beginTransaction();
        NumericFragment fragment = new NumericFragment();
        fragment.setFragmentListener(this);
        ft.add(R.id.number_container, fragment);
        ft.commit();

    }

    void addScientific() {

        ft = fm.beginTransaction();
        ScientificFragment fragment = new ScientificFragment();
        fragment.setFragmentListener(this);
        ft.add(R.id.scientific_container, fragment);
        ft.commit();

    }

    @Override
    public void onActionPerformed(Bundle bundle) {

        char number;
        char op;
        String spacialOperator;
        String scientificOperator;
        int length;
        boolean appendable;
        String text = textView.getText().toString(), newText = textView.getText().toString();
        appendable = !text.contains("=");


        if (text.length() < 100) {
            number = bundle.getChar(FragmentListener.NUMBER_CLICKED, 'N');   //get the object from bundle
            op = bundle.getChar(FragmentListener.OPERATION, 'N');
            spacialOperator = bundle.getString(FragmentListener.SPACIAL_OPERATION, "NOT");
            scientificOperator = bundle.getString(FragmentListener.SCIENTIFIC_OPERATION, "NOT");


            if (!appendable && spacialOperator.equals("equal")) {
                return;
            } else if (!appendable) {
                if (equation.isCharChar(text.charAt(text.length() - 1)) || number != 'N') {
                    newText = "";
                } else {
                    newText = text.replace("=", "");
                    textView.setText(newText);
                }
            }


            if (number != 'N') {
                if (!equation.numberAppend(number, newText).equals("error"))
                    textView.setText(equation.numberAppend(number, newText));
                else {
                    Toast.makeText(getApplicationContext(),
                            "You can not enter more then 15 digits", Toast.LENGTH_SHORT).show();
                }
            } else if (op != 'N') {
                textView.setText(equation.operationAppend(op, newText));
            } else if (!spacialOperator.equals("NOT")) {
               if(spacialOperator.equals("converter")){
                   Intent intent = new Intent(getApplicationContext(), ConverterActivity.class);
                   startActivity(intent);
                   overridePendingTransition(R.anim.left_in, R.anim.left_out);
               }
                textView.setText(equation.spacialOperatorAppend(spacialOperator, newText));
            } else if (!scientificOperator.equals("NOT")) {
                if (equation.scientificOperatorAppend(scientificOperator, newText).contains("error")) {
                    Toast.makeText(getApplicationContext(), equation.scientificOperatorAppend
                            (scientificOperator, newText), Toast.LENGTH_SHORT).show();
                } else
                    textView.setText(equation.scientificOperatorAppend(scientificOperator, newText));
            }
        } else {
            spacialOperator = bundle.getString(FragmentListener.SPACIAL_OPERATION, "NOT");

            if (spacialOperator.equals("backspace") || spacialOperator.equals("clear") || spacialOperator.equals("equal")) {
                textView.setText(equation.spacialOperatorAppend(spacialOperator, newText));
            } else {
                Toast.makeText(getApplicationContext(), "The length of equation more then 100", Toast.LENGTH_SHORT).show();
            }
        }
        length = textView.getText().toString().length();

        newText = textView.getText().toString();
        if (newText.contains("=") && !equation.isCharChar(newText.charAt(length - 1))) {
            index = databaseHistory.getMaxIndex() + 1;
            if (index == 11) {
                databaseHistory.shift();
                databaseHistory.updateData(Integer.toString(10), text, newText);
            } else {
                databaseHistory.insertData(Integer.toString(index), text, newText);
            }
        }
        if (length <= 15) {
            textView.setTextSize(40);
        } else if (length <= 20) {
            textView.setTextSize(30);
        } else {
            textView.setTextSize(25);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.history:
                intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.move_in, R.anim.fade_out);
                break;
            case R.id.copy:
                String copyText = textView.getText().toString();
                if (copyText.contains("=")) {
                    copyText = copyText.replace("=", "");
                }
                if (!copyText.equals("")) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("equation", copyText);
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                    }
                }
                break;
            case R.id.converter:
                intent = new Intent(getApplicationContext(), ConverterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Intent intent = new Intent(getApplicationContext(), ConverterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        return true;
    }
}