package com.example.hristijan.tabs2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hristijan.tabs2.SubjectFragment.OnListFragmentInteractionListener;
import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.items.Subject;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Subject} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySubjectRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final int SUBJECT = 0, SEMESTER = 1;
    private DatabaseHandler db;

    public MySubjectRecyclerViewAdapter(List<Object> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        db = new DatabaseHandler(parent.getContext());

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case SUBJECT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_subject, parent, false);
                viewHolder = new ViewHolder1(view);
                break;
            case SEMESTER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_semester, parent, false);
                viewHolder = new ViewHolder2(view);
                break;
            default:
                break;
        }


        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {



        switch (holder.getItemViewType()) {
            case SUBJECT:
                final ViewHolder1 vh1 = (ViewHolder1) holder;
                final Subject subject = (Subject) mValues.get(position);
                //holder.mItem = mValues.get(position);
                vh1.mSubjectNameView.setText(subject.getName());
                if(db.isAlreadyAdded(subject.getID())){
                    vh1.followSubject.setImageResource(R.drawable.eye);
                    vh1.mSubjectNameView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    vh1.followSubject.setImageResource(R.drawable.view);
                    vh1.mSubjectNameView.setTypeface(Typeface.DEFAULT);
                }
                vh1.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListFragmentInteraction(subject);
                        }
                    }
                });
                vh1.followSubject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(db.isAlreadyAdded(subject.getID())){
                            db.deleteMySubject(subject.getID());
                            vh1.followSubject.setImageResource(R.drawable.view);
                            vh1.mSubjectNameView.setTypeface(Typeface.DEFAULT);
                            //vh1.mSubjectNameView.setTextColor(Color.parseColor("#6ebde6"));
                        } else {
                            db.addMySubject(subject.getID());
                            vh1.followSubject.setImageResource(R.drawable.eye);
                            vh1.mSubjectNameView.setTypeface(Typeface.DEFAULT_BOLD);
                            //vh1.mSubjectNameView.setTextColor(Color.parseColor("#46537d"));
                        }
                    }
                });
                //configureViewHolder1(vh1, position);
                break;
            case SEMESTER:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                String semester = (String) mValues.get(position);
                vh2.mSemesterView.setText(semester);
                //configureViewHolder2(vh2, position);
                break;
            default:
                break;
        }





//
//        holder.mItem = mValues.get(position);
//        if(mValues.get(position).getName() != null) {
//            holder.mSubjectNameView.setText(mValues.get(position).getName());
//        } else {
//            holder.mSubjectNameView.setText("empty");
//        }

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSubjectNameView;
        public final ImageView followSubject;
        public Subject mItem;

        public ViewHolder1(View view) {
            super(view);
            mView = view;
            mSubjectNameView = (TextView) view.findViewById(R.id.subjectName);
            followSubject = (ImageView)view.findViewById(R.id.followSubject);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSubjectNameView.getText() + "'";
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSemesterView;
        public String mItem;

        public ViewHolder2(View view) {
            super(view);
            mView = view;
            mSemesterView = (TextView) view.findViewById(R.id.semester);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSemesterView.getText() + "'";
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position) instanceof Subject) {
            return SUBJECT;
        } else if (mValues.get(position) instanceof String) {
            return SEMESTER;
        }
        return -1;
    }
}
