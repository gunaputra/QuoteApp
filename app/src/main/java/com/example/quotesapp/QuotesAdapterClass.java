package com.example.quotesapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuotesAdapterClass extends RecyclerView.Adapter<QuotesAdapterClass.ViewHolder> {

    List<QuotesModelClass> quotes;
    Context context;
    DatabaseHelperClass databaseHelperClass;

    public QuotesAdapterClass(List<QuotesModelClass> quotes, Context context) {
        this.quotes = quotes;
        this.context = context;
        databaseHelperClass = new DatabaseHelperClass(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.quotes_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final QuotesModelClass quotesModelClass = quotes.get(position);

        holder.textViewID.setText(Integer.toString(quotesModelClass.getId()));
        holder.editText_Name.setText(quotesModelClass.getName());
        holder.editText_Quotes.setText(quotesModelClass.getQuotes());

        holder.button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringName = holder.editText_Name.getText().toString();
                String stringQuotes = holder.editText_Quotes.getText().toString();

                databaseHelperClass.updateEmployee(new QuotesModelClass(quotesModelClass.getId(),stringName, stringQuotes));
                notifyDataSetChanged();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelperClass.deleteEmployee(quotesModelClass.getId());
                quotes.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewID;
        TextView editText_Name;
        TextView editText_Quotes;
        Button button_Edit;
        Button button_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.text_id);
            editText_Name = itemView.findViewById(R.id.tv_author);
            editText_Quotes = itemView.findViewById(R.id.tv_content);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_Edit = itemView.findViewById(R.id.button_edit);

        }
    }
}
