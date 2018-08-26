package edu.sit.great.kagapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter implements Filterable{
    Context mContext;;
    String[]  Title;
    int[] imge;

    ArrayList<search> searches;
    CustomFilter filter;
    ArrayList<search> filterList;


    public SearchAdapter(Context context, ArrayList<search> searches) {
        this.mContext = context;
        this.searches=searches;
        this.filterList=searches;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return searches.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return searches.get(arg0);
    }

    public String getItemName(int arg0) {
        return searches.get(arg0).getName();
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return searches.indexOf(getItem(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.searchlist, null);
        }

        TextView nameTxt= convertView.findViewById(R.id.item);
        ImageView img = convertView.findViewById(R.id.icon);

        nameTxt.setText(searches.get(position).getName());
        img.setImageResource(searches.get(position).getImg());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    //INNER CLASS
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<search> filters=new ArrayList<search>();

                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint))
                    {
                        search p=new search(filterList.get(i).getName(), filterList.get(i).getImg());

                        filters.add(p);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            searches=(ArrayList<search>) results.values;
            notifyDataSetChanged();
        }

    }
}
