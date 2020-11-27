package kr.co.munjanara.examwebview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mAddressEdit;
    Button mMoveButton;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddressEdit = findViewById(R.id.et_address);
        mMoveButton = findViewById(R.id.btn_move);
        mWebView = findViewById(R.id.web_view);

        WebSettings  webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(MainActivity.this, "로딩 끝", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        mAddressEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if( actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mMoveButton.callOnClick();

                    // 키보드 숨기기
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return  true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_action_backward:
                if(mWebView.canGoBack())
                    mWebView.goBack();
                return  true;
            case R.id.menu_action_forward:
                if(mWebView.canGoForward())
                    mWebView.goForward();
            case R.id.menu_action_refresh:
                mWebView.reload();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        String address = mAddressEdit.getText().toString();
        if( address.startsWith("https://") == false) {
            address = "https://" + address;
        }
        mWebView.loadUrl(address);
    }

    @Override
    public void onBackPressed() {
        if( mWebView.canGoBack()) {
            mWebView.goBack();
        }
        else
            super.onBackPressed();
    }
}