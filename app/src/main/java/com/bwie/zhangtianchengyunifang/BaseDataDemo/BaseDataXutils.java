package com.bwie.zhangtianchengyunifang.BaseDataDemo;

import android.text.TextUtils;
import com.bwie.zhangtianchengyunifang.Utils.CommonUtils;
import com.bwie.zhangtianchengyunifang.Utils.MD5Encoder;
import com.bwie.zhangtianchengyunifang.ViewDemo.ShowingPage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhiyuan on 16/9/29.
 */
public abstract class BaseDataXutils {

    private final File fileDir;
    public static final  int  NOTIME=0;
    public static final  int  NORMALTIME=3*24*60*60*1000;


    //缓存数据存到哪里？
    public BaseDataXutils() {
        //找到存储路径
        File cacheDir = CommonUtils.getContext().getCacheDir();
        fileDir = new File(cacheDir, "yunifang");
        if (!fileDir.exists()) {
            //创建文件夹
            fileDir.mkdir();
        }
    }
    //？name=zhangsan  index 索引
    // 0页
    //http://www.baidu.com
    //http://www.baidu.com 1

    /**
     * @param path      请求地址
     * @param args      请求参数
     * @param index     页码索引
     * @param validTime 有效时间
     */
    public void getData(String path, String args, int index, int validTime) {
        //先判断有效时间
        if (validTime == 0) {
            //直接请求网络，要最新数据
            getDataFromNet(path, args, index, validTime);
        } else {
            //从本地获取
            String data = getDataFromLocal(path, index, validTime);
            if (TextUtils.isEmpty(data)) {
                //如果为空，请求网络
                getDataFromNet(path, args, index, validTime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }
        }
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
        RequestParams requestParam = new RequestParams(path);
        Set<Map.Entry<String, String>> entries = argsMap.entrySet();
        for (Map.Entry<String, String> entry :
                entries) {
            requestParam.addParameter(entry.getKey(),entry.getValue());
        }

        x.http().post(requestParam, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public abstract void setResultData(String data);

    private String getDataFromLocal(String path,  int index, int validTime) {
        //读取文件信息
        //读时间
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String s = bufferedReader.readLine();

            long time = Long.parseLong(s);
            //和当前时间进行比较
            //111-110
            if (System.currentTimeMillis() < time) {
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

    /**
     * 从网络获取数据
     *
     * @param path
     * @param args
     * @param index
     * @param validTime
     */
    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        RequestParams requestParams = new RequestParams(path + "?" + args);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                //如果请求成功----将数据返回
                setResultData(s);
                //缓存到本地
                writeDataToLocal(path, args, index, validTime, s);
            }


            @Override
            public void onError(Throwable throwable, boolean b) {
                //请求失败，告诉用户
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onCancelled(CancelledException e) {
                //------
            }

            @Override
            public void onFinished() {

            }
        });
    }

    protected abstract void setResulttError(ShowingPage.StateType stateLoadError);

    /**
     * 将数据写到本地
     *
     * @param path
     * @param args
     * @param index
     * @param validTime
     * @param
     */
    private void writeDataToLocal(String path, String args, int index, int validTime, String data) {
        //每一条请求，都是生成一个文件   dawedfakwehfaowehfoaw
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            //写流信息
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            //323250328383
            //打开个娃偶尔很乏味偶发沃尔夫哈乌龟啊我费劲儿啊
            //100+10
            bufferedWriter.write(System.currentTimeMillis() + validTime + "\r\n");
            //从网络上请求的数据
            bufferedWriter.write(data);

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
