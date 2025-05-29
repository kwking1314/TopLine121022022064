package com.example.topline121022022064.adapter;

import java.util.List;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.topline121022022064.R;
import com.example.topline121022022064.bean.ConstellationBean;

import com.bumptech.glide.Glide;
public class ChooseConstellaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<ConstellationBean> cbList;
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;
    public ChooseConstellaListAdapter(Context context) {
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void setData(List<ConstellationBean> cbList) {
        this.cbList = cbList;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.
                choose_constella_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        ConstellationBean bean = cbList.get(i);
        ((ViewHolder) holder).tv_contella_name.setText(bean.getName());
        ((ViewHolder) holder).tv_date.setText(bean.getDate());
        Glide
                .with(context)
                .load(bean.getImg())
                .error(R.mipmap.ic_launcher)
                .into(((ViewHolder) holder).iv_img);
        //将i保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(i);
    }
    @Override
    public int getItemCount() {
        return cbList == null ? 0 : cbList.size();
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_contella_name, tv_date;
        public ImageView iv_img;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_contella_name = (TextView) itemView.findViewById(R.id.tv_contella_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_constella_img);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
