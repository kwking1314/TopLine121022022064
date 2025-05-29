package com.example.topline121022022064.fragment;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.topline121022022064.bean.VideoBean;
import com.itheima.PullToRefreshView;
import com.example.topline121022022064.view.WrapRecyclerView;
import com.example.topline121022022064.utils.JsonParse;
import com.example.topline121022022064.utils.Contanst;
import com.example.topline121022022064.adapter.VideoListAdapter;
import com.example.topline121022022064.R;
public class VideoFragment extends Fragment {
    private PullToRefreshView mPullToRefreshView;
    private WrapRecyclerView recycleView;
    public static final int REFRESH_DELAY = 1000;
    public static final int MSG_VIDEO_OK = 1; //获取数据
    private MHandler mHandler;                   //事件捕获
    private VideoListAdapter adapter;
    public VideoFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHandler = new MHandler();
        initData();
        View view = initView(inflater, container);
        return view;
    }
    private View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        recycleView = (WrapRecyclerView) view.findViewById(R.id.recycler_view);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VideoListAdapter(getActivity());
        recycleView.setAdapter(adapter);
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.
                pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.
                OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        initData();
                    }
                }, REFRESH_DELAY);
            }
        });
        return view;
    }
    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Contanst.WEB_SITE + Contanst.
                REQUEST_VIDEO_URL).build();
        Call call = okHttpClient.newCall(request);
        //开启异步线程访问网络
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = MSG_VIDEO_OK;
                msg.obj = res;
                mHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Request arg0, IOException arg1) {
            }
        });
    }
    /**
     * 事件捕获
     */
    class MHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case MSG_VIDEO_OK:
                    if (msg.obj != null) {
                        String vlResult = (String) msg.obj;
                        //使用Gson解析数据
                        List<VideoBean> videoList = JsonParse.getInstance().
                                getVideoList(vlResult);
                        adapter.setData(videoList);
                    }
                    break;
            }
        }
    }
}
