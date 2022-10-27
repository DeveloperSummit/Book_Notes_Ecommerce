package com.example.himanshjosh.network;

import com.example.himanshjosh.loign.responce.getrequest.ModelLoginResponce;
import com.example.himanshjosh.loign.responce.getrequest.signup.ModelLoginResponceSignup;
import com.example.himanshjosh.order.model.ModelGetResponcePlaceOrder;
import com.example.himanshjosh.produts.model.get.ModelAddItamToCart;
import com.example.himanshjosh.produts.model.get.ProductDetails;
import com.example.himanshjosh.produts.model.get.allproduct.ModelGetResponceAllProduct;
import com.example.himanshjosh.produts.model.post.GetAllProductList;
import com.example.himanshjosh.ui.home.main.homemodel.HomeDataModel;
import com.example.himanshjosh.ui.home.main.homemodel.getresponce.ModelHomeResponce;
import com.example.himanshjosh.ui.home.model.get.ModelGetProductByCate;
import com.example.himanshjosh.ui.orderhistory.model.GetHistoryListModel;
import com.example.himanshjosh.ui.cartitem.model.GetCartListModel;
import com.example.himanshjosh.ui.cartitem.model.get.ModelRemoveCartResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkListener {

    @FormUrlEncoded
    @POST("login.php")
    public Call<ModelLoginResponce> loginUser(@Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("register.php")
    public Call<ModelLoginResponceSignup> registerUser(@Field("fname") String name, @Field("lname") String lastname, @Field("email") String email, @Field("mobile") String phone, @Field("password") String password, @Field("address") String address);

    @GET("homePageData.php")
    public Call<ModelHomeResponce> getHomeDetails();

    @FormUrlEncoded
    @POST("productsByCategory.php")
    public Call<ModelGetProductByCate> getProductByCate(@Field("cid") String keyValue);


    @FormUrlEncoded
    @POST("productDetail.php")
    public Call<ProductDetails> getProductDetails(@Field("pid") String keyValue);


    @FormUrlEncoded
    @POST("addtocart.php")
    public Call<ModelAddItamToCart> addItemToCart(@Field("uid") String uid, @Field("pid") String pid, @Field("qty") String qty, @Field("price") String price);


    @GET("allProducts.php")
    public Call<ModelGetResponceAllProduct> getProductList();


    @FormUrlEncoded
    @POST("cartList.php")
    public Call<GetCartListModel> getCartList(@Field("uid") String uid);


    @FormUrlEncoded
    @POST("cartRemove.php")
    public Call<ModelRemoveCartResponce> onRemoveCartProduct(@Field("pid") String pid);

    @FormUrlEncoded
    @POST("addAddress.php")
    public Call<ModelRemoveCartResponce> onAddAddress(@Field("uid") String uid,@Field("address") String address,@Field("city") String city,@Field("state") String state,@Field("country") String country);

    @FormUrlEncoded
    @POST("orderHistory.php")
    public Call<GetHistoryListModel> orderHistory(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("placeOrder.php")
    public Call<ModelGetResponcePlaceOrder> onPlaceHolder(@Field("uid") String uid,@Field("txnid") String txnid);




}
