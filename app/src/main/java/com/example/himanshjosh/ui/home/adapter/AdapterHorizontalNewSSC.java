package com.example.himanshjosh.ui.home.adapter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__4;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__9;
import com.example.himanshjosh.utlity.OnViewItem;

import java.util.List;

public class AdapterHorizontalNewSSC extends RecyclerView.Adapter<AdapterHorizontalNewSSC.ViewHolder> {

    private List<Datum__9> data;
    private FragmentActivity fragmentActivity;
    private OnViewItem onViewItem;


    public AdapterHorizontalNewSSC(List<Datum__9> data, FragmentActivity fragmentActivity, OnViewItem onViewItem) {
        this.data=data;
        this.fragmentActivity=fragmentActivity;
        this.onViewItem=onViewItem;

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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewItem.onItemClick(data.get(position).getPid());
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice;
        ImageView imageView;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           productName=itemView.findViewById(R.id.product_name);
            productPrice=itemView.findViewById(R.id.product_price);
            imageView=itemView.findViewById(R.id.product_image);
            cardView =itemView.findViewById(R.id.cardview_holder);

        }
    }
}
