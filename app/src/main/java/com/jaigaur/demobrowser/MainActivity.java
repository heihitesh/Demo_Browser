package com.jaigaur.demobrowser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Hitesh Verma on 6/7/2015.
 */

//$$$ We need to get permission to use the Internet for that we need to declare it in the Manifest  $$$//
//Android uses its own defalut intent to open links to open our llink in our app we need to override it
//to open the link in our app we need to override that intent that is happenning by setting up the web Client
public class MainActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener, SlidingDrawer.OnDrawerOpenListener, SlidingDrawer.OnDrawerCloseListener {
    WebView Bro, Bro2, Bro3;
    EditText URL;
    SlidingDrawer Slide;
    Button go, back, refresh, forward, clearHistory, exit;
    TabHost th;
    TextView ShowResults;
    Button Handle; //this the the button or image of the handle slider
    Button FaceBook, Yahoo;

    //for the history
    String history = "Empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.simplebrowser);
        initilize();
        Bro = (WebView) findViewById(R.id.WebView);
        Bro2 = (WebView) findViewById(R.id.WebView2);

        initilizeBrowser();
        initilizeBrowser2();


    }

    private void initilizeBrowser2() {
        Bro2.getSettings().setJavaScriptEnabled(true);
        // we have to enable javaScript to view webpages such as Youtube.com and other
        Bro2.getSettings().setLoadWithOverviewMode(true); // completely zoomed out
        Bro2.getSettings().setUseWideViewPort(true);  // Normal desktop view

        try { // it might throw an exception if the browser is not able to open a webpage
            Bro2.loadUrl("http://www.google.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bro2.setWebViewClient(new ourViewClent());
        //we need to create a client to handle it anotehr class
    }


    private void initilizeBrowser() {
        Bro.getSettings().setJavaScriptEnabled(true);
        // we have to enable javaScript to view webpages such as Youtube.com and other
        Bro.getSettings().setLoadWithOverviewMode(true); // completely zoomed out
        Bro.getSettings().setUseWideViewPort(true);  // Normal desktop view

        try { // it might throw an exception if the browser is not able to open a webpage
            Bro.loadUrl("http://www.google.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bro.setWebViewClient(new ourViewClent());
        //we need to create a client to handle it anotehr class
    }


    private void initilize() {
        //Browser = (WebView) findViewById(R.id.WebView);

        go = (Button) findViewById(R.id.bGO); // will update my data Base
        URL = (EditText) findViewById(R.id.Url); // this will be stored in the database
        back = (Button) findViewById(R.id.bBack);
        refresh = (Button) findViewById(R.id.bRefresh);
        forward = (Button) findViewById(R.id.bForward);
        exit = (Button) findViewById(R.id.bExit);
        clearHistory = (Button) findViewById(R.id.bClearHistory); // will view my database
        go.setOnClickListener(this);
        back.setOnClickListener(this);
        refresh.setOnClickListener(this);
        forward.setOnClickListener(this);
        exit.setOnClickListener(this);
        clearHistory.setOnClickListener(this);

        URL.setOnEditorActionListener(this);
        //if we want that when we click the enter button in the keyboard it will click the go button or load the page

        Slide = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        Slide.setOnDrawerOpenListener(this);
        Slide.setOnDrawerCloseListener(this);
        Handle = (Button) findViewById(R.id.handle);
        FaceBook = (Button) findViewById(R.id.bFacebook);
        Yahoo = (Button) findViewById(R.id.bYahoo);
        FaceBook.setOnClickListener(this);
        Yahoo.setOnClickListener(this);

        th = (TabHost) findViewById(R.id.tabHost);


        th.setup(); //set the basic tabs
        //now we will organise it
        TabHost.TabSpec specs = th.newTabSpec("tag1"); //parm does  not matter but we need to initalize
        specs.setContent(R.id.tab1); // tab to be edit
        specs.setIndicator("TAB 1"); //Name on the Tab
        th.addTab(specs); // add the tab to the view

        //now the tab 2
        specs = th.newTabSpec("tag2"); //parm does  not matter but we need to initalize
        specs.setContent(R.id.tab2); // tab to be edit
        specs.setIndicator("TAB 2"); //Name on the Tab
        th.addTab(specs); // add the tab to the view

        specs = th.newTabSpec("tag3"); //parm does  not matter but we need to initalize
        specs.setContent(R.id.New_Tab); // tab to be edit
        specs.setIndicator("TAB 3"); //Name on the Tab
        th.addTab(specs); // add the tab to the view




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bGO:
                String theWebsite = URL.getText().toString();
                Bro.loadUrl("http://www." + theWebsite);
                //When we want to close the keyboard when we click the go button
                // this will just close the keyboard

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //this will hide our keyboard
                imm.hideSoftInputFromWindow(URL.getWindowToken(), 0);//first param URL and get the WIndowToken
                //Secnd param is default

                //if go button is pressed history is created ..therfore change the icon

                history = "Full";

                clearHistory.setBackgroundResource(R.drawable.history_notclicked);

                //THIS WILL GET THE CURRENT TIME OF THE SYSTEM
                DateFormat df = new SimpleDateFormat("---> d MMM ''yy, h:mm a");
                String date = df.format(Calendar.getInstance().getTime());


                /// THIS IS SQL CONCEPT ///

                boolean didItWorked = true;

                try {
                    String urldata = URL.getText().toString(); //getting the text or data

                    HistoryDataBase entry = new HistoryDataBase(MainActivity.this); //passing this class context to HotOrNot,java
                    entry.open();
                    entry.createEntry(urldata, date);// timedata);
                    entry.close();
                } catch (Exception e) {
                    didItWorked = false;
                    //Making a Dialog Box
                    Dialog d = new Dialog(this);
                    d.setTitle("Exception Caght");

                    String error = e.toString();

                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                } finally {
                    if (didItWorked) {
                        Toast.makeText(getBaseContext(),"Opening :" +theWebsite ,Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.bBack:
                if (Bro.canGoBack()) { //this will alow that can it go back .. canGoBack is
                    // a boolean type ..it check
                    Bro.goBack();

                    StringBuffer sb = new StringBuffer(Bro.getOriginalUrl()); //get the URL of the going page
                    int start = sb.indexOf(".");
                    int stop = sb.indexOf(".", (start + 1));


                    Toast.makeText(this, "Gooing Backward to: " + sb.substring(start, stop), Toast.LENGTH_SHORT).show(); // this change the text from http://www.facebook.com to facebook  !!
                    InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //this will hide our keyboard
                    imm2.hideSoftInputFromWindow(URL.getWindowToken(), 0);//first param URL and get the WIndowToken
                    //Secnd param is default
                } else {
                    Toast.makeText(this, "Can't go Back!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bRefresh:
                Bro.reload();
                Toast.makeText(this, "Page Reloaded", Toast.LENGTH_SHORT).show();
                break;

            case R.id.bForward:
                if (Bro.canGoForward()) { //this will alow that can it go back .. canGoForward is
                    // a boolean type ..it check go
                    Bro.goForward();
                    StringBuffer sb = new StringBuffer(Bro.getOriginalUrl()); //get the URL of the going page
                    int start = sb.indexOf(".");
                    int stop = sb.indexOf(".", (start + 1));

                    Toast.makeText(this, "Going Forward to :" + sb.substring(start, stop), Toast.LENGTH_SHORT).show();
                    InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //this will hide our keyboard
                    imm3.hideSoftInputFromWindow(URL.getWindowToken(), 0);//first param URL and get the WIndowToken
                    //Secnd param is default
                } else {
                    Toast.makeText(this, "Can't go Forward !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bClearHistory:
                if (history.equals("Full")) {
                    Bro.clearHistory();
                    Toast.makeText(this, "History Cleared", Toast.LENGTH_SHORT).show();
                    history = "Empty";
                    clearHistory.setBackgroundResource(R.drawable.history_clicked);

                } else {
                    Toast.makeText(this, "EMPTY!", Toast.LENGTH_SHORT).show();
                }

                ///THIS IS SQL CONCEPT///
                //this will open the SQLView.java
                Intent i2 = new Intent(MainActivity.this,hitSQL_View.class);
                startActivity(i2);


                break;

            case R.id.bExit:
                // finish();
                // to open Exit dialog
                Intent i = new Intent("com.itshiteshverma.travis01.Exit"); //this will start Exit.java
                startActivity(i);
                break;

            //slider buttons
            case R.id.bFacebook:
                Slide.close();
                history = "Full";
                clearHistory.setBackgroundResource(R.drawable.history_notclicked);
                Bro.loadUrl("http://www.facebook.com");
                Toast.makeText(this, " Opening FaceBook ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bYahoo:
                Slide.close();
                Bro.loadUrl("http://www.yahoo.com");
                history = "Full";
                clearHistory.setBackgroundResource(R.drawable.history_notclicked);
                Toast.makeText(this, " Opening Yahoo ", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        TextView hit = (TextView) v;
        if (actionId == EditorInfo.IME_ACTION_GO) {


            String theWebsite = URL.getText().toString();
            Bro.loadUrl("http://www." + theWebsite);
            //When we want to close the keyboard when we click the go button
            // this will just close the keyboard

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //this will hide our keyboard
            imm.hideSoftInputFromWindow(URL.getWindowToken(), 0);//first param URL and get the WIndowToken
            //Secnd param is default

            //if go button is pressed history is created ..therfore change the icon

            history = "Full";

            clearHistory.setBackgroundResource(R.drawable.history_notclicked);

            //THIS WILL GET THE CURRENT TIME OF THE SYSTEM
            DateFormat df = new SimpleDateFormat("---> d MMM ''yy, h:mm a");
            String date = df.format(Calendar.getInstance().getTime());


            /// THIS IS SQL CONCEPT ///

            boolean didItWorked = true;

            try {
                String urldata = URL.getText().toString(); //getting the text or data

                HistoryDataBase entry = new HistoryDataBase(MainActivity.this); //passing this class context to HotOrNot,java
                entry.open();
                entry.createEntry(urldata, date);// timedata);
                entry.close();
            } catch (Exception e) {
                didItWorked = false;
                //Making a Dialog Box
                Dialog d = new Dialog(this);
                d.setTitle("Exception Caght");

                String error = e.toString();

                TextView tv = new TextView(this);
                tv.setText(error);
                d.setContentView(tv);
                d.show();
            } finally {
                if (didItWorked) {
                    //Making a Dialog Box
                    Dialog d = new Dialog(this);
                    d.setTitle("It WORKED !!!");

                    TextView tv = new TextView(this);
                    tv.setText("Success");

                    d.setContentView(tv);
                    d.show();
                } else {
                    Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
                }
            }


            return true;
        }
        return false;
    }

    @Override
    public void onDrawerOpened() {
        //This will hide the behind actvity because its a frameLayout and need not to used
        Bro.setVisibility(Bro.GONE);
        back.setVisibility(back.GONE);
        go.setVisibility(go.GONE);
        refresh.setVisibility(refresh.GONE);
        forward.setVisibility(forward.GONE);
        clearHistory.setVisibility(clearHistory.GONE);
        exit.setVisibility(exit.GONE);
        URL.setVisibility(URL.GONE);

        //This gona change the icon of the Handler
        Handle.setBackgroundResource(R.drawable.slidere_clicked);
    }

    @Override
    public void onDrawerClosed() {
        Bro.setVisibility(Bro.VISIBLE);
        back.setVisibility(back.VISIBLE);
        go.setVisibility(go.VISIBLE);
        refresh.setVisibility(refresh.VISIBLE);
        forward.setVisibility(forward.VISIBLE);
        clearHistory.setVisibility(clearHistory.VISIBLE);
        exit.setVisibility(exit.VISIBLE);
        URL.setVisibility(URL.VISIBLE);

        //This gona change the icon of the Handler
        Handle.setBackgroundResource(R.drawable.sliderhandle_notclicked);
    }

    @Override // this method gona kill all the activity and force close the app
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}
