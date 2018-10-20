package com.example.dell.calculator.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dell.calculator.R;


public class NumericFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    static int i=0;
    View rootView;
    boolean isPressed;
    Button btnNumber[];
    Button btnPlus, btnMinus, btnMultiply, btnDivide, btnClear, btnSign, btnParenthesis, btnEqualTo, btnDecimal;
    FragmentListener fragmentListener;
    Button btnBackspace;
    boolean buttonLongPressed;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_numaric, container, false);
        initUI();
        return rootView;
    }

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    void initUI() {
        isPressed=false;
        buttonLongPressed = false;
        btnNumber = new Button[10];
        btnNumber[0] = rootView.findViewById(R.id.button_0);
        btnNumber[1] = rootView.findViewById(R.id.button_1);
        btnNumber[2] = rootView.findViewById(R.id.button_2);
        btnNumber[3] = rootView.findViewById(R.id.button_3);
        btnNumber[4] = rootView.findViewById(R.id.button_4);
        btnNumber[5] = rootView.findViewById(R.id.button_5);
        btnNumber[6] = rootView.findViewById(R.id.button_6);
        btnNumber[7] = rootView.findViewById(R.id.button_7);
        btnNumber[8] = rootView.findViewById(R.id.button_8);
        btnNumber[9] = rootView.findViewById(R.id.button_9);

        btnPlus = rootView.findViewById(R.id.plus);
        btnMinus = rootView.findViewById(R.id.minus);
        btnMultiply = rootView.findViewById(R.id.multiply);
        btnDivide = rootView.findViewById(R.id.divide);
        btnClear = rootView.findViewById(R.id.clear);
        btnBackspace = rootView.findViewById(R.id.backspace);
        btnSign = rootView.findViewById(R.id.button_sign);
        btnParenthesis = rootView.findViewById(R.id.parenthesis);
        btnEqualTo = rootView.findViewById(R.id.equal_to);
        btnDecimal = rootView.findViewById(R.id.button_decimal);


        for (int i = 0; i < 10; i++)
            btnNumber[i].setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnSign.setOnClickListener(this);
        btnParenthesis.setOnClickListener(this);
        btnEqualTo.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);
        btnBackspace.setOnTouchListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (int i = 0; i < 10; i++)
            btnNumber[i] = null;

        btnPlus = null;
        btnMinus = null;
        btnMultiply = null;
        btnDivide = null;
        btnClear = null;
        btnBackspace = null;
        btnSign = null;
        btnParenthesis = null;
        btnEqualTo = null;
        btnDecimal = null;
    }

    @Override
    public void onClick(View v) {

        if (fragmentListener != null) {
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.button_0:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '0');
                    break;
                case R.id.button_1:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '1');
                    break;
                case R.id.button_2:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '2');
                    break;
                case R.id.button_3:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '3');
                    break;
                case R.id.button_4:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '4');
                    break;
                case R.id.button_5:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '5');
                    break;
                case R.id.button_6:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '6');
                    break;
                case R.id.button_7:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '7');
                    break;
                case R.id.button_8:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '8');
                    break;
                case R.id.button_9:
                    bundle.putChar(FragmentListener.NUMBER_CLICKED, '9');
                    break;

                case R.id.plus:
                    bundle.putChar(FragmentListener.OPERATION, '+');
                    break;
                case R.id.minus:
                    bundle.putChar(FragmentListener.OPERATION, '-');
                    break;
                case R.id.multiply:
                    bundle.putChar(FragmentListener.OPERATION, 'X');
                    break;
                case R.id.divide:
                    bundle.putChar(FragmentListener.OPERATION, '/');
                    break;

                case R.id.clear:
                    bundle.putString(FragmentListener.SPACIAL_OPERATION, "clear");
                    break;
                case R.id.backspace:
                    bundle.putString(FragmentListener.SPACIAL_OPERATION, "backspace");
                    break;
                case R.id.button_sign:
                    bundle.putString(FragmentListener.SPACIAL_OPERATION, "sign");
                    break;
                case R.id.equal_to:
                    bundle.putString(FragmentListener.SPACIAL_OPERATION, "equal");
                    break;
                case R.id.parenthesis:
                    bundle.putString(FragmentListener.SPACIAL_OPERATION, "parenthesis");
                    break;
                case R.id.button_decimal:
                    bundle.putString(FragmentListener.SPACIAL_OPERATION, "decimal");
                    break;
            }
            fragmentListener.onActionPerformed(bundle);
        }
    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            //remove one char
            removeChar();
            handler.postDelayed(this, 200);
        }
    };

    private void removeChar() {
        Bundle bundle=new Bundle();
        bundle.putString(FragmentListener.SPACIAL_OPERATION,"backspace");
        fragmentListener.onActionPerformed(bundle);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //remove on char
                btnBackspace.setBackgroundResource(R.drawable.backspace_button);
                removeChar();
                handler.postDelayed(mLongPressed, 1000);
                break;
            case MotionEvent.ACTION_UP:
                btnBackspace.setBackgroundResource(R.drawable.button_background);
                handler.removeCallbacks(mLongPressed);

                break;
        }
        return true;
    }
}