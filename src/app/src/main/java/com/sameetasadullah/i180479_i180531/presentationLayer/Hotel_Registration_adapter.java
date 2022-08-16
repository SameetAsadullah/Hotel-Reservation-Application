package com.sameetasadullah.i180479_i180531.presentationLayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sameetasadullah.i180479_i180531.R;

import java.util.ArrayList;
import java.util.List;

public class Hotel_Registration_adapter
        extends RecyclerView.Adapter<Hotel_Registration_adapter.Hotel_Registration_Holder>
        implements Filterable {

    List<Hotel_Registraion_row> ls;
    List<Hotel_Registraion_row> filteredList;
    Context c;

    public Hotel_Registration_adapter(List<Hotel_Registraion_row> ls, Context c) {
        this.c=c;
        this.ls=ls;
        this.filteredList = ls;
    }

    @NonNull
    @Override
    public Hotel_Registration_adapter.Hotel_Registration_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.hotel_registration_row,parent,false);
        return new Hotel_Registration_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Hotel_Registration_adapter.Hotel_Registration_Holder holder, int position) {
        holder.name.setText(filteredList.get(position).getName());
        holder.singlePrice.setText(filteredList.get(position).getSinglePrice());
        holder.doublePrice.setText(filteredList.get(position).getDoublePrice());
        holder.location.setText(filteredList.get(position).getLocation());
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
                    List<Hotel_Registraion_row> listFiltered = new ArrayList<>();
                    for (Hotel_Registraion_row row: ls){
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
                filteredList =  (List<Hotel_Registraion_row>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Hotel_Registration_Holder extends RecyclerView.ViewHolder {
        TextView name,singlePrice,doublePrice,location;
        public Hotel_Registration_Holder(@NonNull View itemView){
            super(itemView);
            name =itemView.findViewById(R.id.name);
            singlePrice= itemView.findViewById(R.id.single_price);
            doublePrice=itemView.findViewById(R.id.double_price);
            location=itemView.findViewById(R.id.Location_hotel);
        }
    }
}

