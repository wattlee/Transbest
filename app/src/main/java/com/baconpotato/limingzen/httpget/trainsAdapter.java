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
public class trainsAdapter extends BaseAdapter{
    private List<trainsBean> mList;
    private LayoutInflater mInflater;

    public trainsAdapter(Context context,List<trainsBean>data){
        mList=data;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){viewHolder=new ViewHolder();
        convertView = mInflater.inflate(R.layout.item,null);
        viewHolder.start_time=(TextView)convertView.findViewById(R.id.start_time);
            viewHolder.trainnumber=(TextView)convertView.findViewById(R.id.trainnumber);
            viewHolder.end_time=(TextView)convertView.findViewById(R.id.end_time);
            viewHolder.last_time=(TextView)convertView.findViewById(R.id.last_time);
            viewHolder.endplace=(TextView)convertView.findViewById(R.id.endplace);
            viewHolder.startplace=(TextView)convertView.findViewById(R.id.startplace);
            viewHolder.ticketsnumber=(TextView)convertView.findViewById(R.id.ticketsnumber);
            viewHolder.seattype=(TextView)convertView.findViewById(R.id.seattype);
            convertView.setTag(viewHolder);
        }else{viewHolder = (ViewHolder)convertView.getTag();}
        viewHolder.trainnumber.setText(mList.get(position).trainnumber);
        viewHolder.start_time.setText(mList.get(position).starttime);
        viewHolder.end_time.setText(mList.get(position).arrivaltime);
        viewHolder.startplace.setText(mList.get(position).startplace);
       viewHolder.ticketsnumber.setText(mList.get(position).ticketsnum);
        viewHolder.endplace.setText(mList.get(position).endplace);
        viewHolder.last_time.setText(mList.get(position).lasttime);
        viewHolder.seattype.setText(mList.get(position).seattype);
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
    }
}
