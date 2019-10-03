package com.hlub.dev.demomvp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hlub.dev.demomvp.adapter.EmployeeAdapter;
import com.hlub.dev.demomvp.entity.Employee;
import com.hlub.dev.demomvp.retrofit.EmployeeRetrofit;
import com.hlub.dev.demomvp.retrofit.EmployeeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycleviewEmployee;
    private FloatingActionButton flAddEmployee;

    List<Employee> employeeList;
    EmployeeAdapter employeeAdapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleviewEmployee = (RecyclerView) findViewById(R.id.recycleviewEmployee);
        flAddEmployee = (FloatingActionButton) findViewById(R.id.flAddEmployee);

        flAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CreateActivity.class));
            }
        });

        employeeList = new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(this);
        employeeAdapter=new EmployeeAdapter(this,employeeList);
        recycleviewEmployee.setAdapter(employeeAdapter);
        recycleviewEmployee.setLayoutManager(linearLayoutManager);

        requestDataFromServer();
    }

    private void requestDataFromServer() {
        EmployeeRetrofit.getInstance().getEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                employeeList.addAll(response.body());
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });
    }
}
