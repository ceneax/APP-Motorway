package ceneax.app.motorway.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import ceneax.app.motorway.R;
import ceneax.app.motorway.widget.TopToast;

public class DialogUtil {

    public static void showBottomList(Context context, String title, List<String> datas, OnSelectedListener listener) {
        if (datas == null || datas.size() <= 0)
            return;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_list, null, false);
        TextView titleText = view.findViewById(R.id.dialog_bottom_list_title);
        RecyclerView recyclerView = view.findViewById(R.id.dialog_bottom_list_list);
        CommonAdapter<String> adapter;
        BottomSheetDialog dialog = new BottomSheetDialog(context);

        titleText.setText(title);

        adapter = new CommonAdapter<String>(context, android.R.layout.simple_selectable_list_item, datas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(android.R.id.text1, s);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (listener != null) {
                    listener.onSelected(datas.get(i), i);
                }
                dialog.dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        dialog.setContentView(view);
        dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackground(context.getDrawable(R.drawable.shape_layout_corner));
        dialog.show();
    }

    public static void showTopToast(Activity activity, String content) {
        new TopToast(activity, content);
    }

    public interface OnSelectedListener {
        void onSelected(String s, int position);
    }

}