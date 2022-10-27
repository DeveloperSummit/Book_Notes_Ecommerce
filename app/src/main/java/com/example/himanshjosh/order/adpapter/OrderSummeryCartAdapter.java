package com.example.himanshjosh.order.adpapter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.AdpterviewOrderSummeryBinding;
import com.example.himanshjosh.databinding.CartItemBinding;
import com.example.himanshjosh.ui.cartitem.model.CartDatum;

import java.util.List;


public class OrderSummeryCartAdapter extends RecyclerView.Adapter<OrderSummeryCartAdapter.ViewHolder> {
    private FragmentActivity requireActivity;
    private List<CartDatum> cartData;
    private int quantity = 1;

    public OrderSummeryCartAdapter(FragmentActivity requireActivity, List<CartDatum> cartData) {

        this.requireActivity = requireActivity;
        this.cartData = cartData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdpterviewOrderSummeryBinding orcersummery = AdpterviewOrderSummeryBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new ViewHolder(orcersummery);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.textviewProductname.setText(cartData.get(position).getPname());
        holder.binding.textviewProductPrice.setText("₹"+cartData.get(position).getPrice());
        holder.binding.textviewProductOffer.setText(cartData.get(position).getDiscount());
        holder.binding.textviewProductQuantity.setText("Qty: "+cartData.get(position).getQty());


        Glide.with(holder.itemView)
                .load(IMAGE_BASE_URL + cartData.get(position).getImage())
                .fitCenter()
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .circleCrop()
                .into(holder.binding.imageviewProductImage);

    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final AdpterviewOrderSummeryBinding binding;

        public ViewHolder(AdpterviewOrderSummeryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }


}