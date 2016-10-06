package com.example.hristijan.tabs2;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hristijan.tabs2.ConsultationsFragment.OnListFragmentInteractionListener;
import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.dummy.ConsultationsContent.ConsultationItem;
import com.example.hristijan.tabs2.items.Consultation;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ConsultationItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyConsultationsRecyclerViewAdapter extends RecyclerView.Adapter<MyConsultationsRecyclerViewAdapter.ViewHolder> {

    private final List<Consultation> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;
    private DatabaseHandler db;

    public MyConsultationsRecyclerViewAdapter(List<Consultation> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_consultations, parent, false);
        context = parent.getContext();
        db = new DatabaseHandler(context);
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
        holder.mRoom.setText(mValues.get(position).getRoomName());
        holder.mTimeFromTo.setText(mValues.get(position).getDateFrom() + " - " + mValues.get(position).getDateTo());

        holder.mRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

                View locationView = LayoutInflater.from(context).inflate(R.layout.study_info, null);

                dialogBuilder.setView(locationView);

                dialogBuilder.setTitle(holder.mItem.getRoomName());
                dialogBuilder.setIcon(R.drawable.home_loc);

                TextView info = (TextView) locationView.findViewById(R.id.study_info);
                info.setText(holder.mItem.getRoomDescription());

                dialogBuilder.create().show();

            }
        });

                holder.mSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int len = db.getSubjectsCons(mValues.get(position).getEmail()).toString().length();
                String sub = db.getSubjectsCons(mValues.get(position).getEmail()).toString().substring(1, len-1);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

                View locationView = LayoutInflater.from(context).inflate(R.layout.study_info, null);

                dialogBuilder.setView(locationView);

                dialogBuilder.setTitle(holder.mItem.getStaffFirstName() + " " + holder.mItem.getStaffLastName());
                dialogBuilder.setIcon(R.drawable.graduate);

                TextView info = (TextView) locationView.findViewById(R.id.study_info);
                info.setText("Курсеви: " + sub);

                dialogBuilder.create().show();

            }
        });

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
        public final TextView mRoom;
        public final TextView mTimeFromTo;
        public final ImageView mSubjects;
        public Consultation mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mStaffImage = (ImageView) view.findViewById(R.id.teacherImage);
            mStaffName = (TextView) view.findViewById(R.id.teacherName);
            mRoom = (TextView) view.findViewById(R.id.cons_location);
            mTimeFromTo = (TextView) view.findViewById(R.id.cons_time);
            mSubjects = (ImageView)view.findViewById(R.id.subjectsIcon);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
