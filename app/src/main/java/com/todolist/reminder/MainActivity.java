package com.todolist.reminder;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
  private android.support.v7.widget.Toolbar mToolbar;
  public static int count=1;
  private ListView lv;
 static ArrayList<Model> itemModel;
  static CustomAdapter adapter;
  int hour,minute;
  private static int shot=1;
  private TextView textView;
  private static int TIMEOUT=2000;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle("INSTA TASK");
        mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setLogoDescription("MANAGE YOUR TASKS");
        setSupportActionBar(mToolbar);
        //Handler for splash screen


        lv=(ListView)findViewById(R.id.mylistview);
        textView=(TextView)findViewById(R.id.emptyElement);

        SharedPreferences appSharedPreference = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPreference.getString("anjan",null);
        Type type = new TypeToken<ArrayList<Model>>() {}.getType();
        itemModel = gson.fromJson(json, type);
        if(itemModel==null){
            itemModel=new ArrayList<>();
        }

        adapter=new CustomAdapter(this,itemModel);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        lv.setEmptyView(findViewById(R.id.emptyElement));


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                }).setNegativeButton("No", null).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.action_favorite){
            FragmentManager fm=getFragmentManager();
            Bundle args=new Bundle();
            args.putInt("value",100);
            drawingsurface drawingsurface=new drawingsurface();
            drawingsurface.setArguments(args);
            drawingsurface.show(fm,"SET TASK");
            return true;
        }
        if(id==R.id.settings){
            FragmentManager fm=getFragmentManager();
            settingsdialog settingsdialog=new settingsdialog();
            settingsdialog.show(fm,"SETTINGS");
            return true;
        }
        if(id==R.id.share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "http://play.google.com/store/apps/details?id=anjanproduction.com.projectxyz";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        }

        return super.onOptionsItemSelected(item);
    }
}
