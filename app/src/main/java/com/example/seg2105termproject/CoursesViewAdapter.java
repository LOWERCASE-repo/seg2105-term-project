package com.example.seg2105termproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca> 
 *              Jerry S       <jsoon029@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 * 
*/
public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.CourseHolder> {

    // The array holding the Courses to display on the RecyclerView.
    private Course[] courseDataset;

    public static class CourseHolder extends RecyclerView.ViewHolder{
        private final TextView tvCNameRow, tvCCodeRow, tvCInstructorRow;

        public CourseHolder (View view){
            super(view);

            tvCNameRow = view.findViewById(R.id.tvCNameRow);
            tvCCodeRow = view.findViewById(R.id.tvCCodeRow);
            tvCInstructorRow = view.findViewById(R.id.tvCInstructorRow);
        }

        public TextView getTvCName() {
            return tvCNameRow;
        }

        public TextView getTvCCode() { return tvCCodeRow; }

        public TextView getTvCInstructorRow() { return tvCInstructorRow; }
    }

    public CoursesViewAdapter(Course[] dataset) { this.courseDataset = dataset; }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courses_view_row, parent, false);

        return new CourseHolder(view);
    }

    // Laying out the text in the layout.
    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        holder.getTvCName().setText(courseDataset[position].getName());
        holder.getTvCCode().setText(courseDataset[position].getCode());

        Instructor instructor = courseDataset[position].getInstructor();
        String text = instructor == null ? "-" : instructor.getUsername();
        holder.getTvCInstructorRow().setText(text);
    }

    @Override
    public int getItemCount() {
        return courseDataset.length;
    }

    /**
     * Displays the passed array of Courses on the RecyclerView.
     * Essentially "refreshes" the RecyclerView with the passed information.
     * @param courses     The array of Courses to display on the RecyclerView.
     */
    public void refresh(Course[] courses){
        this.courseDataset = courses;
        this.notifyDataSetChanged();
    }
}
