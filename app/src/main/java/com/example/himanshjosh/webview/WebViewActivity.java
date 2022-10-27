package com.example.himanshjosh.webview;

import static com.example.himanshjosh.utlity.ConstantField.PDF_BASE_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.himanshjosh.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class WebViewActivity extends AppCompatActivity {

    private String TAG = "WebActivyt";
    private  ACProgressFlower progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String endpath = intent.getStringExtra("doc_end");
        ImageView button = findViewById(R.id.back_button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


        if (endpath.isEmpty() || endpath == null) {
            Toast.makeText(this, "PDF Not Found !", Toast.LENGTH_SHORT).show();

        } else {
            showProgressDialog();
            WebView webView = findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + PDF_BASE_URL + endpath);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(WebViewActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    private void showProgressDialog() {
        progressDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}