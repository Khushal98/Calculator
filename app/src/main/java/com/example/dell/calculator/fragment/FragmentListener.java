package com.example.dell.calculator.fragment;

import android.os.Bundle;

public interface FragmentListener {

    String NUMBER_CLICKED = "NUMBER_CLICKED";
    String OPERATION = "OPERATION";
    String SPACIAL_OPERATION = "SPACIAL_OPERATION";
    String SCIENTIFIC_OPERATION = "SCIENTIFIC_OPERATION";

    void onActionPerformed(Bundle bundle);
}
