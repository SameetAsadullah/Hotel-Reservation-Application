package com.sameetasadullah.i180479_i180531.presentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sameetasadullah.i180479_i180531.R;

import java.util.ArrayList;
import java.util.List;

public class Hotel_row_adapter
        extends RecyclerView.Adapter<Hotel_row_adapter.Hotel_row_Holder>
        implements Filterable {

    List<Hotel_row> ls;
    List<Hotel_row> filteredList;
    Context c;

    public Hotel_row_adapter(List<Hotel_row> ls, Context c) {
        this.c=c;
        this.ls=ls;
        this.filteredList = ls;
    }

    @NonNull
    @Override
    public Hotel_row_adapter.Hotel_row_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.hotel_row,parent,false);
        return new Hotel_row_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Hotel_row_adapter.Hotel_row_Holder holder, int position) {
        holder.name.setText(filteredList.get(position).getName());
        holder.singlePrice.setText(filteredList.get(position).getSinglePrice());
        holder.doublePrice.setText(filteredList.get(position).getDoublePrice());
        holder.location.setText(filteredList.get(position).getLocation());
        holder.check_out_date.setText(filteredList.get(position).getCheck_out_date());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if(Key.isEmpty()){
                    filteredList = ls;
                }
                else{
                    List<Hotel_row> listFiltered = new ArrayList<>();
                    for (Hotel_row row: ls){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
                            listFiltered.add(row);
                        }
                    }
                    filteredList = listFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList =  (List<Hotel_row>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Hotel_row_Holder extends RecyclerView.ViewHolder {
        TextView name,singlePrice,doublePrice,location,check_out_date;
        public Hotel_row_Holder(@NonNull View itemView){
            super(itemView);
            name =itemView.findViewById(R.id.name);
            singlePrice= itemView.findViewById(R.id.single_price);
            doublePrice=itemView.findViewById(R.id.double_price);
            location=itemView.findViewById(R.id.Location_hotel);
            check_out_date=itemView.findViewById(R.id.check_out_date);
        }
    }
}

