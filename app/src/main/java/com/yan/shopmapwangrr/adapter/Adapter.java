package com.yan.shopmapwangrr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yan.shopmapwangrr.R;
import com.yan.shopmapwangrr.bean.Bean;
import com.yan.shopmapwangrr.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by dell on 2017/2/25.
 */

public class Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Bean.DataBeanHeadline> list;
    private final DisplayImageOptions option;

    public Adapter(List<Bean.DataBeanHeadline> list, Context context) {
        this.list = list;
        this.context = context;
        option = ImageLoaderUtil.getOption
                (R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.
                        ic_launcher, new FadeInBitmapDisplayer(100));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        //ImageLoader.getInstance().displayImage(list.get(position).getImg(),holder1.imageView,option);
        Glide.with(context).load(list.get(position).getImg()).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder1.imageView);
        holder1.ageTv.setText(list.get(position).getUserAge()+"");
        holder1.zhiTv.setText(list.get(position).getOccupation());
        holder1.desTv.setText(list.get(position).getIntroduction());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView ageTv,zhiTv,desTv;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);
            ageTv = (TextView) itemView.findViewById(R.id.ageTv);
            zhiTv = (TextView) itemView.findViewById(R.id.zhiTv);
            desTv = (TextView) itemView.findViewById(R.id.desTv);
        }
    }
}
