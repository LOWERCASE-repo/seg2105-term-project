package com.example.seg2105termproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersViewAdapter extends RecyclerView.Adapter<UsersViewAdapter.UserHolder> {

    private User[] usersDataset;

    public static class UserHolder extends RecyclerView.ViewHolder{
        private final TextView tvUTypeRow, tvUNameRow;

        public UserHolder(View view){
            super(view);

            tvUTypeRow = view.findViewById(R.id.tvUTypeRow);
            tvUNameRow = view.findViewById(R.id.tvUNameRow);
        }

        public TextView getTvUTypeRow() {
            return tvUTypeRow;
        }

        public TextView getTvUNameRow() {
            return tvUNameRow;
        }
    }

    public UsersViewAdapter(User[] dataset) { this.usersDataset = dataset; }

    @Override
    public UsersViewAdapter.UserHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(UsersViewAdapter.UserHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return usersDataset.length;
    }

    public void refresh(User[] users){
        this.usersDataset = users;
        this.notifyDataSetChanged();
    }
}
