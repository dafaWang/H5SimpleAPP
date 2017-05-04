package app.par.com.h5app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    public static final String JS_NAME = "android";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        tv = (TextView) findViewById(R.id.textview);

        mWebView.loadUrl("file:///android_asset/test.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("baidu")) {
                    Toast.makeText(MainActivity.this, "拦截界面跳转成功", Toast.LENGTH_SHORT).show();
                    tv.setText("拦截到的url为 : " + url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JsInteration(), JS_NAME);
    }

    public void useJs(View view) {
        //使用js中的方法
        mWebView.evaluateJavascript("sum(30,50)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
//                Toast.makeText(MainActivity.this, "JS代码中sum()方法计算30+50的结果为 : "+s, Toast.LENGTH_SHORT).show();
                tv.setText("JS代码中sum()方法计算30+50的结果为 : " + s);
            }
        });
    }

    public class JsInteration {
        @JavascriptInterface
        public String show() {
            return "成功调用android中的方法";
        }
    }
}
