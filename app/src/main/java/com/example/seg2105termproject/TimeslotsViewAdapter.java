package com.example.seg2105termproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <sraad062@uottawa.ca>
 *              Jerry S       <jsoon029@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 *
 */
public class TimeslotsViewAdapter extends RecyclerView.Adapter<TimeslotsViewAdapter.TimeslotsHolder>{

    private final RecyclerView rView;
    private Course course;

    public static class TimeslotsHolder extends RecyclerView.ViewHolder{
        private final TextView tvDay, tvTimeSlot;

        public TimeslotsHolder (View view){
            super(view);

            tvDay = view.findViewById(R.id.tvDay);
            tvTimeSlot = view.findViewById(R.id.tvTimeSlot);
        }

        public TextView getTvDay() { return tvDay; }
        public TextView getTvTimeSlot() { return tvTimeSlot; }
    }

    public TimeslotsViewAdapter(RecyclerView rv, Course course) {
        this.rView = rv;
        this.course = course;
    }

    @Override
    public TimeslotsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeslot_view_row, parent, false);

        // onClick method for each holder.
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableActivity.self, EditTimeslotActivity.class);
                int position = rView.getChildLayoutPosition(v);

                intent.putExtra(InstructorActivity.SELECTED_COURSE, course.getCode());
                intent.putExtra(TimetableActivity.TIMESLOT_POSITION, position);

                TimetableActivity.self.startActivity(intent);
            }
        });

        return new TimeslotsViewAdapter.TimeslotsHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeslotsViewAdapter.TimeslotsHolder holder, int position) {
        holder.getTvDay().setText(course.getDays()[position].toString());
        holder.getTvTimeSlot().setText(String.format("%s - %s",
                course.getStartTimes()[position].toString(),
                course.getEndTimes()[position].toString()));
    }

    @Override
    public int getItemCount() { return course.getDays().length; }

    /**
     * Displays the time array of the passed Course on the RecyclerView.
     * Essentially "refreshes" the RecyclerView with the passed information.
     * @param course     The course which needs its time displayed on the RecyclerView.
     */
    public void refresh(Course course){
        this.course = course;
        notifyDataSetChanged();
    }
}
