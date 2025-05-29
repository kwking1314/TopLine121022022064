package com.example.topline121022022064.adapter;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.topline121022022064.R;
import com.example.topline121022022064.bean.PythonBean;
import androidx.recyclerview.widget.RecyclerView;
public class PythonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PythonBean> pbl;
    public void setData(List<PythonBean> pbl) {
        this.pbl = pbl;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.python_list_item, viewGroup, false);
        PythonViewHolder viewHolder = new PythonViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int i) {
        final PythonBean bean = pbl.get(i);
        ((PythonViewHolder) holder).tv_address.setText(bean.getAddress());
        ((PythonViewHolder) holder).tv_content.setText(bean.getContent());
    }
    @Override
    public int getItemCount() {
        return pbl == null ? 0 : pbl.size();
    }
    public class PythonViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_address, tv_content;
        public ImageView iv_img;
        public PythonViewHolder(View itemView) {
            super(itemView);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_fire);
        }
    }
}

