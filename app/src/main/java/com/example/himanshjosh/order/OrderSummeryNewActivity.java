package com.example.himanshjosh.order;

import static com.example.himanshjosh.utlity.ConstantField.PAYMENT_KEY;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himanshjosh.MainActivity;
import com.example.himanshjosh.R;
import com.example.himanshjosh.cart.adapter.CartAdapter;
import com.example.himanshjosh.databinding.ActivityOrderSummeryNewBinding;
import com.example.himanshjosh.network.NetworkListener;
import com.example.himanshjosh.network.RetrofitService;
import com.example.himanshjosh.order.adpapter.OrderSummeryCartAdapter;
import com.example.himanshjosh.order.model.ModelGetResponcePlaceOrder;
import com.example.himanshjosh.ui.cartitem.model.CartDatum;
import com.example.himanshjosh.ui.cartitem.model.GetCartListModel;
import com.example.himanshjosh.utlity.SharedPrefrence;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummeryNewActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityOrderSummeryNewBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderSummeryCartAdapter cartAdapter;
    private NetworkListener networkListener;
    private ACProgressFlower progressDialog;
    private static final String TAG = "PaymentActivity";
    private int orignalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderSummeryNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Checkout.preload(getApplicationContext());
        networkListener = RetrofitService.getClient().create(NetworkListener.class);

        recyclerView = binding.recyviewCartItem;
        binding.username.setText(SharedPrefrence.getStr(this, "UserName"));
        binding.userMobile.setText(SharedPrefrence.getStr(this, "UserMob"));
        binding.userAddresh.setText(SharedPrefrence.getStr(this, "Address"));

        binding.seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.icon_color_700, getTheme()), PorterDuff.Mode.SRC_ATOP);
        binding.seekbar.getThumb().setColorFilter(getResources().getColor(R.color.icon_color_700, getTheme()), PorterDuff.Mode.SRC_ATOP);
        binding.seekbar.setProgress(48);
        getCartList();

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* startActivity(new Intent(OrderSummeryNewActivity.this,PaymentActivity.class));
                finish();*/
                if (orignalPrice > 0) {
                    startPayment(orignalPrice);
                }
            }
        });

    }


    private void getCartList() {
        showProgressBar();
        Call<GetCartListModel> cartList = networkListener.getCartList(SharedPrefrence.getStr(OrderSummeryNewActivity.this, "customerId"));
        cartList.enqueue(new Callback<GetCartListModel>() {
            @Override
            public void onResponse(Call<GetCartListModel> call, Response<GetCartListModel> response) {
                if (response.body().getStatus() && response.body().getCartData().size() > 0) {
                    progressDialog.dismiss();
                    setUpRecyclerView(response.body().getCartData());


                } else {
                    progressDialog.dismiss();
                    setUpRecyclerView(new ArrayList<CartDatum>());

                }


            }

            @Override
            public void onFailure(Call<GetCartListModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderSummeryNewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }


    private void showProgressBar() {
        progressDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }

    private void setUpRecyclerView(List<CartDatum> cartData) {
        int totalPrice = 0;
        if (cartData != null && cartData.size() > 0) {
            cartAdapter = new OrderSummeryCartAdapter(this, cartData);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(cartAdapter);
            for (CartDatum cart : cartData) {
                totalPrice += Integer.parseInt(cart.getTotal());
            }
            orignalPrice = totalPrice;
            binding.textviewTotalPrice.setText("₹" + totalPrice);
        }


    }

    public void startPayment(int orignalPrice) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(PAYMENT_KEY);

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.icon_launcher);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", SharedPrefrence.getStr(this, "UserName"));
            options.put("description", SharedPrefrence.getStr(this, "customerId"));
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", (orignalPrice * 100));//pass amount in currency subunits
            options.put("prefill.email", SharedPrefrence.getStr(this, "UserEmail"));
            options.put("prefill.contact", SharedPrefrence.getStr(this, "UserMob"));
            /*JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);*/

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String txnid) {
        showProgressBar();
        Toast.makeText(this, "Payment Successfully !", Toast.LENGTH_SHORT).show();
        Call<ModelGetResponcePlaceOrder> event = networkListener.onPlaceHolder(SharedPrefrence.getStr(this, "customerId"), txnid);
        event.enqueue(new Callback<ModelGetResponcePlaceOrder>() {
            @Override
            public void onResponse(Call<ModelGetResponcePlaceOrder> call, Response<ModelGetResponcePlaceOrder> response) {
                if (response.body().getStatus()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(OrderSummeryNewActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(OrderSummeryNewActivity.this, "Failed to Order Placed !", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<ModelGetResponcePlaceOrder> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderSummeryNewActivity.this, "Payment Not Successfully !", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Not Successfully !", Toast.LENGTH_SHORT).show();

    }


}