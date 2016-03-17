package com.baconpotato.limingzen.httpget;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by 明辰 on 2016/3/12.
 */
public class airsAdapter extends BaseAdapter{
    private List<airsBean> mList1;
    private LayoutInflater mInflater1;

    public airsAdapter(Context context1,List<airsBean>data1){
        mList1=data1;
        mInflater1 = LayoutInflater.from(context1);
    }
    @Override
    public int getCount() {
        return mList1.size();
    }

    @Override
    public Object getItem(int position) {
        return mList1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){viewHolder=new ViewHolder();
            convertView = mInflater1.inflate(R.layout.item2,null);
            viewHolder.airnumber=(TextView)convertView.findViewById(R.id.airnumber);
            viewHolder.startport=(TextView)convertView.findViewById(R.id.startairport);
            viewHolder.endport=(TextView)convertView.findViewById(R.id.endairport);
            viewHolder.flytime=(TextView)convertView.findViewById(R.id.flytime);
            viewHolder.arrivetime=(TextView)convertView.findViewById(R.id.arrivaltime);
            viewHolder.ticketprice=(TextView)convertView.findViewById(R.id.airprice);
            viewHolder.copyname=(TextView)convertView.findViewById(R.id.company_name);
            convertView.setTag(viewHolder);
        }else{viewHolder = (ViewHolder)convertView.getTag();}
        viewHolder.airnumber.setText(mList1.get(position).airnumber);
        viewHolder.startport.setText(mList1.get(position).startport);
        viewHolder.endport.setText(mList1.get(position).endport);
        viewHolder.flytime.setText(mList1.get(position).flytime);
        viewHolder.arrivetime.setText(mList1.get(position).arrivaltime);
        viewHolder.ticketprice.setText(mList1.get(position).tkprice);
        viewHolder.copyname.setText(mList1.get(position).copyname);
        return convertView;
    }
    class ViewHolder{
        public TextView airnumber;
        public TextView startport;
        public TextView endport;
        public TextView arrivetime;
        public TextView ticketprice;
        public TextView copyname;
        public TextView flytime;
    }
}
