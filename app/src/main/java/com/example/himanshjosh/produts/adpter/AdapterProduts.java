package com.example.himanshjosh.produts.adpter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.produts.ProductActivity;
import com.example.himanshjosh.ui.home.adapter.AdapterHorizontal;
import com.example.himanshjosh.ui.home.listner.OnClickListner;
import com.example.himanshjosh.ui.home.model.get.ModelGetProductByCate;
import com.google.android.material.card.MaterialCardView;

public class AdapterProduts extends RecyclerView.Adapter<AdapterProduts.ViewHolder> {

    private ProductActivity productActivity;
    private ModelGetProductByCate listData;
    private OnClickListner onClickListner;

    public AdapterProduts(ProductActivity productActivity, ModelGetProductByCate listData,OnClickListner onClickListner) {
        this.productActivity=productActivity;
        this.listData=listData;
        this.onClickListner=onClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_product, parent, false);

        // Return a new holder instance
        AdapterProduts.ViewHolder viewHolder = new AdapterProduts.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(listData.getProduct().get(position).getPname());
        holder.cateType.setText(listData.getProduct().get(position).getSubcat());
        holder.orignalPrice.setText("₹"+listData.getProduct().get(position).getSprice());
        holder.localprice.setText("₹"+listData.getProduct().get(position).getRprice());

        Glide.with(productActivity)
                .load(IMAGE_BASE_URL+listData.getProduct().get(position).getImage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

        try {
            long value = Integer.parseInt(listData.getProduct().get(position).getRprice()) - Integer.parseInt(listData.getProduct().get(position).getSprice());
            long value2=Integer.parseInt(listData.getProduct().get(position).getRprice());
            double value1 =(double)( (double)value/(double) value2);
            double pertange = (double) value1 * 100;
            holder.offer.setText(String.valueOf((int)pertange)+ "% off");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListner.onEvent(listData.getProduct().get(position).getPid());
            }
        });

    }

    @Override
    public int getItemCount() {
       return listData.getProduct().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  ImageView imageView;
        private TextView textView, cateType, orignalPrice,localprice,offer;
        private MaterialCardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.text_product_name);
            cardView=itemView.findViewById(R.id.cardview_product);
            cateType=itemView.findViewById(R.id.sub_categoery);
            orignalPrice=itemView.findViewById(R.id.orignal_price);
            localprice=itemView.findViewById(R.id.local_price);
            offer=itemView.findViewById(R.id.textView5);
        }
    }
}
