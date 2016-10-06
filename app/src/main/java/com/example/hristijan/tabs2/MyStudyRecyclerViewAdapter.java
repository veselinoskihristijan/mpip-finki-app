package com.example.hristijan.tabs2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hristijan.tabs2.StudyFragment.OnListFragmentInteractionListener;
import com.example.hristijan.tabs2.dummy.DummyContent.DummyItem;
import com.example.hristijan.tabs2.items.Study;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Study} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStudyRecyclerViewAdapter extends RecyclerView.Adapter<MyStudyRecyclerViewAdapter.ViewHolder> {

    private final List<Study> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyStudyRecyclerViewAdapter(List<Study> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_study, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mStudyNameView.setText(mValues.get(position).getName());

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
        public final TextView mStudyNameView;
        public Study mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mStudyNameView = (TextView) view.findViewById(R.id.study_name);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
