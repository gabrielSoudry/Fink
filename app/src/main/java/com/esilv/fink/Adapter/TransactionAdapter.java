package com.esilv.fink.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esilv.fink.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    Context context;
    List<String> TypeDepense, Value, Month, Year;

    public TransactionAdapter(Context context, List<String> TypeDepense, List<String> Value, List<String> Month, List<String> Year) {
        this.context = context;
        this.TypeDepense = TypeDepense;
        this.Value = Value;
        this.Month = Month;
        this.Year = Year;
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
        holder.ttype.setText(this.TypeDepense.get(position));
        holder.value.setText(this.Value.get(position));
        holder.month.setText(this.Month.get(position));
        holder.year.setText(this.Year.get(position));
    }

    @Override
    public int getItemCount() {
        return TypeDepense.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView ttype, value, month, year;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ttype = itemView.findViewById(R.id.Type);
            value = itemView.findViewById(R.id.Value);
            month = itemView.findViewById(R.id.Month);
            year = itemView.findViewById(R.id.Year);
        }
    }
}
