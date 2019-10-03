package com.hlub.dev.demomvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hlub.dev.demomvp.retrofit.EmployeeRetrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtAge;
    private EditText edtSalary;
    private Button btnCreate;
    private Toolbar toolbarCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        toolbarCreate = findViewById(R.id.toolbar_create);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtSalary = (EditText) findViewById(R.id.edtSalary);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        setSupportActionBar(toolbarCreate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thêm Employee");

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEmployee();

            }
        });
    }

    private void createEmployee() {

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", edtName.getText().toString());
        jsonObject.addProperty("salary", edtSalary.getText().toString());
        jsonObject.addProperty("age", edtAge.getText().toString());

        if (edtName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Phải nhập tên nhân viên", Toast.LENGTH_SHORT).show();
        }
        Call<ResponseBody> createEmp = EmployeeRetrofit.getInstance().createEmployee(jsonObject);
        createEmp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(CreateActivity.this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();

                //Toast.makeText(CreateActivity.this, "Trùng lặp nhân viên: " + edtName.getText().toString(), Toast.LENGTH_SHORT).show();

                setViewEmpity();

                try {
                    Log.e("create", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreateActivity.this, "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
                Log.e("create", t.toString());
            }
        });
    }

    private void setViewEmpity() {
        edtSalary.setText("");
        edtName.setText("");
        edtAge.setText("");
    }


}
