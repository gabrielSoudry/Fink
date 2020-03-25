package com.esilv.fink.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esilv.fink.R;
import com.esilv.fink.api.InnerTransaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> implements Filterable {

    private final Context context;
    protected List<InnerTransaction> originalList;
    private List<InnerTransaction> mData;

    public TransactionAdapter(Context context, List<InnerTransaction> mData) {
        this.context = context;
        this.originalList = mData;
        this.mData = mData;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (List<InnerTransaction>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<InnerTransaction> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<InnerTransaction> getFilteredResults(String constraint) {
        List<InnerTransaction> results = new ArrayList<>();

        for (InnerTransaction item : originalList) {
            if (item.getTypeDepense().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_layout, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.ttype.setText(this.mData.get(position).getTypeDepense());
        holder.value.setText(this.mData.get(position).getValue());
        holder.month.setText(this.mData.get(position).getMonth());
        holder.year.setText(this.mData.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        final TextView ttype;
        final TextView value;
        final TextView month;
        final TextView year;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ttype = itemView.findViewById(R.id.Type);
            value = itemView.findViewById(R.id.Value);
            month = itemView.findViewById(R.id.Month);
            year = itemView.findViewById(R.id.Year);
        }
    }
}
