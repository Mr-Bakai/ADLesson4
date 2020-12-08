package com.hfad.adlesson4;import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    public static final String KEY = "key" ;
    private static final int REQUEST_CODE_2 = 2;
    private static final int REQUEST_CODE_3 = 3;
    private static final String TAAG = "taag";
    public RecyclerView recyclerView;
    private MainAdapter adapter;
    public List<Title> list;
    public static int REQUEST_CODE = 1;
    int position;
    private Button btnAdd;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Title("Item " + i + " Список заметок", temp));
        }

        adapter = new MainAdapter(list, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
                startActivityForResult(intent, REQUEST_CODE_2);
            }
        });
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
            (ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT |
                    ItemTouchHelper.LEFT){
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {

            int positionDrag = viewHolder.getAdapterPosition();
            int positionTarget = target.getAdapterPosition();
            Collections.swap(adapter.list, positionDrag, positionTarget);
            adapter.notifyItemMoved(positionDrag, positionTarget);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.list.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    // interface's method
    @Override
    public void onItemClick(Title title, int pos){
        this.position = pos;
        Intent intent = new Intent(this,ActivityTwo.class);
        intent.putExtra(KEY, title);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Title title = (Title) data.getSerializableExtra(ActivityTwo.KEY2);
            adapter.setElement(title, position);
        }

        if (requestCode == REQUEST_CODE_2 && resultCode == RESULT_OK){
            Title title = (Title) data.getSerializableExtra(ActivityTwo.KEY2);
            adapter.addApplication(title);
            adapter.notifyDataSetChanged();
        }
    }
}