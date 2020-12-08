package com.hfad.adlesson4;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements PopupMenu.OnMenuItemClickListener {

    private static final String TAGE = "tag";
    private static final String TAG = "tagg";
    public List<Title> list;
    public Context context;
    ItemClickListener listener;
    int position;
    String date;



    public MainAdapter(List<Title> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setElement(Title title, int position) {
        list.set(position, title);
        this.position = position;
        notifyItemChanged(position, title);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_item, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addApplication(Title title) {
        list.add(0,title);
        notifyDataSetChanged();
    }



    // ======================onMenuItem======================
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_pop_up:
                Log.d(TAG, "onMenuItemClick: action_pop_up");

                return true;

            case R.id.action_popup_delete:
                Log.d(TAG, "onMenuItemClick: action_popup_delete");
                list.remove(position);
                notifyDataSetChanged();
                return true;

            default:
                return false;

        }
    }

    // =============================class MainViewHolder==================================

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewForDate;
        TextView textView;
        Title title;
        ImageButton button;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.textView);
            textViewForDate = itemView.findViewById(R.id.textViewDate);

            button = itemView.findViewById(R.id.button);// added

            button.setOnClickListener(v -> showPopup(v));
        }

        public void onBind(Title title) {
            this.title = title;
            textView.setText(title.name);
            textViewForDate.setText(title.date);
        }

//        public void onDateBind(Title title){
//            this.title = title;
//            textViewForDate.setText(date);
//            notifyDataSetChanged();
//        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(title, getAdapterPosition());
            }
        }
    }

    // ==============================PopUp==========================================
    private void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.pop_up);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    public void setOnClick(ItemClickListener mListener) {
        this.listener = mListener;
    }

    //==============================Interface=======================================

    public interface ItemClickListener {
        void onItemClick(Title title, int pos);
    }
}