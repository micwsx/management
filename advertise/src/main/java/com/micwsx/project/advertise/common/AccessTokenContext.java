package com.micwsx.project.advertise.common;

import com.alibaba.fastjson.JSONObject;
import com.micwsx.project.advertise.utility.HttpUtility;
import com.micwsx.project.advertise.utility.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;

public abstract class AccessTokenContext {
    private static Logger logger = LoggerFactory.getLogger(AccessTokenContext.class);
    private static String tokenFileName = "accessToken.json";

    /**
     * 首先尝试从文件中获取access_token，
     * 获取AccessToken并序列化本地文件，存在则覆盖
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        //　首先尝试从文件中获取access_token
        Optional<AccessToken> optionalAccessToken = loadAccessTokenLocal();
        if (optionalAccessToken.isPresent()) {
            AccessToken accessToken = optionalAccessToken.get();
            if (System.currentTimeMillis() < accessToken.getExpiryTime()) {
                System.out.println("本地文件中的accessToken有效");
                return accessToken;
            } else {
                System.out.println("本地文件中的accessToken已过期！");
            }
        }
        System.out.println("重新获取accessToken");
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Credential.APPID + "&secret=" + Credential.APPSECRET;
        // GET请求
        String responseMsg = HttpUtility.get(url);
        logger.info("返回内容："+responseMsg);
        JSONObject outJson = JSONObject.parseObject(responseMsg);
        Integer expires_in = outJson.getInteger("expires_in");
        String access_token = outJson.getString("access_token");
        AccessToken accessToken = new AccessToken(access_token, expires_in);
        String jsonObject = JSONObject.toJSONString(accessToken);
        // 保存本地文件
        IOUtil.save(tokenFileName, jsonObject);
        return accessToken;
    }

    private static Optional<AccessToken> loadAccessTokenLocal() {
        File root = new File(IOUtil.class.getClassLoader().getResource("").getPath());
        String fullPath = root + File.separator + tokenFileName;
        File file = new File(fullPath);
        Optional<BufferedReader> bufferedReader = null;
        if (file.exists()) {
            try {
                bufferedReader = Optional.of(new BufferedReader(new FileReader(file)));
                String line = bufferedReader.get().readLine();
                AccessToken accessToken = JSONObject.parseObject(line, AccessToken.class);
                return Optional.ofNullable(accessToken);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                bufferedReader.ifPresent((reader) -> {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        return Optional.empty();
    }


    public static void main(String[] args) {
        AccessToken accessToken = AccessTokenContext.getAccessToken();
        System.out.println(accessToken);
    }
}
