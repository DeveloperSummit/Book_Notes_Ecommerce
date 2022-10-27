package com.example.himanshjosh.ui.home.main;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.FragmentHomeBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.produts.AllProductActivity;
import com.example.himanshjosh.produts.ProductActivity;
import com.example.himanshjosh.produts.ProductDetailsActivity;
import com.example.himanshjosh.ui.home.adapter.AdapterCircleHorizontal;
import com.example.himanshjosh.ui.home.adapter.AdapterHorizontal;
import com.example.himanshjosh.ui.home.adapter.AdapterHorizontalBpsc;
import com.example.himanshjosh.ui.home.adapter.AdapterHorizontalNewSSC;
import com.example.himanshjosh.ui.home.adapter.AdpteBankrGrid;
import com.example.himanshjosh.ui.home.adapter.AdpterGrid;
import com.example.himanshjosh.ui.home.adapter.slider_adapter;
import com.example.himanshjosh.ui.home.listner.OnClickListner;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__2;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__5;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.Datum__9;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.ModelHomeResponce;
import com.example.himanshjosh.ui.home.model.SliderItem;
import com.example.himanshjosh.utlity.OnViewItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnClickListner, OnViewItem {

    private FragmentHomeBinding binding;
    private SliderView sliderView;
    private slider_adapter adapter;
    private NetworkListener networkListenerl;
    private ACProgressFlower progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        networkListenerl = RetrofitService.getClient().create(NetworkListener.class);
        getHomeData(networkListenerl.getHomeDetails());

        //   HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // View root = binding.getRoot();
        //  homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        swipeRefreshLayout = binding.swiperefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData(networkListenerl.getHomeDetails());

            }
        });


        binding.cardviewFirst.setBackgroundResource(R.drawable.image_firsttnew);
        binding.cardviewGrid.setBackgroundResource(R.drawable.image_threeeee);
        binding.cardviewThree.setBackgroundResource(R.drawable.image_firsttnew);
        binding.cardviewGridOne.setBackgroundResource(R.drawable.image_threeeee);
        binding.cardviewFive.setBackgroundResource(R.drawable.img_first22);


        binding.newArrivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AllProductActivity.class));
            }
        });
        binding.toparrivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent("1");
            }
        });
        binding.bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent("2");
            }
        });
        binding.toparrivelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent("3");
            }
        });
        binding.newArrivelFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent("4");
            }
        });
        binding.imageStrip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEvent("5");

            }
        });


        return binding.getRoot();
    }

    private void onViewGrid(List<Datum__2> data) {
        if (data.size() <= 4) {
            AdpterGrid adpterGrid = new AdpterGrid(requireActivity(), data, this);
            binding.recyviewGrid.setAdapter(adpterGrid);
            binding.recyviewGrid.setHasFixedSize(true);
            binding.recyviewGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            ArrayList<Datum__2> arrayList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (i == 4) {
                    break;
                } else {
                    arrayList.add(data.get(i));

                }
            }

            AdpterGrid adpterGrid = new AdpterGrid(requireActivity(), arrayList, this);
            binding.recyviewGrid.setAdapter(adpterGrid);
            binding.recyviewGrid.setHasFixedSize(true);
            binding.recyviewGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }


    }

    private void onViewGridBank(List<Datum__5> data) {
        if (data.size() <= 4) {
            AdpteBankrGrid bankrGrid = new AdpteBankrGrid(requireActivity(), data, this);
            binding.recyviewHorizontalNewbank.setAdapter(bankrGrid);
            binding.recyviewHorizontalNewbank.setHasFixedSize(true);
            binding.recyviewHorizontalNewbank.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            ArrayList<Datum__5> arrayList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (i == 4) {
                    break;
                } else {
                    arrayList.add(data.get(i));

                }
            }

            AdpteBankrGrid bankrGrid = new AdpteBankrGrid(requireActivity(), arrayList, this);
            binding.recyviewHorizontalNewbank.setAdapter(bankrGrid);
            binding.recyviewHorizontalNewbank.setHasFixedSize(true);
            binding.recyviewHorizontalNewbank.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        }

    }

    private void onViewHorizontalssc(List<Datum__9> data) {
        AdapterHorizontalNewSSC adapter_horizontalNew = new AdapterHorizontalNewSSC(data, requireActivity(), this);
        binding.recyviewHorizontalFive.setAdapter(adapter_horizontalNew);
        binding.recyviewHorizontalFive.setHasFixedSize(true);
        binding.recyviewHorizontalFive.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    private void showProgressDialog() {
        progressDialog = new ACProgressFlower.Builder(requireActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }

    private void recyecleviewHorizontal(ModelHomeResponce categorytype) {
        AdapterHorizontal adapter_horizontal = new AdapterHorizontal(requireActivity(), categorytype, this);
        binding.recyviewHorizontal.setAdapter(adapter_horizontal);
        binding.recyviewHorizontal.setHasFixedSize(true);
        binding.recyviewHorizontal.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    private void recyecleviewHorizontalBpsc(ModelHomeResponce categorytype) {
        AdapterHorizontalBpsc adapter_horizontal = new AdapterHorizontalBpsc(requireActivity(), categorytype, this);
        binding.recyviewGridOne.setAdapter(adapter_horizontal);
        binding.recyviewGridOne.setHasFixedSize(true);
        binding.recyviewGridOne.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    private void getHomeData(Call<ModelHomeResponce> call) {
        showProgressDialog();
        call.enqueue(new Callback<ModelHomeResponce>() {
            @Override
            public void onResponse(Call<ModelHomeResponce> call, Response<ModelHomeResponce> response) {
                if (response.body() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.body().getCategory().getStatus()) {
                        categoryByProduct(response.body());
                    }
                    if (response.body().getSlider().getStatus()) {
                        imageSlider(response.body());
                    }
                    if (response.body().getUpscCategory().getStatus()) {
                        recyecleviewHorizontal(response.body());
                    }
                    if (response.body().getHomeMidBanner().getStatus()) {
                        getBigBanner(response);
                    }
                    if (response.body().getAllProduct().getStatus()) {
                        onViewGrid(response.body().getAllProduct().getData());
                    }
                    if (response.body().getBankCategory().getStatus()) {
                        onViewGridBank(response.body().getBankCategory().getData());
                    }
                    if (response.body().getHomeBanner2().getStatus()) {
                        getBigBannerNew(response);
                    }
                    if (response.body().getBpscCategory().getStatus()) {
                        recyecleviewHorizontalBpsc(response.body());
                    }
                    if (response.body().getSscCategory().getStatus()) {
                        onViewHorizontalssc(response.body().getSscCategory().getData());
                    }


                    Glide.with(requireActivity())
                            .load(IMAGE_BASE_URL + response.body().getHomeMidBanner().getData().get(0).getCimage())
                            .placeholder(R.drawable.image_search)
                            .error(R.drawable.image_not_found)
                            .into(binding.imageStrip);
                    progressDialog.dismiss();

                    Glide.with(requireActivity())
                            .load(IMAGE_BASE_URL + response.body().getHomeBanner3().getData().get(0).getCimage())
                            .placeholder(R.drawable.image_search)
                            .error(R.drawable.image_not_found)
                            .into(binding.imageStrip2);
                    progressDialog.dismiss();


                } else {
                    Toast.makeText(requireActivity(), "data not found", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);


                }
            }

            @Override
            public void onFailure(Call<ModelHomeResponce> call, Throwable t) {
                Toast.makeText(requireActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);



            }
        });
    }

    private void getBigBanner(Response<ModelHomeResponce> response) {
        Glide.with(requireActivity())
                .load(IMAGE_BASE_URL + response.body().getHomeMidBanner().getData().get(0).getMimage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(binding.imageBigbanner);
    }

    private void getBigBannerNew(Response<ModelHomeResponce> response) {
        Glide.with(requireActivity())
                .load(IMAGE_BASE_URL + response.body().getHomeBanner2().getData().get(0).getCimage())
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(binding.imageBigbanner1);
    }

    private void categoryByProduct(ModelHomeResponce body) {
        AdapterCircleHorizontal adapter_horizontalCircle = new AdapterCircleHorizontal(requireActivity(), body, this);
        binding.recyviewHorizontalCircle.setAdapter(adapter_horizontalCircle);
        binding.recyviewHorizontalCircle.setHasFixedSize(true);
        binding.recyviewHorizontalCircle.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }


    private void imageSlider(ModelHomeResponce bannerImage) {
        sliderView = binding.imageSlider;
        adapter = new slider_adapter(getContext(), this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        addNewItem(bannerImage, binding.getRoot());
        renewItems(bannerImage, binding.getRoot());
        removeLastItem(binding.getRoot());
    }


    public void renewItems(ModelHomeResponce bannerImage, View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < bannerImage.getSlider().getData().size(); i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl(IMAGE_BASE_URL + bannerImage.getSlider().getData().get(i).getImage());
            } else {
                sliderItem.setImageUrl(IMAGE_BASE_URL + bannerImage.getSlider().getData().get(i).getImage());
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        //if (adapter.getCount() - 1 >= 0)
        if (adapter.getCount() - 1 == 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(ModelHomeResponce bannerImage, View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl(IMAGE_BASE_URL + bannerImage.getSlider().getData().get(0).getImage());
        adapter.addItem(sliderItem);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onEvent(String id) {
        if (id != null) {
            startActivity(new Intent(getActivity(), ProductActivity.class).putExtra("cateID", id));

        }

    }


    @Override
    public void onItemClick(String productId) {
        if (productId != null && !(productId.isEmpty())) {
            startActivity(new Intent(requireActivity(), ProductDetailsActivity.class).putExtra("pid", productId));
        }


    }

    @Override
    public void onItemView(String productId) {
        startActivity(new Intent(getActivity(), AllProductActivity.class));

    }
}