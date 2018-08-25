package edu.sit.great.kagapp;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NavigateActivity extends AppCompatActivity {

    String[] values = { "Onsen", "Room", "Ping Pong", "Mahjong", "Arcade", "Cafe", "Shop" };

    ListView listView;

    SearchView searchView;

    List<SearchAdapter> rowItems;

    ArrayAdapter<SearchAdapter> arrayAdapter;

    int[] imgid = {
            R.drawable.onsen,
            R.drawable.bed,
            R.drawable.pingpong,
            R.drawable.pingpong,
            R.drawable.arcade,
            R.drawable.cafe,
            R.drawable.shopping,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        final SearchAdapter adapter = new SearchAdapter(this, getSearch());
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);

        searchView = (SearchView)findViewById(R.id.editText);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                adapter.getFilter().filter(text);
                //arrayAdapter.getFilter().filter(text);

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), Navigate2Activity.class);
                intent.putExtra("location", (String) listView.getItemAtPosition(i));
                startActivity(intent);
            }
        });

    }

    private ArrayList<search> getSearch()
    {
        ArrayList<search> eachSearch=new ArrayList<search>();
        search p;

        for(int i=0;i<values.length;i++)
        {
            p=new search(values[i], imgid[i]);
            eachSearch.add(p);
        }

        return eachSearch;
    }


}
