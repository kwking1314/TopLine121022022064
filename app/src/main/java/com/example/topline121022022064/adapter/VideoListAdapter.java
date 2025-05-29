package com.example.topline121022022064.adapter;
import java.io.Serializable;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.topline121022022064.R;
import com.example.topline121022022064.bean.VideoBean;
import android.content.Context;
import com.example.topline121022022064.activity.VideoDetailActivity;
public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<VideoBean> videoList;
    private Context context;
    public VideoListAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<VideoBean> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int
            viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.
                layout.video_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        final VideoBean bean = videoList.get(i);
        Glide
                .with(context)
                .load(bean.getImg())
                .error(R.mipmap.ic_launcher)
                .into(((ViewHolder) holder).iv_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("intro", bean.getIntro());
                intent.putExtra("videoDetailList", (Serializable) bean.getVideoDetailList());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_img;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img_round);
        }
    }
}
