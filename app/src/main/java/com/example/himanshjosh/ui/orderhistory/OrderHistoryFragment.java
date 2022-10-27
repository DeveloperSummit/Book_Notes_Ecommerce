package com.example.himanshjosh.ui.orderhistory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.himanshjosh.R;
import com.example.himanshjosh.databinding.FragmentOrderHistoryBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.ui.orderhistory.model.GetHistoryListModel;
import com.example.himanshjosh.ui.orderhistory.model.Product;
import com.example.himanshjosh.utlity.OnViewItem;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.example.himanshjosh.webview.WebViewActivity;

import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryFragment extends Fragment implements OnViewItem {

    private FragmentOrderHistoryBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HistoryAdpter cartAdapter;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        recyclerView = binding.recyOrderhistory;
        networkListener = RetrofitService.getClient().create(NetworkListener.class);

        getCartList();


        return binding.getRoot();
    }

    private void getCartList() {
        showProgressBar();
        Call<GetHistoryListModel> cartList = networkListener.orderHistory(SharedPrefrence.getStr(requireActivity(), "customerId"));
        cartList.enqueue(new Callback<GetHistoryListModel>() {
            @Override
            public void onResponse(Call<GetHistoryListModel> call, Response<GetHistoryListModel> response) {
                if (response.body().getStatus() && response.body().getProduct().size() > 0) {
                    setUpRecyclerView(response.body().getProduct());
                    progressDialog.dismiss();
                    binding.recyOrderhistory.setVisibility(View.VISIBLE);
                    binding.noDataFound.setVisibility(View.GONE);
                } else {
                    binding.recyOrderhistory.setVisibility(View.GONE);
                    binding.noDataFound.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();


                }


            }

            @Override
            public void onFailure(Call<GetHistoryListModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(requireActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.recyOrderhistory.setVisibility(View.GONE);
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

    private void setUpRecyclerView(List<Product> cartData) {
        if (cartData.size() > 0 || cartData != null) {
            cartAdapter = new HistoryAdpter(this,cartData, this);
            linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            binding.recyOrderhistory.setAdapter(cartAdapter);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        progressDialog = null;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {

            menu.findItem(R.id.action_cartItem).setVisible(false);
        }
    }


    @Override
    public void onItemClick(String productId) {
        Intent intent = new Intent(requireActivity(), WebViewActivity.class);
        intent.putExtra("doc_end", productId);
        startActivity(intent);
    }

    @Override
    public void onItemView(String productId) {

    }
}