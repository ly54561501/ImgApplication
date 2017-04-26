package org.lyimg.com.imgapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.lyimg.com.imgapplication.R;
import org.lyimg.com.imgapplication.bean.ImgType;

import java.util.List;

/**
 * Created by liuyong on 2017/4/19.
 */

public class ImgRecAdapter extends RecyclerView.Adapter<ImgRecAdapter.MyViewHolder> {

    Context context;
    List<ImgType> list;

    public ImgRecAdapter(Context context, List<ImgType> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_imgs, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(list.get(position).title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView img;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_name);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
