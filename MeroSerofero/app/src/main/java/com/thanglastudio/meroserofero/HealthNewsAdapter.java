package com.thanglastudio.meroserofero;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisoo on 5/26/16.
 */
public class HealthNewsAdapter extends RecyclerView.Adapter<HealthNewsAdapter.ViewHolder> {

    private List<HealthNews> mHealthNewses;
    private Callback mCallback;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("localnews");
    private static final String TAG = "HealthNewsAdapter";


    public HealthNewsAdapter(Callback callback, Context context) {
        mCallback = callback;
        mHealthNewses = new ArrayList<>();

        myRef.addChildEventListener( new NewsChildEventListener());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HealthNews healthNews = mHealthNewses.get(position);
        holder.news_title.setText(healthNews.getNews_title());
        holder.news_content.setText(healthNews.getNews_content());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEdit(healthNews);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(mHealthNewses.get(position));
                return true;
            }
        });
    }

    public void remove(HealthNews healthNews) {
        //TODO: Remove the next line(s) and use Firebase instead
       myRef.child(healthNews.getKeys()).removeValue();
    }


    @Override
    public int getItemCount() {
        return mHealthNewses.size();
    }

    public void add(HealthNews healthNews) {
        //TODO: Remove the next line(s) and use Firebase instead

        myRef.push().setValue(healthNews);
    }

    public void update(HealthNews healthNews, String newTitle, String newContent) {
        //TODO: Remove the next line(s) and use Firebase instead
        healthNews.setNews_title(newTitle);
        healthNews.setNews_content(newContent);
        myRef.child(healthNews.getKeys()).setValue(healthNews);
    }

    public interface Callback {
        public void onEdit(HealthNews healthNews);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView news_title;
        private TextView news_content;

        public ViewHolder(View itemView) {
            super(itemView);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_content = (TextView) itemView.findViewById(R.id.news_content);
        }
    }


    private class NewsChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            HealthNews healthNews=dataSnapshot.getValue(HealthNews.class);
            healthNews.setKeys(dataSnapshot.getKey());
            mHealthNewses.add(0,healthNews);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            String key=dataSnapshot.getKey();
            HealthNews newHealthNews=dataSnapshot.getValue(HealthNews.class);
            for(HealthNews hn:mHealthNewses){

                if(hn.getKeys().equals(key)){

                    hn.setValues(newHealthNews);

                    break;
                }
            }

            notifyDataSetChanged();

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            String key= dataSnapshot.getKey();
            for (HealthNews healthNews: mHealthNewses){

                if(key.equals(healthNews.getKeys())){

                    mHealthNewses.remove(healthNews);
                    notifyDataSetChanged();
                    break;
                }
            }


        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            //empty

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

            Log.e(TAG, "onCancelled() called with: " + "databaseError = [" + databaseError + "]");


        }
    }
}
