package com.example.himanshjosh.ui.cartitem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himanshjosh.R;
import com.example.himanshjosh.cart.adapter.CartAdapter;
import com.example.himanshjosh.databinding.FragmentSlideshowBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.order.order_placing;
import com.example.himanshjosh.ui.cartitem.model.CartDatum;
import com.example.himanshjosh.ui.cartitem.model.GetCartListModel;
import com.example.himanshjosh.ui.cartitem.model.get.ModelRemoveCartResponce;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.utlity.UtlityaFunction;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListFragment extends Fragment implements CartAdapter.OnClickUpdateValue {

    private FragmentSlideshowBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CartAdapter cartAdapter;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        recyclerView = binding.recyclerview;
        networkListener = RetrofitService.getClient().create(NetworkListener.class);

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), order_placing.class);
                startActivity(intent);

            }
        });

        getCartList();

        return binding.getRoot();
    }

    private void getCartList() {
        showProgressBar();
        Call<GetCartListModel> cartList = networkListener.getCartList(SharedPrefrence.getStr(requireActivity(), "customerId"));
        cartList.enqueue(new Callback<GetCartListModel>() {
            @Override
            public void onResponse(Call<GetCartListModel> call, Response<GetCartListModel> response) {
                if (response.body().getStatus() && response.body().getCartData().size() > 0) {

                    progressDialog.dismiss();
                    setUpRecyclerView(response.body().getCartData());
                    binding.layourtConst.setVisibility(View.VISIBLE);
                    binding.layoutButton.setVisibility(View.VISIBLE);
                    binding.noDataFound.setVisibility(View.GONE);

                } else {
                    progressDialog.dismiss();
                    setUpRecyclerView(new ArrayList<CartDatum>());
                    binding.layourtConst.setVisibility(View.GONE);
                    binding.layoutButton.setVisibility(View.GONE);
                    binding.noDataFound.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<GetCartListModel> call, Throwable t) {
                progressDialog.dismiss();
                binding.layourtConst.setVisibility(View.GONE);
                binding.layoutButton.setVisibility(View.GONE);
                binding.noDataFound.setVisibility(View.VISIBLE);

            }
        });
    }

    private void showProgressBar() {
        progressDialog = new ACProgressFlower.Builder(requireActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }

    private void setUpRecyclerView(List<CartDatum> cartData) {
        if (cartData.size() > 0 || cartData != null) {
            onDetailsUpdate(cartData);
            linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            cartAdapter = new CartAdapter(requireActivity(), cartData, this);
            binding.recyclerview.setAdapter(cartAdapter);
        }
    }

    private void onDetailsUpdate(List<CartDatum> cartData) {
        int total_price = 0;
        binding.totalProduct.setText("  Total Price (" + cartData.size() + " items)");
        for (CartDatum var : cartData) {
            total_price += Integer.parseInt(var.getPrice()) * Integer.parseInt(var.getQty());
        }
        binding.totalprice.setText(" ₹" + total_price);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        progressDialog = null;
    }


    @Override
    public void onUpdate(String value, int position, List<CartDatum> updatecartList) {
        onDetailsUpdate(updatecartList);
    }

    @Override
    public void onRemoveCart(int postion, String pid) {
        showProgressBar();
        Call<ModelRemoveCartResponce> cartList = networkListener.onRemoveCartProduct(pid);
        cartList.enqueue(new Callback<ModelRemoveCartResponce>() {
            @Override
            public void onResponse(Call<ModelRemoveCartResponce> call, Response<ModelRemoveCartResponce> response) {
                if (response.body().getStatus()) {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Item Remove Successfully !");
                    progressDialog.dismiss();
                    getCartList();

                } else {
                    UtlityaFunction.showSnackBar(binding.getRoot(), "Item  Not Removed");
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ModelRemoveCartResponce> call, Throwable t) {
                UtlityaFunction.showSnackBar(binding.getRoot(), "" + t.getMessage());
                progressDialog.dismiss();


            }
        });


    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {

            menu.findItem(R.id.action_cartItem).setVisible(false);
        }
    }


}