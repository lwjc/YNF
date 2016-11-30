package com.bwie.zhangtianchengyunifang.BaseDataDemo;

import android.text.TextUtils;

import com.bwie.zhangtianchengyunifang.Utils.CommonUtils;
import com.bwie.zhangtianchengyunifang.Utils.MD5Encoder;
import com.bwie.zhangtianchengyunifang.ViewDemo.ShowingPage;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by lwj on 2016/11/29.
 */
public abstract class BaseData {

    private final File file;
    public static final int NOTIME = 0;
    public static final int NORMALTIME = 3 * 24 * 60 * 60 * 1000;

    public BaseData() {
        //找到缓存路径
        File cacheDir = CommonUtils.getContext().getCacheDir();
        //给他指定一个存储路径
        file = new File(cacheDir, "yunifang");
        //进行判断
        if (!file.exists()) {
            //如果不是文件夹就新创建一个
            file.mkdir();
        }
    }
    //path 路径，args请求参数，indesx 索引， vatime 有效时间

    public void getData(String path, String args, int index, int vatime) {
        //如果有效时间为0那么表示为直接访问网络
        if (vatime == 0) {
            getDataFromNet(path, args, index, vatime);
            //fouze从本地读取
        } else {
            String data = getDataFromLocal(path, index, vatime);
            if (TextUtils.isEmpty(data)) {
                //如果为空，请求网络
                getDataFromNet(path, args, index, vatime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }

        }
    }

    //从本地读取
    private String getDataFromLocal(String path, int index, int vatime) {
        //读取文件信息
        try {
            File read_file = new File(file, MD5Encoder.encode(path) + index);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(read_file));
            //先读取第一行的时间
            String s = bufferedReader.readLine();
            //强转为long类型
            long time = Long.parseLong(s);
            //和当前时间进行比较
            if (System.currentTimeMillis() - time < vatime) {
                //将信息读出来
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
                return builder.toString();


            } else {
                //无效
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //从网络读取
    private void getDataFromNet(final String path, final String args, final int index, final int vatime) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(path + "?" + args)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String data = response.body().string();
                CommonUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置数据
                        setResultData(data);
                    }
                });


                //写到本地
                writeDataToLocal(path, args, index, vatime, data);


            }
        });


    }
    /**
     * @param path      请求地址
     * @param index     页码索引
     * @param validTime 有效时间
     */
    public void postData(String path, HashMap<String, String> argsMap, int index, int validTime) {
        //先判断有效时间
        if (validTime == 0) {
            //直接请求网络，要最新数据
            postDataFromNet(path, argsMap, index, validTime);
        } else {
            //从本地获取
            String data = getDataFromLocal(path, index, validTime);
            if (TextUtils.isEmpty(data)) {
                //如果为空，请求网络
                postDataFromNet(path, argsMap, index, validTime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }
        }
    }

    private void postDataFromNet(String path, HashMap<String, String> argsMap, int index, int validTime) {
        //将Map中的参数取出
        Set<String> strings = argsMap.keySet();
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String key : strings
                ) {
            formEncodingBuilder.add(key, argsMap.get(key));
        }
        //创建client对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建body
        RequestBody requestBody = formEncodingBuilder.build();
        //创建一个post请求
        Request request = new Request.Builder()
                .url(path)
                .post(requestBody)
                .build();
        //new call
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //设置error
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }
            @Override
            public void onResponse(final Response response) throws IOException {
                final String data = response.body().string();
                CommonUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置数据
                        setResultData(data);
                    }
                });
            }
        });


    }


    public abstract void setResultData(String data);
    protected abstract void setResulttError(ShowingPage.StateType stateLoadError);
    //请求网络后将读取的信息写入本地
    private void writeDataToLocal(String path, String args, int index, int vatime, String data) {
        try {
            //每一个请求都会生成一个文件
            File write_file = new File(file, MD5Encoder.encode(path) + index);
            //写流信息
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(write_file));
            bufferedWriter.write(System.currentTimeMillis() + vatime + "\r\n");
            //从网络上请求的数据
            bufferedWriter.write(data);
            //关闭流
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
