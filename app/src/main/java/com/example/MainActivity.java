package com.example;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.halcyon.HalcyonRecyclerAdapter;
import com.halcyon.HalcyonViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        init();
    }



    private void init() {
        List<MyOption> options = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MyOption option = new MyOption();
            option.content = "position"+i;
            option.choice = new Random().nextInt(4);
            options.add(option);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View itemView = View.inflate(this,R.layout.item_recycler,null);
        mRecyclerView.setAdapter(new MyAdapter(options,itemView));
    }

    public static class MyOption{
        public String content;
        public int choice;
    }

    class MyAdapter extends HalcyonRecyclerAdapter<MyOption>{

        public MyAdapter(@NonNull List<MyOption> dataList, View itemView) {
            super(dataList, itemView);
        }

        @Override
        public void onBindViewHolder(HalcyonViewHolder holder, int position) {
            MyOption item = getItem(position);
            holder.setText(R.id.tv_content,item.content);
            holder.setText(R.id.checkbox,String.valueOf(item.choice));
            holder.setCheckableId(R.id.checkbox);
            holder.setChecked(item.choice == 1);
        }
    }
}
