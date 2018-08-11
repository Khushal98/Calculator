package com.example.dell.calculator;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class HistoryActivity extends Activity implements View.OnClickListener {
    String[] equation;
    ListView listView;
    DatabaseHistory databaseHistory;
    Button btnClearHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView=findViewById(R.id.history_list);
        btnClearHistory=findViewById(R.id.clear_history);
        databaseHistory=new DatabaseHistory(this);
        btnClearHistory.setOnClickListener(this);
        setList();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in,R.anim.move_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setList() {
        equation=getHistory();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.activity_listview,equation);
        listView.setAdapter(adapter);
    }

    String[] getHistory() {
        Cursor myCursor = databaseHistory.getAllData();
        String[] hist = new String[myCursor.getCount()];
        if (myCursor.getCount() == 0) {
            return new String[]{"THERE IS NOT ANY HISTORY"};
        }
        int i = 0;
        while (myCursor.moveToNext()) {
            hist[i++] =myCursor.getString(1) +""+ myCursor.getString(2);
        }
        return hist;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clear_history:
                databaseHistory.deleteAllData();
                setList();
                break;
        }
    }
}
