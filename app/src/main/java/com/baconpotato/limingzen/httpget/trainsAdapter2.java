package com.baconpotato.limingzen.httpget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 明辰 on 2016/3/7.
 */
public class trainsAdapter2 extends BaseAdapter{
    private List<trainsBean> mList3;
    private LayoutInflater mInflater3;

    public trainsAdapter2(Context context,List<trainsBean>data){
        mList3=data;
        mInflater3 = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList3.size();
    }

    @Override
    public Object getItem(int position) {
        return mList3.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){viewHolder=new ViewHolder();
            convertView = mInflater3.inflate(R.layout.item3,null);
            viewHolder.start_time=(TextView)convertView.findViewById(R.id.begin_time);
            viewHolder.trainnumber=(TextView)convertView.findViewById(R.id.trainname);
            viewHolder.end_time=(TextView)convertView.findViewById(R.id.arr_time);
            viewHolder.last_time=(TextView)convertView.findViewById(R.id.lishi);
            viewHolder.endplace=(TextView)convertView.findViewById(R.id.endplc);
            viewHolder.startplace=(TextView)convertView.findViewById(R.id.startplc);
            viewHolder.ticketsnumber=(TextView)convertView.findViewById(R.id.ticketsnum);
            viewHolder.seattype=(TextView)convertView.findViewById(R.id.seattype);
            viewHolder.lattergetoffstation=(TextView)convertView.findViewById(R.id.latterstation);
            convertView.setTag(viewHolder);
        }else{viewHolder = (ViewHolder)convertView.getTag();}
        viewHolder.trainnumber.setText(mList3.get(position).trainnumber);
        viewHolder.start_time.setText(mList3.get(position).starttime);
        viewHolder.end_time.setText(mList3.get(position).arrivaltime);
        viewHolder.startplace.setText(mList3.get(position).startplace);
        viewHolder.ticketsnumber.setText(mList3.get(position).ticketsnum);
        viewHolder.endplace.setText(mList3.get(position).endplace);
        viewHolder.last_time.setText(mList3.get(position).lasttime);
        viewHolder.seattype.setText(mList3.get(position).seattype);
        viewHolder.lattergetoffstation.setText(mList3.get(position).latterGetOffStation);
        return convertView;
    }
    class ViewHolder{
        public TextView trainnumber;
        public TextView start_time;
        public TextView end_time;
        public TextView last_time;
        public TextView ticketsnumber;
        public TextView startplace;
        public TextView endplace;
        public TextView seattype;
        public TextView lattergetoffstation;
    }
}
