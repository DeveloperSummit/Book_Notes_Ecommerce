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
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__2;
import com.example.himanshjosh.utlity.OnViewItem;

import java.util.List;

public class AdpterGrid extends RecyclerView.Adapter<AdpterGrid.ViewHolder> {

    private  FragmentActivity requireActivity;
    private  List<Datum__2> data;
    private OnViewItem onViewItem;


    public AdpterGrid(FragmentActivity requireActivity, List<Datum__2> data,OnViewItem onViewItem) {

        this.requireActivity=requireActivity;
        this.data=data;
        this.onViewItem=onViewItem;

    }

    @NonNull
    @Override
    public AdpterGrid.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_grid, parent, false);

        // Return a new holder instance
        AdpterGrid.ViewHolder viewHolder = new AdpterGrid.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterGrid.ViewHolder holder, int position) {

        holder.productName.setText(data.get(position).getPname());
        holder.productPrioce.setText("₹"+data.get(position).getRprice());

        Glide.with(requireActivity)
                .load(IMAGE_BASE_URL+data.get(position).getImage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(holder.productImage);

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

        private  TextView productName,productPrioce;
        private  ImageView productImage;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage =itemView.findViewById(R.id.product_image_g);
            productName =itemView.findViewById(R.id.product_name_g);
            productPrioce =itemView.findViewById(R.id.product_price_g);
            cardView =itemView.findViewById(R.id.cardview_product);
        }
    }


}
