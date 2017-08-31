package com.lucky.ebookapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    ArrayList<BookInfo> ItemList ;
    Context context;

    public RecyclerAdapter(ArrayList<BookInfo> itemList, Context context) {
        ItemList = itemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BookInfo bookInfo = ItemList.get(position);

        holder.book_title_text.setText(bookInfo.book_title);
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView book_title_text ;
        public ImageView book_img;

        public ViewHolder(View itemView) {

            super(itemView);

            book_title_text = (TextView) itemView.findViewById(R.id.book_title_id);
            book_img = (ImageView) itemView.findViewById(R.id.Book_img_id);


        }
    }

}
