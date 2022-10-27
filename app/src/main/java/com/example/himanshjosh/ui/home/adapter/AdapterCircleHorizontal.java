package com.example.himanshjosh.ui.home.adapter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.annotation.SuppressLint;
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
import com.example.himanshjosh.ui.home.listner.OnClickListner;
import com.example.himanshjosh.ui.home.main.homemodel.HomeDataModel;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.ModelHomeResponce;
import com.example.himanshjosh.ui.home.responce.get.ModelHomePageResponce;

import java.util.ArrayList;

public class AdapterCircleHorizontal extends RecyclerView.Adapter<AdapterCircleHorizontal.ViewHolder> {

    private ModelHomeResponce mainlist;
    private FragmentActivity fragmentActivity;
    private  OnClickListner onClickListner;

    public AdapterCircleHorizontal(FragmentActivity fragmentActivity, ModelHomeResponce mainlist, OnClickListner onClickListner) {
        this.mainlist=mainlist;
        this.fragmentActivity=fragmentActivity;
        this.onClickListner=onClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view__circle_horizontal, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(mainlist.getCategory().getData().get(position).getCategory());
        Glide.with(fragmentActivity)
                .load(IMAGE_BASE_URL+mainlist.getCategory().getData().get(position).getCimage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainlist.getCategory().getData().get(position).getId()!=null && !(mainlist.getCategory().getData().get(position).getId().isEmpty()))
                    onClickListner.onEvent(mainlist.getCategory().getData().get(position).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mainlist.getCategory().getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.cat_image);
            textView=itemView.findViewById(R.id.cat_title);


        }
    }
}
