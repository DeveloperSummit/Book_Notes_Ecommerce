package com.example.himanshjosh.ui.home.adapter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.graphics.ColorSpace;
import android.media.Image;
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
import com.example.himanshjosh.ui.home.main.homemodel.HomeDataModel;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.ModelHomeResponce;
import com.example.himanshjosh.ui.home.responce.get.ModelHomePageResponce;
import com.example.himanshjosh.utlity.OnViewItem;

public class AdapterHorizontal extends RecyclerView.Adapter<AdapterHorizontal.ViewHolder> {

    private FragmentActivity fragmentActivity;
    private ModelHomeResponce categorytype;
    private OnViewItem onViewItem;



    public AdapterHorizontal(FragmentActivity requireActivity, ModelHomeResponce categorytype,OnViewItem onViewItem) {
        this.fragmentActivity=requireActivity;
        this.categorytype=categorytype;
        this.onViewItem=onViewItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_horizontal, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(categorytype.getUpscCategory().getData().get(position).getPname());
        holder.textView1.setText("₹"+categorytype.getUpscCategory().getData().get(position).getSprice());

        Glide.with(fragmentActivity)
                .load(IMAGE_BASE_URL+categorytype.getUpscCategory().getData().get(position).getImage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewItem.onItemClick(categorytype.getUpscCategory().getData().get(position).getPid());
            }
        });


    }

    @Override
    public int getItemCount() {
        return categorytype.getUpscCategory().getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView textView1;
         CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.catee_title);
            imageView=itemView.findViewById(R.id.catee_image);
            textView1=itemView.findViewById(R.id.cate_price);
            cardView =itemView.findViewById(R.id.cardview_main);



        }
    }
}
