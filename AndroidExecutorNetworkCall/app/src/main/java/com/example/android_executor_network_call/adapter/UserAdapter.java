package com.example.android_executor_network_call.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_executor_network_call.MainActivity2;
import com.example.android_executor_network_call.R;
import com.example.android_executor_network_call.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    Context context;
    private List<User> list;

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

//    public UserAdapter(AdapterInterface adapterInterface, List<User> list) {
//        this.adapterInterface = adapterInterface;
//        this.list = list;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.username.setText(list.get(position).getUsername());

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "username "+list.get(position).getUsername(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context, MainActivity2.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("username", list.get(position).getUsername());
                intent.putExtra("email", list.get(position).getEmail());
                intent.putExtra("street", list.get(position).getStreet());
                intent.putExtra("suite", list.get(position).getSuite());
                intent.putExtra("city", list.get(position).getCity());
                intent.putExtra("zipcode", list.get(position).getZipcode());
                intent.putExtra("lat", list.get(position).getLat());
                intent.putExtra("lng", list.get(position).getLng());
                intent.putExtra("phone", list.get(position).getPhone());
                intent.putExtra("website", list.get(position).getWebsite());
                intent.putExtra("companyName", list.get(position).getCompanyName());
                intent.putExtra("catchPhrase", list.get(position).getCatchPhrase());
                intent.putExtra("bs", list.get(position).getBs());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);

        }
    }
}
