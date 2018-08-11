package com.example.dell.calculator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScientificFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Button btnFact, btnSqrt, btnPercent, btnSin, btnCos, btnTan, btnIn, btnLog, btnInverse,
            btnEx, btnSquare, btnPower, btnAbs, btnPi, btnExp;
    FragmentListener fragmentListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_scientific, container, false);
        init();
        return rootView;
    }


    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }


    void init() {

        btnFact = rootView.findViewById(R.id.fact);
        btnSqrt = rootView.findViewById(R.id.sqrt);
        btnPercent = rootView.findViewById(R.id.percent);
        btnSin = rootView.findViewById(R.id.sin);
        btnCos = rootView.findViewById(R.id.cos);
        btnTan = rootView.findViewById(R.id.tan);
        btnIn = rootView.findViewById(R.id.in);
        btnLog = rootView.findViewById(R.id.log);
        btnInverse = rootView.findViewById(R.id.inverse);
        btnEx = rootView.findViewById(R.id.ex);
        btnSquare = rootView.findViewById(R.id.square);
        btnPower = rootView.findViewById(R.id.power);
        btnAbs = rootView.findViewById(R.id.abs);
        btnPi = rootView.findViewById(R.id.pi);
        btnExp = rootView.findViewById(R.id.exp);

        btnFact.setOnClickListener(this);
        btnSqrt.setOnClickListener(this);
        btnPercent.setOnClickListener(this);
        btnSin.setOnClickListener(this);
        btnCos.setOnClickListener(this);
        btnTan.setOnClickListener(this);
        btnIn.setOnClickListener(this);
        btnLog.setOnClickListener(this);
        btnInverse.setOnClickListener(this);
        btnEx.setOnClickListener(this);
        btnSquare.setOnClickListener(this);
        btnPower.setOnClickListener(this);
        btnAbs.setOnClickListener(this);
        btnPi.setOnClickListener(this);
        btnExp.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        btnFact = null;
        btnSqrt = null;
        btnPercent = null;
        btnSin = null;
        btnCos = null;
        btnTan = null;
        btnIn = null;
        btnLog = null;
        btnInverse = null;
        btnEx = null;
        btnSquare = null;
        btnPower = null;
        btnAbs = null;
        btnPi = null;
        btnExp = null;
    }

    @Override
    public void onClick(View v) {

        if (fragmentListener != null) {
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.fact:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "fact");
                    break;
                case R.id.sqrt:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "sqrt");
                    break;
                case R.id.percent:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "percent");
                    break;
                case R.id.sin:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "sin");
                    break;
                case R.id.cos:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "cos");
                    break;
                case R.id.tan:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "tan");
                    break;
                case R.id.in:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "in");
                    break;
                case R.id.log:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "log");
                    break;
                case R.id.inverse:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "inverse");
                    break;
                case R.id.ex:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "ex");
                    break;
                case R.id.square:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "square");
                    break;
                case R.id.power:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "power");
                    break;
                case R.id.abs:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "abs");
                    break;
                case R.id.pi:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "pi");
                    break;
                case R.id.exp:
                    bundle.putString(FragmentListener.SCIENTIFIC_OPERATION, "exp");
                    break;
            }
            fragmentListener.onActionPerformed(bundle);
        }
    }
}