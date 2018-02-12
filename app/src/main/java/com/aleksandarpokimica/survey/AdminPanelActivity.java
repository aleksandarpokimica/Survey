package com.aleksandarpokimica.survey;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

import static com.aleksandarpokimica.survey.MainActivity.allQuestions;

public class AdminPanelActivity extends AppCompatActivity {

    public static ArrayList<Question> arrayListQuestions;
    public AdminAdapter adminAdapter;
    public ListView listView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        listView1 = (ListView)findViewById(R.id.listView1);

        adminAdapter = new AdminAdapter(AdminPanelActivity.this, R.layout.admin_panel_row, allQuestions);
        listView1.setAdapter(adminAdapter);
    }
}
