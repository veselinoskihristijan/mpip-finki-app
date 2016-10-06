package com.example.hristijan.tabs2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hristijan.tabs2.TeacherFragment.OnListFragmentInteractionListener;
import com.example.hristijan.tabs2.items.Staff;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Staff} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTeacherRecyclerViewAdapter extends RecyclerView.Adapter<MyTeacherRecyclerViewAdapter.ViewHolder> {

    private final List<Staff> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyTeacherRecyclerViewAdapter(List<Staff> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_teacher, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        int idRes = context.getResources().getIdentifier(mValues.get(position).getImageUrl(), "drawable", context.getPackageName());
        if(idRes != 0){
            holder.mStaffImage.setImageResource(idRes);
        }
        holder.mStaffFirstNameView.setText(mValues.get(position).getFirstName());
        holder.mStaffLastNameView.setText(mValues.get(position).getLastName());

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
        if(mValues != null)
        return mValues.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mStaffImage;
        public final TextView mStaffFirstNameView;
        public final TextView mStaffLastNameView;
        public Staff mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mStaffImage= (ImageView) view.findViewById(R.id.staffImage);
            mStaffFirstNameView = (TextView) view.findViewById(R.id.staffFirstName);
            mStaffLastNameView = (TextView) view.findViewById(R.id.staffLastName);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
