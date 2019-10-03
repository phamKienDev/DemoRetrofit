package com.hlub.dev.demomvp.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hlub.dev.demomvp.R;
import com.hlub.dev.demomvp.UpdateActivity;
import com.hlub.dev.demomvp.entity.Employee;
import com.hlub.dev.demomvp.retrofit.EmployeeRetrofit;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    Context context;
    List<Employee> employeeList;


    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @Override
    public int getItemViewType(int position) {
        int pos = position + 1;
        if (pos % 5 == 0) {
            return 1;
        } else if (pos % 3 == 0) {
            return 2;
        } else if (pos % 2 == 0) {
            return 3;
        } else {
            return 4;
        }

    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case 1:
                return new EmployeeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_employee_a, parent, false));
            case 2:
                return new EmployeeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_employee_b, parent, false));
            case 3:
                return new EmployeeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_employee_c, parent, false));
            default:
                return new EmployeeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_employee_a, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, final int position) {

        Random rand = new Random();
        //Lương nhân ramdom từ 50 -> 200 sau đó format thành 1.000.000 vnđ
        Double salary = Double.parseDouble(employeeList.get(position).getEmployeeSalary()) * (rand.nextInt(150) + 50);
        String name = employeeList.get(position).getEmployeeName().toString();
        String age = employeeList.get(position).getEmployeeAge().toString();
        final int id = Integer.parseInt(employeeList.get(position).getId().toString());


        holder.tvItemName.setText(name);
        holder.tvItemMoney.setText(formatVnCurrence(context, String.valueOf(salary)));
        holder.tvItemAge.setText(age);
        holder.imgItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDeleteEmployee(id, position);
            }
        });

        holder.containerUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UpdateActivity.class));
            }
        });


    }


    private void showDialogDeleteEmployee(final int id, final int pos) {
        final Dialog dialog = new Dialog(context);

        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);

        TextView tvDeleteHuy = (TextView) view.findViewById(R.id.tvDeleteHuy);
        TextView tvDeleteOK = (TextView) view.findViewById(R.id.tvDeleteOK);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvDeleteHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvDeleteOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Employee> deleteEmp = EmployeeRetrofit.getInstance().deleteEmployee(id);
                deleteEmp.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        Toast.makeText(context, "Đã xóa Employee", Toast.LENGTH_SHORT).show();
                        employeeList.remove(pos);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(context, "Xóa Employee không thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }


    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvItemAge;
        private TextView tvItemMoney;
        private ImageView imgItemDelete;
        private LinearLayout containerUpdate;

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            tvItemAge = (TextView) itemView.findViewById(R.id.tvItemAge);
            tvItemMoney = (TextView) itemView.findViewById(R.id.tvItemMoney);
            imgItemDelete = (ImageView) itemView.findViewById(R.id.imgItemDelete);
            containerUpdate = (LinearLayout) itemView.findViewById(R.id.container_update);

        }
    }

    public static String formatVnCurrence(Context context, String price) {

        NumberFormat format =
                new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        price = (!TextUtils.isEmpty(price)) ? price : "0";
        price = price.trim();
        price = format.format(Double.parseDouble(price));
        price = price.replaceAll(",", "\\.");

        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }
        price = String.format("%s vnđ", price);
        return price;
    }
}
