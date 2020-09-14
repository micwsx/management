package com.micwsx.project.advertise.utility;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

//文件接收参考文章： qhttps://www.cnblogs.com/jying/p/10310865.html
public abstract class HttpUtility {

    private static Logger logger = LoggerFactory.getLogger(HttpUtility.class);

    /**
     * @param url     :http://xxx.xxx.xxx.xxx/xxx/xxx?apikey={apikey}
     * @param params: map.put("apikey","xxx");
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        // 请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(null, httpHeaders);
        //发送请求
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, params);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exchange: " + exchange)
                .append("hasBody(): " + exchange.hasBody())
                .append("getBody(): " + exchange.getBody())
                .append("getStatusCode():" + exchange.getStatusCode())
                .append("getStatusCodeValue():" + exchange.getStatusCodeValue())
                .append("getHeaders():" + exchange.getHeaders());
        logger.info(stringBuilder.toString());
        return exchange.getBody();
    }

    /**
     * @param url :http://xxx.xxx.xxx.xxx/xxx/xxx?apikey=abc&name=michael
     * @return
     */
    public static String get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        // 请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //发送请求
        String responseMsg = restTemplate.getForObject(url, String.class);
        return responseMsg;
    }


    /**
     * @param url:       http://xxx.xxx.xxx.xxx/xxx/xxx?apikey={apikey}
     * @param jsonParam: json参数字符串
     * @return
     */
    public static String post(String url, String jsonParam) {
        RestTemplate restTemplate = new RestTemplate();
        // 请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //请求体
        HttpEntity<String> request = new HttpEntity<>(jsonParam, httpHeaders);
        //发送请求
        ResponseEntity<String> exchange = restTemplate.postForEntity(url, request, String.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exchange: " + exchange)
                .append("hasBody(): " + exchange.hasBody())
                .append("getBody(): " + exchange.getBody())
                .append("getStatusCode():" + exchange.getStatusCode())
                .append("getStatusCodeValue():" + exchange.getStatusCodeValue())
                .append("getHeaders():" + exchange.getHeaders());
        logger.info(stringBuilder.toString());
        return exchange.getBody();
    }

    public static String postWithXml(String url, String xml) {
        logger.info("请求报文：" + xml);
        RestTemplate restTemplate = new RestTemplate();
        // 请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        //请求体
        HttpEntity<String> request = new HttpEntity<>(xml, httpHeaders);
        //发送请求
        ResponseEntity<String> exchange = restTemplate.postForEntity(url, request, String.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exchange: " + exchange)
                .append("hasBody(): " + exchange.hasBody())
                .append("getBody(): " + exchange.getBody())
                .append("getStatusCode():" + exchange.getStatusCode())
                .append("getStatusCodeValue():" + exchange.getStatusCodeValue())
                .append("getHeaders():" + exchange.getHeaders());
        logger.info("响应报文：" + stringBuilder.toString());
        return exchange.getBody();
    }


    private HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new NoopHostnameVerifier());
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        return factory;
    }


    /**
     * 下载文件到本地doc目录下
     *
     * @param url：下载地址
     * @param fileName：文件名
     */
    public static void donwloadToDoc(String url, String fileName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Resource> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
        byte[] imgByte = response.getBody();
        // 保存到本地doc目录下
        IOUtil.saveIntoDoc(fileName, imgByte);
    }


    /**
     * 读取流数据
     *
     * @param request
     * @return
     */
    public static String getStreamContent(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 微信上传media文件
     * @param url: 上传地址:https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=<token>
     *                      https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=<token>&type=image";
     * @param file：上传文件
     * @param isVideo: 是否是video
     * @param title： video title
     * @param introduction: video introduction
     * @return
     */
    public static String uploadForWechat(String url, File file,boolean isVideo,String title,String introduction) {
        String responseMsg = "";
        HttpURLConnection connection = null;
        String BOUNDARY = "---------------------------123821742118716";//boundary 就是request头和上传文件内容的分隔符。
        try {
            URL _url = new URL(url);
            connection = (HttpURLConnection) _url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(30000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0(Windows;U;Windows NT 6.1;zh-CN;rv:1.9.2.6)");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            // 向远程输出流
            OutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            // file
            String inputName = "media";
            String fileName = file.getName();
            String contentType = new MimetypesFileTypeMap().getContentType(file);
            if (fileName.endsWith(".png")) {
                contentType = "image/png";
            }
            if (contentType == null || contentType.equals("")) {
                contentType = "application/octet-stream";
            }

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            stringBuffer.append("Content-Disposition:form-data;name=\"" + inputName + "\";filename=\"" + fileName + "\"\r\n");
            stringBuffer.append("Content-Type:" + contentType + "\r\n\r\n");

            outputStream.write(stringBuffer.toString().getBytes());
            DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytes);
            }
            inputStream.close();
            byte[] end = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            outputStream.write(end);

            if (isVideo){
                outputStream.write(("Content-Disposition: form-data; name=\"description\";\r\n\r\n").getBytes());
                outputStream.write(("{{\"title\":\""+title+"\", \"introduction\":\""+introduction+"\"}}").getBytes());
                outputStream.write(end);
            }

            outputStream.flush();
            outputStream.close();


            // 读取返回数据
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            reader.close();
            reader = null;
            responseMsg = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    public static void main(String[] args) {
        String url = "gQFR8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNUpLM0VqUFRhRGUxMDVmbGh2Y08AAgSF1AtfAwSAOgkA";
        try {
            System.out.println(URLEncoder.encode(url, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
