package com.example.android_executor_network_call.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_executor_network_call.R;
import com.example.android_executor_network_call.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    private List<Post> list;
    String userId;

    public PostAdapter(Context context, List<Post> list, String userId) {
        this.context = context;
        this.list = list;
        this.userId = userId;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_lists, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {

//        if(userId.equals(list.get(position).getId()))
//        {
            holder.user_id.setText(list.get(position).getUserId());
            holder.post_id.setText(list.get(position).getId());
            holder.title.setText(list.get(position).getTitle());
            holder.body.setText(list.get(position).getBody());
//        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView user_id, post_id, title, body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_id = itemView.findViewById(R.id.user_id);
            post_id = itemView.findViewById(R.id.post_id);
            title = itemView.findViewById(R.id.title_id);
            body = itemView.findViewById(R.id.body_id);
        }
    }
}
