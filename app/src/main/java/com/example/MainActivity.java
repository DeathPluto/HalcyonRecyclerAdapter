package com.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.halcyon.HalcyonRecyclerAdapter;
import com.halcyon.HalcyonViewHolder;
import com.halcyon.RecyclerAdapterWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private RecyclerAdapterWrapper mAdapterWrapper;
    private HalcyonRecyclerAdapter mAdapter;

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
            option.content = "position" + i;
            option.choice = new Random().nextInt(4);
            options.add(option);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new MyAdapter(options, R.layout.item_recycler);
        mAdapterWrapper = new RecyclerAdapterWrapper(mAdapter);
        addHeader(null);
        mRecyclerView.setAdapter(mAdapterWrapper);
    }


    private void addHeader(String content) {
        View header = View.inflate(this, R.layout.header, null);
        if (!TextUtils.isEmpty(content)) {
            TextView textView = (TextView) header.findViewById(R.id.tv_content);
            textView.setText(content);
        }
        mAdapterWrapper.addHeaderView(header);
    }

    private void addFooter(String content) {
        View footer = View.inflate(this, R.layout.footer, null);
        if (!TextUtils.isEmpty(content)) {
            TextView textView = (TextView) footer.findViewById(R.id.tv_content);
            textView.setText(content);
        }
        mAdapterWrapper.addFooterView(footer);
    }


    public static class MyOption {
        public String content;
        public int choice;
    }

    class MyAdapter extends HalcyonRecyclerAdapter<MyOption> {

        public MyAdapter(@NonNull List<MyOption> dataList, int layoutId) {
            super(dataList, layoutId);
        }

        @Override
        public void onBindViewHolder(HalcyonViewHolder holder, int position) {
            MyOption item = getItem(position);
            holder.setText(R.id.tv_content, item.content);
            holder.setText(R.id.checkbox, String.valueOf(item.choice));
            holder.setCheckableId(R.id.checkbox);
            holder.setChecked(item.choice == 1);
        }
    }
}
