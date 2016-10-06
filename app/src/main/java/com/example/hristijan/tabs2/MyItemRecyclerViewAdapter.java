package com.example.hristijan.tabs2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hristijan.tabs2.ItemFragment.OnListFragmentInteractionListener;
import com.example.hristijan.tabs2.dummy.DummyContent.DummyItem;
import com.example.hristijan.tabs2.items.Lecture;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Lecture> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;


    public MyItemRecyclerViewAdapter(List<Lecture> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        int idRes = context.getResources().getIdentifier(mValues.get(position).getStaffImageUrl(), "drawable", context.getPackageName());
        if(idRes != 0){
            holder.mStaffImage.setImageResource(idRes);
        }

        holder.mStaffName.setText(mValues.get(position).getStaffLastName() + " " + mValues.get(position).getStaffFirstName());
        holder.mSubjectName.setText(mValues.get(position).getSubjectName());
        holder.mSubjectTypeName.setText(mValues.get(position).getSubjectTypeName());
        holder.mClassroomName.setText(mValues.get(position).getClassroomName());
        holder.mTimeFromTo.setText(mValues.get(position).getDateFrom() + " - " + mValues.get(position).getDateTo());


        holder.mClassroomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

                View locationView = LayoutInflater.from(context).inflate(R.layout.study_info, null);

                dialogBuilder.setView(locationView);

                dialogBuilder.setTitle(holder.mItem.getClassroomName());
                dialogBuilder.setIcon(R.drawable.home_loc);

                TextView info = (TextView) locationView.findViewById(R.id.study_info);
                info.setText(holder.mItem.getClassroomDescription());

                dialogBuilder.create().show();

            }
        });

//        holder.mLocationNameD.setText(mValues.get(position).locationName);
//        holder.mCourseNameD.setText(mValues.get(position).courseNameD);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mStaffImage;
        public final TextView mStaffName;
        public final TextView mClassroomName;
        public final TextView mSubjectName;
        public final TextView mSubjectTypeName;
        public final TextView mTimeFromTo;
        public Lecture mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mStaffImage = (ImageView)view.findViewById(R.id.staffImage);
            mStaffName = (TextView)view.findViewById(R.id.staffName);
            mClassroomName = (TextView) view.findViewById(R.id.locationName);
            mSubjectName = (TextView) view.findViewById(R.id.courseName);
            mSubjectTypeName = (TextView) view.findViewById(R.id.courseYype);
            mTimeFromTo = (TextView) view.findViewById(R.id.time_from_to);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
