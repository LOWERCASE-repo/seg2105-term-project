package com.example.seg2105termproject;
/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca> 
 *              Jerry S       <@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 * 
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersViewAdapter extends RecyclerView.Adapter<UsersViewAdapter.UserHolder> {

    // The array holding the Users to display on the RecyclerView.
    private User[] usersDataset;

    public static class UserHolder extends RecyclerView.ViewHolder{
        private final TextView tvUTypeRow, tvUNameRow;

        public UserHolder(View view){
            super(view);

            tvUTypeRow = view.findViewById(R.id.tvUTypeRow);
            tvUNameRow = view.findViewById(R.id.tvUNameRow);
        }

        public TextView getTvUTypeRow() { return tvUTypeRow; }

        public TextView getTvUNameRow() { return tvUNameRow; }
    }

    public UsersViewAdapter(User[] dataset) { this.usersDataset = dataset; }

    @Override
    public UserHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_view_row, parent, false);

        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewAdapter.UserHolder holder, int position) {
        holder.getTvUTypeRow().setText("User Type: " + usersDataset[position].getType().toString());
        holder.getTvUNameRow().setText("Name: " + usersDataset[position].getUsername());
    }

    @Override
    public int getItemCount() {
        return usersDataset.length;
    }

    /**
     * Displays the passed array of Users on the RecyclerView.
     * Essentially "refreshes" the RecyclerView with the passed information.
     * @param users     The array of Users to display on the RecyclerView.
     */
    public void refresh(User[] users){
        this.usersDataset = users;
        this.notifyDataSetChanged();
    }
}
