package com.vivekcorp.driverside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.vivekcorp.driverside.model.DonatorInfo;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private final List<DonatorInfo> listdata;

    // RecyclerView recyclerView;
    public ListAdapter(List<DonatorInfo> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        System.out.println("here");
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DonatorInfo myListData = listdata.get(position);
        holder.donatorName.setText(myListData.getDonatorName());
        holder.donatorPhone.setText(myListData.getPhoneNum());
        holder.donatorAddress.setText(myListData.getAddress());
        holder.imageView.setImageBitmap(myListData.getBitmap());
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getDonatorName(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView donatorName;
        public TextView donatorPhone;
        public TextView donatorAddress;
        public Button collect;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.foodImage);
            this.donatorName = (TextView) itemView.findViewById(R.id.name);
            this.donatorPhone = (TextView) itemView.findViewById(R.id.phone_no);
            this.donatorAddress = (TextView) itemView.findViewById(R.id.address);
            this.collect = (Button) itemView.findViewById(R.id.collect);

        }
    }
}