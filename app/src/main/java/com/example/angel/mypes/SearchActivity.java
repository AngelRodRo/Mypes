package com.example.angel.mypes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

import org.w3c.dom.Text;

import java.util.Locale;

public class SearchActivity extends SherlockFragmentActivity {

    ActionBar mActionBar;
    ViewPager mPager;
    ActionBar.Tab tab;
    EditText editsearch;
    Button btnSearch;

    SearchManager searchManager;
    SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());

        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mPager = (ViewPager) findViewById(R.id.pager);


        FragmentManager fm = getSupportFragmentManager();

        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        };

        mPager.setOnPageChangeListener(ViewPagerListener);

        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);

        mPager.setAdapter(viewpageradapter);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };

        LayoutInflater inflater1 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater2 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater3 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater4 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater5 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater6 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout customLinear1 = (LinearLayout) inflater1.inflate(R.layout.customtextview,null);

        TextView textView1 = (TextView)  customLinear1.findViewById(R.id.textView2);
        textView1.setText("Restaurante");
        textView1.setTextColor(Color.WHITE);

        ImageView imageView1 = (ImageView) customLinear1.findViewById(R.id.imageView2);
        imageView1.setImageResource(R.drawable.restaurant);

        tab = mActionBar.newTab().setCustomView(customLinear1).setTabListener(tabListener);
        mActionBar.addTab(tab);

        LinearLayout customLinear2 = (LinearLayout) inflater2.inflate(R.layout.customtextview,null);

        TextView textView2 = (TextView)  customLinear2.findViewById(R.id.textView2);
        textView2.setText("Vestimenta");
        textView2.setTextColor(Color.WHITE);

        ImageView imageView2 = (ImageView) customLinear2.findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.shirt);

        tab = mActionBar.newTab().setCustomView(customLinear2).setTabListener(tabListener);
        mActionBar.addTab(tab);

        LinearLayout customLinear3 = (LinearLayout) inflater3.inflate(R.layout.customtextview,null);

        TextView textView3 = (TextView)  customLinear3.findViewById(R.id.textView2);
        textView3.setText("Diversion Nocturna");
        textView3.setTextColor(Color.WHITE);

        ImageView imageView3 = (ImageView) customLinear3.findViewById(R.id.imageView2);
        imageView3.setImageResource(R.drawable.bar);

        tab = mActionBar.newTab().setCustomView(customLinear3).setTabListener(tabListener);
        mActionBar.addTab(tab);

        LinearLayout customLinear4 = (LinearLayout) inflater4.inflate(R.layout.customtextview,null);

        TextView textView4 = (TextView)  customLinear4.findViewById(R.id.textView2);
        textView4.setText("Salud");
        textView4.setTextColor(Color.WHITE);

        ImageView imageView4 = (ImageView) customLinear4.findViewById(R.id.imageView2);
        imageView4.setImageResource(R.drawable.clinic);

        tab = mActionBar.newTab().setCustomView(customLinear4).setTabListener(tabListener);
        mActionBar.addTab(tab);

        LinearLayout customLinear5 = (LinearLayout) inflater5.inflate(R.layout.customtextview,null);

        TextView textView5 = (TextView)  customLinear5.findViewById(R.id.textView2);
        textView5.setText("Hospedaje");
        textView5.setTextColor(Color.WHITE);

        ImageView imageView5 = (ImageView) customLinear5.findViewById(R.id.imageView2);
        imageView5.setImageResource(R.drawable.clinic);

        tab = mActionBar.newTab().setCustomView(customLinear5).setTabListener(tabListener);
        mActionBar.addTab(tab);

        LinearLayout customLinear6 = (LinearLayout) inflater6.inflate(R.layout.customtextview,null);

        TextView textView6 = (TextView)  customLinear6.findViewById(R.id.textView2);
        textView6.setText("Comercios");
        textView6.setTextColor(Color.WHITE);

        ImageView imageView6 = (ImageView) customLinear6.findViewById(R.id.imageView2);
        imageView6.setImageResource(R.drawable.clinic);

        tab = mActionBar.newTab().setCustomView(customLinear6).setTabListener(tabListener);
        mActionBar.addTab(tab);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.menu_search, menu);


        //Set searchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        // Show the settings menu item in menu.xml
        MenuItem menuSettings = menu.findItem(R.id.menu_settings);

        // Capture menu item clicks
        menuSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                // Do something here
                Toast.makeText(getApplicationContext(), "Nothing here!",
                        Toast.LENGTH_LONG).show();
                return false;
            }

        });
        return true;
    }

/*    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            String text = editsearch.getText().toString()
                    .toLowerCase(Locale.getDefault());
            //adapter.filter(text);
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub

        }

    };*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);

    }

    private void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String queryStr)
    {
        Intent i = new Intent(SearchActivity.this,ListMypeActivity.class);
        i.putExtra("query",queryStr);
        startActivity(i);

    }
}
