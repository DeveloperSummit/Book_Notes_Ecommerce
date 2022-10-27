package com.example.himanshjosh.ui.home.adapter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__4;

import java.util.List;

public class AdapterHorizontalNew extends RecyclerView.Adapter<AdapterHorizontalNew.ViewHolder> {

    private List<Datum__4> data;
    private FragmentActivity fragmentActivity;

    public AdapterHorizontalNew(List<Datum__4> data, FragmentActivity fragmentActivity) {
        this.data=data;
        this.fragmentActivity=fragmentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_horizontalnew, parent, false);
                ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(data.get(position).getPname());
        holder.productPrice.setText("₹"+data.get(position).getRprice());

        Glide.with(fragmentActivity)
                .load(IMAGE_BASE_URL+data.get(position).getImage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           productName=itemView.findViewById(R.id.product_name);
            productPrice=itemView.findViewById(R.id.product_price);
            imageView=itemView.findViewById(R.id.product_image);
        }
    }
}
