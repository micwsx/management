package com.micwsx.project.advertise.utility;

import com.micwsx.project.advertise.common.Credential;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Util {

    /**
     * // 过滤空值，升排序，区分大小写，排序sign字段+&key=xx
     * @param map
     * @return
     */
    public static String wechatSign(Map<String, String> map) {
        String sign = map.entrySet().stream()
                .filter(kv -> !StringUtils.isEmpty(kv.getValue()) && !kv.getKey().equals("sign"))
                .sorted(Map.Entry.comparingByKey())
                .map(kv -> kv.getKey() + "=" + kv.getValue())
                .collect(Collectors.joining("&"));
        String signContent= sign+"&key="+ Credential.PAY_MD5Key;
        return CryptoUtil.digest(signContent, CryptoUtil.Algrithom.MD5);
    }

    public static String mapToXml(Map<String, String> map){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<xml>");
        for (Map.Entry<String, String> kv : map.entrySet()) {
            stringBuilder.append("<" + kv.getKey() + ">").append(kv.getValue()).append("<" + kv.getKey() + "/>");
        }
        stringBuilder.append("</xml>");
        return stringBuilder.toString();
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = request.getRemoteAddr();
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
            ip = "unknown";
        return ip;
    }

}
