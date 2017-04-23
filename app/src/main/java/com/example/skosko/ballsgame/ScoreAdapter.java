package com.example.skosko.ballsgame;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by skosko on 21/04/2017.
 */

public class ScoreAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ScoreItem> mDataSource;

    public ScoreAdapter(Context context, ArrayList<ScoreItem> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.score_list_item, parent, false);

        TextView ScoreText =
                (TextView) rowView.findViewById(R.id.ScoreText);
        TextView NameText =
                (TextView) rowView.findViewById(R.id.NameText);
        TextView sijoitus =
                (TextView) rowView.findViewById(R.id.sijoitus);



// Get detail element

        ScoreItem item = (ScoreItem) getItem(position);

// 2

        sijoitus.setText(position+1 + ".");
        ScoreText.setText("pisteet: " + item.points);
        NameText.setText(item.name);

        sijoitus.setTextColor(Color.BLACK);
        ScoreText.setTextColor(Color.BLACK);
        NameText.setTextColor(Color.BLACK);
        return rowView;
    }




}
