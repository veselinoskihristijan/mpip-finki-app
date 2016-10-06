package com.example.hristijan.tabs2;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.items.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hristijan on 8/5/2016.
 */
public class MySubjectsAdapter extends RecyclerView.Adapter<MySubjectsAdapter.MyViewHolder> {

    private List<Subject> mySubjectsList;
//    private OnItemClickListener listener;
    private DatabaseHandler db;
    private MySubjectsAdapter mySubjectsAdapter;
//
//    public interface OnItemClickListener {
//        void onItemClick(Subject item);
//
//        void onLongClick(Subject item);
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, type;
        public ImageView delete;


        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.my_subject_name);
            type = (TextView) view.findViewById(R.id.my_subject_type_name);
            delete = (ImageView)view.findViewById(R.id.delete_subject_icon);
        }

        public void bind(final Subject subject) {
            title.setText(subject.getName());
            if(subject.isCompulsory()){
                type.setText("Задолжителен");
            } else {
                type.setText("Изборен");
            }


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    db.deleteMySubject(subject.getID());
                                    mySubjectsList.remove(subject);
                                    mySubjectsAdapter.notifyDataSetChanged();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(con);
                    builder.setMessage("Избриши го предметот?").setPositiveButton("ДА", dialogClickListener)
                            .setNegativeButton("НЕ", dialogClickListener).show();

                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    listener.onItemClick(subject);
//                }
//            });
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    listener.onLongClick(subject);
//                    return true;
//                }
//            });
        }
    }


    public MySubjectsAdapter(List<Subject> mySubjectsList) {
        this.mySubjectsList = mySubjectsList;
        this.mySubjectsAdapter = this;
//        this.listener = listener;

    }

    private Context con;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_subject_layout, parent, false);
        db = new DatabaseHandler(parent.getContext());
        con = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mySubjectsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mySubjectsList.size();
    }
}