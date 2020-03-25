package com.esilv.fink.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.esilv.fink.R;
import com.esilv.fink.api.Customer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final List<Customer> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Customer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Customer selected = mData.get(position);
        holder.nameTV.setText(selected.getSURNAME());
        holder.dateTV.setText(selected.getBIRTHDATE().substring(0,10));
        holder.countryTV.setText(selected.getCOUNTRY());
        holder.nbOfProductsTV.setText(selected.getNBOFPRODUCTS().toString());
        holder.salaryTV.setText(selected.getESTIMATEDSALARY().toString().split("\\.")[0].replaceAll("(\\d)(?=(\\d{3})+$)", "$1 ") + " €");
        holder.balanceTV.setText(selected.getBALANCE().toString().split("\\.")[0].replaceAll("(\\d)(?=(\\d{3})+$)", "$1 ") + " €");

        //System.out.println(selected.getSURNAME());
        Picasso.get().load(selected.getImageUrl()).resize(150, 150).centerCrop().into(holder.imageView);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameTV;
        final ImageView imageView;
        final TextView dateTV;
        final TextView countryTV;
        final TextView nbOfProductsTV;
        final TextView salaryTV;
        final TextView balanceTV;

        ViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.UserImageView);
            dateTV = itemView.findViewById(R.id.dateTextView);
            countryTV = itemView.findViewById(R.id.NationalityTextView);
            nbOfProductsTV = itemView.findViewById(R.id.NbOfProducts);
            salaryTV = itemView.findViewById(R.id.Salary);
            balanceTV = itemView.findViewById(R.id.Balance);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Customer getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}