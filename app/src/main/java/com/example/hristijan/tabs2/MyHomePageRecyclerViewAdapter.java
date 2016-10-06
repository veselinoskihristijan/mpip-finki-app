package com.example.hristijan.tabs2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hristijan.tabs2.HomePageFragment.OnListFragmentInteractionListener;
import com.example.hristijan.tabs2.database.DatabaseHandler;
import com.example.hristijan.tabs2.items.Information;
import com.example.hristijan.tabs2.items.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Object} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHomePageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final int INFORMATION = 0, NEWS = 1;
    private DatabaseHandler db;
    private Context con;

    public MyHomePageRecyclerViewAdapter(List<Object> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        db = new DatabaseHandler(parent.getContext());
        con = parent.getContext();

        RecyclerView.ViewHolder viewHolder = null;
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case NEWS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_layout, parent, false);
                viewHolder = new ViewHolderNews(view);
                break;
            case INFORMATION:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.information_layout, parent, false);
                viewHolder = new ViewHolderInformation(view);
                break;
            default:
                break;
        }

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case NEWS:
                final ViewHolderNews holderNews = (ViewHolderNews) holder;
                final News news = (News) mValues.get(position);
                //holder.mItem = mValues.get(position);
                holderNews.mNewsTitleView.setText(news.getTitle());
                holderNews.mNewsTypeView.setText(news.getDate());
//                if(position==0)
//                    animate(holderNews.mNewsTypeView);
                Picasso.with(con).load(news.getImageUrl()).into(holderNews.mNewsImage);

//                holderNews.mNewsDayView.setText(news.getDate().toString());
//                holderNews.mNewsMonthView.setText(news.getDate().toString());

                holderNews.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListFragmentInteraction(news);
                        }
                    }
                });
                //configureViewHolder1(vh1, position);
                break;
            case INFORMATION:
                final ViewHolderInformation holderInformation = (ViewHolderInformation) holder;
                final Information information = (Information) mValues.get(position);
                //holder.mItem = mValues.get(position);
                holderInformation.mInformationTitleView.setText(information.getTitle());
                holderInformation.mInformationTypeView.setText(information.getContent());
                //holderInformation.mInformationDayView.setText(information.getDate().toString());
                //holderInformation.mInformationMonthView.setText(information.getDate().toString());


                holderInformation.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListFragmentInteraction(information);
                        }
                    }
                });
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

    private Animation mAnimation;

    public void animate (View view) {
        mAnimation = new AlphaAnimation(1.0f, 0.0f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setAnimation(mAnimation);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolderNews extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNewsTitleView;
//        public final TextView mNewsDayView;
//        public final TextView mNewsMonthView;
        public final ImageView mNewsImage;
        public final TextView mNewsTypeView;
        public News mItem;

        public ViewHolderNews(View view) {
            super(view);
            mView = view;
            mNewsTitleView = (TextView) view.findViewById(R.id.news_title);
            mNewsTypeView = (TextView) view.findViewById(R.id.news_type);

//            mNewsDayView = (TextView) view.findViewById(R.id.news);
//            mNewsMonthView = (TextView)view.findViewById(R.id.followSubject);
            mNewsImage = (ImageView)view.findViewById(R.id.news_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNewsTitleView.getText() + "'";
        }
    }

    public class ViewHolderInformation extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mInformationTitleView;
//        public final TextView mInformationHourView;
//        public final TextView mInformationDayView;
//        public final TextView mInformationMonthView;
        public final TextView mInformationTypeView;
        public Information mItem;

        public ViewHolderInformation(View view) {
            super(view);
            mView = view;
            mInformationTitleView = (TextView) view.findViewById(R.id.info_title);
//            mInformationHourView = (TextView)view.findViewById(R.id.info_hour);
//            mInformationDayView = (TextView)view.findViewById(R.id.info_day);
//            mInformationMonthView = (TextView) view.findViewById(R.id.info_month);
            mInformationTypeView = (TextView) view.findViewById(R.id.info_type);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mInformationTitleView.getText() + "'";
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position) instanceof News) {
            return NEWS;
        } else if (mValues.get(position) instanceof Information) {
            return INFORMATION;
        }
        return -1;
    }
}
