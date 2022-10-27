package com.example.himanshjosh.ui.orderhistory;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.LayoutHistorylistBinding;
import com.example.himanshjosh.ui.cartitem.model.CartDatum;
import com.example.himanshjosh.ui.orderhistory.model.Product;
import com.example.himanshjosh.utlity.OnViewItem;

import java.util.List;

public class HistoryAdpter extends RecyclerView.Adapter<HistoryAdpter.ViewHolder> {

    private  List<Product> list;
    private  OnViewItem onViewItem;
    private OrderHistoryFragment orderHistoryFragment;

    public HistoryAdpter(OrderHistoryFragment orderHistoryFragment, List<Product> cartData, OnViewItem onViewItem) {
        this.list=cartData;
        this.onViewItem=onViewItem;
        this.orderHistoryFragment=orderHistoryFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutHistorylistBinding cartItemBinding = LayoutHistorylistBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new ViewHolder(cartItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.productName.setText("Name : "+list.get(position).getPname());
        holder.binding.produuctoffer.setText("Product Quantity : "+list.get(position).getQty());
        holder.binding.productprcie.setText("Product Price : "+list.get(position).getPrice());
        Glide.with(orderHistoryFragment)
                .load(IMAGE_BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(holder.binding.productImage);


        holder.binding.imageDownlaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewItem.onItemClick(list.get(position).getPdf());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final LayoutHistorylistBinding binding;

        public ViewHolder(LayoutHistorylistBinding binding) {
            super(binding.getRoot());
            View itemView = binding.getRoot();

            this.binding = binding;


        }


    }
}
