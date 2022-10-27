package com.example.himanshjosh.cart.adapter;

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
import com.example.himanshjosh.databinding.CartItemBinding;
import com.example.himanshjosh.ui.cartitem.model.CartDatum;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private FragmentActivity requireActivity;
    private List<CartDatum> cartData;
    private int quantity = 1;
    private OnClickUpdateValue onClickUpdateValue;

    public CartAdapter(FragmentActivity requireActivity, List<CartDatum> cartData, OnClickUpdateValue onClickUpdateValue) {

        this.requireActivity = requireActivity;
        this.cartData = cartData;
        this.onClickUpdateValue = onClickUpdateValue;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        //return new ViewHolder(view);
        CartItemBinding cartItemBinding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new ViewHolder(cartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.productName.setText(cartData.get(position).getPname());
        holder.binding.productprcie.setText(cartData.get(position).getPrice());
        holder.binding.produuctoffer.setText(cartData.get(position).getDiscount());
        holder.binding.display.setText(cartData.get(position).getQty());
        holder.binding.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateValue.onRemoveCart(position, cartData.get(position).getPid());
            }
        });
        holder.binding.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(cartData.get(position).getQty());
                quantity++;
                holder.binding.display.setText("" + quantity);
                cartData.get(position).setQty(String.valueOf(quantity));
                onClickUpdateValue.onUpdate(String.valueOf(quantity), position, cartData);
            }
        });

        holder.binding.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(cartData.get(position).getQty());
                if (quantity > 1)
                    quantity--;
                holder.binding.display.setText("" + quantity);
                cartData.get(position).setQty(String.valueOf(quantity));
                onClickUpdateValue.onUpdate(String.valueOf(quantity), position, cartData);


            }
        });


        Glide.with(holder.itemView)
                .load(IMAGE_BASE_URL + cartData.get(position).getImage())
                .fitCenter()
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .circleCrop()
                .into(holder.binding.productImage);

    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CartItemBinding binding;

        public ViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            View itemView = binding.getRoot();

            this.binding = binding;


        }
    }

    public interface OnClickUpdateValue {
        void onUpdate(String value, int position, List<CartDatum> cartList);

        void onRemoveCart(int postion, String pid);
    }
}