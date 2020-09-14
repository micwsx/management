package com.micwsx.project.advertise.common;

import com.alibaba.fastjson.JSONObject;
import com.micwsx.project.advertise.utility.BinaryTuple;
import com.micwsx.project.advertise.utility.HttpUtility;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MenuContext extends Context {

    private Map<String, BinaryTuple<String, String>> menu = new HashMap();

    public MenuContext() {
        menu.put("personal", new BinaryTuple<>("个人中心", "/menu/personal"));
        menu.put("activities", new BinaryTuple<>("会议入口", "/menu/activities"));
    }

    public boolean createMenu(String host) {
        StringBuilder menuJson = new StringBuilder();
        menuJson.append("{\"button\":[");
        String temp = "";
        for (Map.Entry<String, BinaryTuple<String, String>> kv : menu.entrySet()) {
            temp += "{\"type\":\"view\",\"name\":\"" + kv.getValue().getItem1() + "\",\"url\":\"" + host + kv.getValue().getItem2() + "\"},";
        }
        menuJson.append(temp.substring(0, temp.length() - 1));
        menuJson.append("]}");

        String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + getAccessToken().getToken();
        String responseMsg = HttpUtility.post(url, menuJson.toString());
        //{"errcode":0,"errmsg":"ok"}
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);
        return jsonObject.getInteger("errcode") == 0;
    }

    public static void main(String[] args) throws Throwable {


//        String encode = URLEncoder.encode("/menu/personal", "utf-8");
//        System.out.println(encode);

//        Map<String, BinaryTuple<String,String>> menu=new HashMap();
//        menu.put("personal",new BinaryTuple<>("个人中心","/menu/personal"));
//        menu.put("activities",new BinaryTuple<>("会议入口","/menu/activities"));
//
//        String host="http://localhost:8081";
//        StringBuilder menuJson=new StringBuilder();
//        menuJson.append("{\"button\":[");
//        String temp="";
//        for (Map.Entry<String,BinaryTuple<String,String>> kv:menu.entrySet()){
//            temp+="{\"type\":\"view\",\"name\":\""+kv.getValue().getItem1()+"\",\"url\":\""+host+kv.getValue().getItem2()+"\"},";
//        }
//        menuJson.append(temp.substring(0,temp.length()-1));
//        menuJson.append("]}");
//        System.out.println(menuJson.toString());
    }

}
