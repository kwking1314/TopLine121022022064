package com.example.topline121022022064.utils;

import com.example.topline121022022064.bean.NewsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.util.Log;
import java.lang.reflect.Type;
import java.util.List;
import com.example.topline121022022064.bean.PythonBean;
import com.example.topline121022022064.bean.VideoBean;
import com.example.topline121022022064.bean.ConstellationBean;
public class JsonParse {
    private static JsonParse instance;

    private JsonParse() {
    }

    public static JsonParse getInstance() {
        if (instance == null) {
            instance = new JsonParse();
        }
        return instance;
    }

public List<NewsBean> getAdList(String json) {
    Gson gson = new Gson();
    Type listType = new TypeToken<List<NewsBean>>() {}.getType();
    try {
        List<NewsBean> adList = gson.fromJson(json, listType);
        for (NewsBean bean : adList) {
            // 补充picUrl到newsUrl
            if (bean.getNewsUrl() == null || bean.getNewsUrl().isEmpty()) {
                bean.setNewsUrl(bean.getPicUrl());
            }
        }
        return adList;
    } catch (Exception e) {
        Log.e("JsonParse", "JSON 数据格式错误", e);
        return null;
    }
}


    public List<NewsBean> getNewsList(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<NewsBean>>() {}.getType();
        try {
            List<NewsBean> newsList = gson.fromJson(json, listType);
            return newsList;
        } catch (Exception e) {
            Log.e("JsonParse", "JSON 数据格式错误", e);
            return null;
        }
    }
    public List<PythonBean> getPythonList(String json) {
        //使用gson库解析JSON数据
        Gson gson = new Gson();
        //创建一个TypeToken的匿名子类对象，并调用对象的getType()方法
        Type listType = new TypeToken<List<PythonBean>>() {}.getType();
        //把获取到的信息集合存到pythonList中
        List<PythonBean> pythonList = gson.fromJson(json, listType);
        return pythonList;
    }
    public List<VideoBean> getVideoList(String json) {
        //使用gson库解析JSON数据
        Gson gson = new Gson();
        //创建一个TypeToken的匿名子类对象，并调用对象的getType()方法
        Type listType = new TypeToken<List<VideoBean>>() {
        }.getType();
        //把获取到的信息集合存到videoList中
        List<VideoBean> videoList = gson.fromJson(json, listType);
        return videoList;
    }
    public List<ConstellationBean> getConstellaList(String json) {
        //使用gson库解析JSON数据
        Gson gson = new Gson();
        //创建一个TypeToken的匿名子类对象，并调用对象的getType()方法
        Type listType = new TypeToken<List<ConstellationBean>>() {
        }.getType();
        //把获取到的信息集合存到constellaList中
        List<ConstellationBean> constellaList = gson.fromJson(json, listType);
        return constellaList;
    }
}