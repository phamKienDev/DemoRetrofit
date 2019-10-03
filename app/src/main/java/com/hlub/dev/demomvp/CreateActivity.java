package com.hlub.dev.demomvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hlub.dev.demomvp.entity.Employee;
import com.hlub.dev.demomvp.entity.Model;
import com.hlub.dev.demomvp.retrofit.EmployeeRetrofit;
import com.hlub.dev.demomvp.retrofit.EmployeeService;

import java.io.IOException;
import java.util.List;

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

                Call<Model> createEmp = EmployeeRetrofit.getInstance().createEmployee(getEmployeeFromView());

                createEmp.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Toast.makeText(CreateActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Toast.makeText(CreateActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        Log.e("create",t.toString());
                    }
                });

            }
        });
    }

    private Model getEmployeeFromView() {
        Model model = new Model();
        model.setId(String.valueOf(System.currentTimeMillis()));
//        nv.setEmployeeName(edtName.getText().toString());
//        nv.setEmployeeSalary(String.valueOf(edtSalary.getText().toString()));
//        nv.setEmployeeAge(String.valueOf(edtAge.getText().toString()));
        model.setEmployeeName("1234");
        model.setEmployeeSalary("1234");
        model.setEmployeeAge("1234");
        model.setProfileImage("1234");

        return model;
    }

    private void setViewEmpity() {
        edtSalary.setText("");
        edtName.setText("");
        edtAge.setText("");
    }


}
