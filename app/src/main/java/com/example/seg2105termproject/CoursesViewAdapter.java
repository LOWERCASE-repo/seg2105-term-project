package com.example.seg2105termproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.CourseHolder> {

    // The array holding the Courses to display on the RecyclerView.
    private Course[] courseDataset;

    public static class CourseHolder extends RecyclerView.ViewHolder{
        private final TextView tvCName, tvCCode;

        public CourseHolder (View view){
            super(view);

            tvCName = view.findViewById(R.id.tvCNameRow);
            tvCCode = view.findViewById(R.id.tvCCodeRow);
        }

        public TextView getTvCName() {
            return tvCName;
        }

        public TextView getTvCCode() { return tvCCode; }
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
        holder.getTvCName().setText("Course Name: " + courseDataset[position].getCourseName());
        holder.getTvCCode().setText("Course Code: " + courseDataset[position].getCourseCode());
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
