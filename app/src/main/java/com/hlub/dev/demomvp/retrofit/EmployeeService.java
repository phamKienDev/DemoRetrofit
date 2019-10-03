package com.hlub.dev.demomvp.retrofit;

import com.google.gson.JsonObject;
import com.hlub.dev.demomvp.entity.Employee;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EmployeeService {

    //http://dummy.restapiexample.com/api/v1/employees
    @GET("api/v1/employees")
    Call<List<Employee>> getEmployees();

    @DELETE("/api/v1/delete/{id}")
    Call<Employee> deleteEmployee(@Path("id") int itemId);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api/v1/create")
    Call<ResponseBody> createEmployee(@Body JsonObject model);


}
