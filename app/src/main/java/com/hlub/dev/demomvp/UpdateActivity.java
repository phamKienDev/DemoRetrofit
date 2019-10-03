package com.hlub.dev.demomvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtAge;
    private EditText edtSalary;
    private Button btnUpdate;
    private Toolbar toolbarUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        toolbarUpdate = findViewById(R.id.toolbar_update);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        setSupportActionBar(toolbarUpdate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cập nhật Employee");
    }
}
