package com.example.topline121022022064.fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.topline121022022064.utils.DBUtils;
import com.example.topline121022022064.utils.UtilsHelper;
import com.example.topline121022022064.receiver.UpdateUserInfoReceiver;
import com.example.topline121022022064.R;
import com.example.topline121022022064.activity.LoginActivity;
import com.example.topline121022022064.activity.UserInfoActivity;
import com.example.topline121022022064.activity.CollectionActivity;
import com.example.topline121022022064.activity.SettingActivity;
import com.example.topline121022022064.activity.CalendarActivity;
import com.example.topline121022022064.activity.ConstellationActivity;
import com.example.topline121022022064.activity.ScrawActivity;
import com.example.topline121022022064.activity.MapActivity;
public class MeFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ll_calendar, ll_constellation, ll_scraw, ll_map;
    private RelativeLayout rl_collection, rl_setting;
    private CircleImageView iv_avatar;
    private View view;
    private UpdateUserInfoReceiver updateUserInfoReceiver;
    private IntentFilter filter;
    private boolean isLogin = false;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    public MeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        ll_calendar = (LinearLayout) view.findViewById(R.id.ll_calendar);
        ll_constellation = (LinearLayout) view.findViewById(R.id.ll_constellation);
        ll_scraw = (LinearLayout) view.findViewById(R.id.ll_scraw);
        ll_map = (LinearLayout) view.findViewById(R.id.ll_map);
        rl_collection = (RelativeLayout) view.findViewById(R.id.rl_collection);
        rl_setting = (RelativeLayout) view.findViewById(R.id.rl_setting);
        iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.
                collapsing_tool_bar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ToolbarTitle);
        isLogin = UtilsHelper.readLoginStatus(getActivity());
        setLoginParams(isLogin);
        setListener();
        receiver();
    }
    private void receiver() {
        updateUserInfoReceiver = new UpdateUserInfoReceiver(
                new UpdateUserInfoReceiver.BaseOnReceiveMsgListener() {
                    @Override
                    public void onReceiveMsg(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (UpdateUserInfoReceiver.ACTION.UPDATE_USERINFO
                                .equals(action)) {
                            String type = intent.getStringExtra(UpdateUserInfoReceiver.
                                    INTENT_TYPE.TYPE_NAME);
                            if (UpdateUserInfoReceiver.INTENT_TYPE.UPDATE_HEAD  //更新头像
                                    .equals(type)) {
                                String head = intent.getStringExtra("head");
                                Bitmap bt = BitmapFactory.decodeFile(head);
                                if (bt != null) {
                                    Drawable drawable = new BitmapDrawable(bt);
                                    iv_avatar.setImageDrawable(drawable);
                                } else {
                                    iv_avatar.setImageResource(R.drawable.default_head);
                                }
                            }
                        }
                    }
                });
        filter = new IntentFilter(UpdateUserInfoReceiver.ACTION.UPDATE_USERINFO);
        getActivity().registerReceiver(updateUserInfoReceiver, filter);
    }
    private void setListener() {
        ll_calendar.setOnClickListener(this);
        ll_constellation.setOnClickListener(this);
        ll_scraw.setOnClickListener(this);
        ll_map.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateUserInfoReceiver != null) {
            getActivity().unregisterReceiver(updateUserInfoReceiver);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_calendar:
                Intent calendarIntent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(calendarIntent);
                break;
            case R.id.ll_constellation:
                Intent constellIntent = new Intent(getActivity(), ConstellationActivity.class);
                startActivity(constellIntent);
                break;
            case R.id.ll_scraw:
                Intent scarwIntent = new Intent(getActivity(), ScrawActivity.class);
                startActivity(scarwIntent);
                break;
            case R.id.ll_map:
                Intent mapIntent = new Intent(getActivity(), MapActivity.class);
                startActivity(mapIntent);
                break;
            case R.id.rl_collection:
                if (isLogin) {
                    Intent collection = new Intent(getActivity(), CollectionActivity.class);
                    startActivity(collection);
                } else {
                    Toast.makeText(getActivity(), "您还未登录，请先登录",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_setting:
                if (isLogin) {
                    //跳转到设置界面
                    Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
                    startActivityForResult(settingIntent, 1);
                } else {
                    Toast.makeText(getActivity(), "您还未登录，请先登录",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_avatar:
                if (isLogin) {
//                    跳转到个人资料界面
                    Intent userinfo = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(userinfo);
                } else {
                    //跳转到登录界面
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(login, 1);
                }
                break;
        }
    }
    /**
     * 登录成功后设置我的界面
     */
    public void setLoginParams(boolean isLogin) {
        if (isLogin) {
            String userName = UtilsHelper.readLoginUserName(getActivity());
            collapsingToolbarLayout.setTitle(userName);
            String head = DBUtils.getInstance(getActivity()).getUserHead(userName);
            Bitmap bt = BitmapFactory.decodeFile(head);
            if (bt != null) {
                Drawable drawable = new BitmapDrawable(bt);//转换成drawable
                iv_avatar.setImageDrawable(drawable);
            } else {
                iv_avatar.setImageResource(R.drawable.default_head);
            }
        } else {
            iv_avatar.setImageResource(R.drawable.default_head);
            collapsingToolbarLayout.setTitle("点击登录");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            boolean isLogin = data.getBooleanExtra("isLogin", false);
            setLoginParams(isLogin);
            this.isLogin = isLogin;
        }
    }
}

