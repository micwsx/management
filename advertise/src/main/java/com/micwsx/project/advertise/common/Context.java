package com.micwsx.project.advertise.common;

import com.micwsx.project.advertise.utility.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Context {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static AccessToken accessToken;

    /**
     * 获取微信access_token,后续操作都需要的access_token,只对内部使用。
     *
     * @return
     */
    protected AccessToken getAccessToken() {
        if (accessToken != null) {
            boolean expriy = System.currentTimeMillis() > accessToken.getExpiryTime();
            if (expriy) {
                logger.info("内存access_token过期,重新获取！");
                accessToken = AccessTokenContext.getAccessToken();
            }
        } else {
            //内存中不可能为空
            accessToken = AccessTokenContext.getAccessToken();
            logger.info("内存access_token有效！");
        }
        return accessToken;
    }

    protected String sha1(String plainText){
        return CryptoUtil.digest(plainText, CryptoUtil.Algrithom.SHA);
    }
}
