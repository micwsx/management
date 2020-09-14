package com.micwsx.project.advertise.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class MediaContext extends Context {

    public static void main(String[] args) throws Exception {

//            MediaContext mediaContext = new MediaContext();
//        String newsMediaId="GQeR4nw8oSE1DDtg8fjVFClPtgPJAo0Uaw9WZ4Qlnnk";
//        String videoMediaId="GQeR4nw8oSE1DDtg8fjVFJe4WRd7S5bIEXfqlWzYY44";
//            String imageMediaId = "GQeR4nw8oSE1DDtg8fjVFFeKyB0nu6jAZHcnatCm46M";
//            File materialFile = mediaContext.getMaterialFile(imageMediaId);
//            System.out.println(materialFile.getPath());


//        JSONObject material = mediaContext.getMaterial(newsMediaId);
//        System.out.println(material.toJSONString());

//        JSONObject materialCount = mediaContext.getMaterialCount();
//        System.out.println(materialCount.toJSONString());

//        JSONObject image = mediaContext.getMaterialList("news", 0,20);
//        System.out.println(image.toJSONString());

//        String mediaId="GQeR4nw8oSE1DDtg8fjVFInn48xK-i7rOlgZlJ87vDY";
//        JSONObject jsonObject = mediaContext.deleteMaterial(mediaId);
//        System.out.println(jsonObject.toJSONString());

//        try {
//            MediaContext mediaContext=new MediaContext();
//            File imageFile= FileUtil.getFileInUpload("regex.png");
//            if (imageFile.exists()){
//                System.out.println("有效文件");
//                String url="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + mediaContext.getAccessToken().getToken() + "&type=image";
//                String responseMsg = HttpUtility.uploadForWechat(url, imageFile, false, "", "");
//                String responseMsg = mediaContext.uploadImage(imageFile);
//                JSONObject responseMsg = mediaContext.addMaterial("image",imageFile,false,"","");
//                System.out.println(responseMsg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 参考文章： https://blog.csdn.net/weixin_33735077/article/details/91620809
     * 上传图片,获取图片url地址
     * 100000个的限制，图片仅支持jpg/png格式，大小必须在1MB以下
     *
     * @param imageFile： 上传图片文件
     * @return: 返回图片地址。
     * e.g. http://mmbiz.qpic.cn/mmbiz_png/iaSibhmkIBzr1FK28kj7L8IzkB1lrwGSibJ1VicmYBcWNbFBldSdBBwq9AicN4e3dkrl6shI3ibFxqbpKMmNibyDhHetg/0
     */
    public String uploadImage(File imageFile) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + getAccessToken().getToken();
        WritableResource resource = new FileSystemResource(imageFile);
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
        data.add("media", resource);//传递参数
        RestTemplate restTemplate = new RestTemplate();
        String responseMsg = restTemplate.postForObject(url, data, String.class);
        System.out.println("微信返回结果：" + responseMsg);
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);
        String imagUrl = jsonObject.getString("url");
        return imagUrl;
    }

    /**
     * 添加永久图文素材
     *
     * @param news
     * @return
     */
    public JSONObject addNews(JSONObject news) {
        String uri = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + getAccessToken().getToken();
        String responseMsg = postJson(uri, news.toString());
        return JSONObject.parseObject(responseMsg);
    }

    /**
     * 修改永久图文素材
     *
     * @param news：格式 {
     *                "articles": [{
     *                "title": TITLE,
     *                "thumb_media_id": THUMB_MEDIA_ID,
     *                "author": AUTHOR,
     *                "digest": DIGEST,
     *                "show_cover_pic": SHOW_COVER_PIC(0 / 1),
     *                "content": CONTENT,
     *                "content_source_url": CONTENT_SOURCE_URL,
     *                "need_open_comment":1,
     *                "only_fans_can_comment":1
     *                },
     *                //若新增的是多图文素材，则此处应还有几段articles结构
     *                ]
     *                }
     * @return
     */
    public JSONObject editNews(JSONObject news) {
        String uri = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=" + getAccessToken().getToken();
        String responseMsg = postJson(uri, news.toString());
        return JSONObject.parseObject(responseMsg);
    }

    /**
     * 添加音频、图片、视频永久素材
     *
     * @param type：image/voice/video/thumb（缩略图）
     * @param file:                             上传文件
     * @param isVideo：是视频
     * @param videoTitle：视频标题
     * @param videoIntroduction：视频介绍
     * @return: {"item":[],"media_id":"GQeR4nw8oSE1DDtg8fjVFFeKyB0nu6jAZHcnatCm46M","url":"http://mmbiz.qpic.cn/mmbiz_png/iaSibhmkIBzr1FK28kj7L8IzkB1lrwGSibJ1VicmYBcWNbFBldSdBBwq9AicN4e3dkrl6shI3ibFxqbpKMmNibyDhHetg/0?wx_fmt=png"}
     */
    public JSONObject addMaterial(String type, File file, boolean isVideo, String videoTitle, String videoIntroduction) {
        RestTemplate restTemplate = new RestTemplate();
        WritableResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
        data.add("media", resource);
        if (type == "video") {
            JSONObject p = new JSONObject();
            p.put("title", videoTitle);
            p.put("introduction", videoIntroduction);
            data.add("description", p.toString());
        }
        String uri = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + getAccessToken().getToken() + "&type=" + type;
        String responseMsg = restTemplate.postForObject(uri, data, String.class);
        System.out.println("微信返回：" + responseMsg);
        return JSONObject.parseObject(responseMsg);
    }


    /**
     * 获取音频、图片素材,保存本地临时目录下。
     *
     * @param mediaId
     * @throws IOException
     * @return: 返回临时文件
     */
    public File getMaterialFile(String mediaId) throws IOException {
        JSONObject param = new JSONObject();
        param.put("media_id", mediaId);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(param.toString(), headers);
        String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + getAccessToken().getToken();
        ResponseEntity<byte[]> response = restTemplate.exchange(uri, HttpMethod.POST, entity, byte[].class);
        String content_disposition = response.getHeaders().get("Content-disposition").get(0);
        int start = content_disposition.indexOf("\"");
        int mid = content_disposition.indexOf(".");
        int end = content_disposition.lastIndexOf("\"");
        String fileName = content_disposition.substring(start + 1, mid);
        String suffix = content_disposition.substring(mid, end);
        // 保存到临时文件目录下以temp-前缀
        File file = File.createTempFile(fileName, suffix, null);
//        File file = File.createTempFile("tmp-", "." + response.getHeaders().getContentType().getSubtype());
        // 若不需要保存临时文件可删除
//        file.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(response.getBody());
        fos.flush();
        fos.close();
        return file;
    }

    /**
     * 获取图文、视频素材
     *
     * @param mediaId：图文素材Id或视频Id
     * @return：若其它类型素材，直接返回内容，此方法会报异常。 news类型返回结果：{"news_item":[{"thumb_url":"http://mmbiz.qpic.cn/mmbiz_png/iaSibhmkIBzr18jNlBlf2IgTD7weTggMtic7t6AzLZ70H8v6ISYkQ63tOv0ibzFD8YJqdkR4PX99ODibUjQa8Hfcia6Q/0?wx_fmt=png","thumb_media_id":"GQeR4nw8oSE1DDtg8fjVFFxCw_CHNtfgkNY6bqNlImM","author":"","only_fans_can_comment":1,"digest":"","show_cover_pic":0,"content_source_url":"","need_open_comment":0,"title":"å\u0098¿å\u0093\u0088TVï¼\u009Aå®¢æ\u009C\u008Dä¸ºæ\u0082¨æ\u009C\u008Då\u008A¡","content":"<img data-src=\"https://mmbiz.qpic.cn/mmbiz_png/iaSibhmkIBzr18jNlBlf2IgTD7weTggMtic7t6AzLZ70H8v6ISYkQ63tOv0ibzFD8YJqdkR4PX99ODibUjQa8Hfcia6Q/640?wx_fmt=png\">","url":"http://mp.weixin.qq.com/s?__biz=MjM5MzM1NjIxMQ==&mid=100000010&idx=1&sn=d75111540c355a8918c6fd6dc05ca9b8&chksm=269901d611ee88c06b58af92abdd98bc25fc05515bf9c983b1c4fe37bac895276d7f5dd8914a#rd"}],"update_time":1542472440,"create_time":1542471862}
     * video类型返回结果:{"down_url":"http://203.205.137.222/vweixinp.tc.qq.com/1007_a8776437bb864a22a2ad093c847a6f1c.f10.mp4?vkey=4C71D4AEF6D4ECFF088D43880A4E37CC07EE030B472E74F873CFCE3565FB04090EE0DAA5E5B805F134135C037BDBD86E2AA6303C6142396B319EC8AF20D2FE8DB7872D2D3062EAEFD75ADB9EBB9797AC2D6FDE0EE145C5D7&sha=0&save=1","description":"","title":"test","tags":[]}
     */
    public JSONObject getMaterial(String mediaId) {
        JSONObject param = new JSONObject();
        param.put("media_id", mediaId);
        String uri = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + getAccessToken().getToken();
        String responseMsg = postJson(uri, param.toString());
        return JSONObject.parseObject(responseMsg);
    }

    /**
     * 删除素材
     *
     * @param mediaId: 素材mediaId
     * @return: {"errcode":0,"errmsg":"ok"}
     */
    public JSONObject deleteMaterial(String mediaId) {
        JSONObject param = new JSONObject();
        param.put("media_id", mediaId);
        String uri = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=" + getAccessToken().getToken();
        String responseMsg = postJson(uri, param.toString());
        return JSONObject.parseObject(responseMsg);
    }

    /**
     * 获取素材总数
     *
     * @return: {"voice_count":0,"video_count":1,"image_count":8,"news_count":4}
     */
    public JSONObject getMaterialCount() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=" + getAccessToken().getToken();
        String responseMsg = restTemplate.getForObject(uri, String.class);
        return JSONObject.parseObject(responseMsg);
    }


    /**
     * 获取素材列表
     *
     * @param type：                            图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset：从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count：返回素材的数量，取值在1到20之间
     * @return: {"item_count":8,"item":[{"update_time":1596175992,"name":"regex.png","media_id":"GQeR4nw8oSE1DDtg8fjVFFeKyB0nu6jAZHcnatCm46M","url":"http://mmbiz.qpic.cn/mmbiz_png/iaSibhmkIBzr1FK28kj7L8IzkB1lrwGSibJ1VicmYBcWNbFBldSdBBwq9AicN4e3dkrl6shI3ibFxqbpKMmNibyDhHetg/0?wx_fmt=png","tags":[]},{"update_time":1546931977,"name":"sprout.jpg","media_id":"GQeR4nw8oSE1DDtg8fjVFEP3YLoS0VFqfOowu1hCW1c","url":"http://mmbiz.qpic.cn/mmbiz_jpg/iaSibhmkIBzr0yCK1IEzZDSFx25RKhG3DIkclNmle0UxXtwZbQPg6h8Bic5ibPYe1a1l4plbhdYz3swcDEibRJAJ9jQ/0?wx_fmt=jpeg","tags":[]},{"update_time":1546931281,"name":"å\u008F\u0091è\u008A½.jpg","media_id":"GQeR4nw8oSE1DDtg8fjVFCDDbeIfJcoW2up4jrGGw8g","url":"http://mmbiz.qpic.cn/mmbiz_jpg/iaSibhmkIBzr0yCK1IEzZDSFx25RKhG3DIkclNmle0UxXtwZbQPg6h8Bic5ibPYe1a1l4plbhdYz3swcDEibRJAJ9jQ/0?wx_fmt=jpeg","tags":[]},{"update_time":1546592460,"name":"timg.png","media_id":"GQeR4nw8oSE1DDtg8fjVFCcO02pfPdHfQ_lPqg-Qp4Y","url":"http://mmbiz.qpic.cn/mmbiz_jpg/iaSibhmkIBzr1J7UavvQibqxuYoYWmjicPRaOwVPTeBib9h7WtZPhtiaEGyWEbxQvBvPwdm58TLyYQgY8Cw8e3VnQveg/0?wx_fmt=jpeg","tags":[]},{"update_time":1542471714,"name":"agency.PNG","media_id":"GQeR4nw8oSE1DDtg8fjVFFxCw_CHNtfgkNY6bqNlImM","url":"http://mmbiz.qpic.cn/mmbiz_png/iaSibhmkIBzr18jNlBlf2IgTD7weTggMtic7t6AzLZ70H8v6ISYkQ63tOv0ibzFD8YJqdkR4PX99ODibUjQa8Hfcia6Q/0?wx_fmt=png","tags":[]},{"update_time":1542470433,"name":"Installation.jpg","media_id":"GQeR4nw8oSE1DDtg8fjVFEtj52zT9y-jtRW6erHicl8","url":"http://mmbiz.qpic.cn/mmbiz_jpg/iaSibhmkIBzr18jNlBlf2IgTD7weTggMticqqD8auGv01MMBLSbh6e6ibZSbB0MCfD4OVpCIRBSicnhv3XtMdanh8Ig/0?wx_fmt=jpeg","tags":[]},{"update_time":1542383690,"name":"banner.jpg","media_id":"GQeR4nw8oSE1DDtg8fjVFBgFMumGqawaWBvsto2Y3DA","url":"http://mmbiz.qpic.cn/mmbiz_jpg/iaSibhmkIBzr2O42dPu1qHbMa1P3MWomuwo3VWxCYzQG84Xe9DOibL6AbJQD8kCQjiaMqiaUtWeVgmiccgsILOLIJvcw/0?wx_fmt=jpeg","tags":[]},{"update_time":1542383052,"name":"banner.jpg","media_id":"GQeR4nw8oSE1DDtg8fjVFMN7jYtmA-V3rNFrLFFktUU","url":"http://mmbiz.qpic.cn/mmbiz_jpg/iaSibhmkIBzr2O42dPu1qHbMa1P3MWomuwo3VWxCYzQG84Xe9DOibL6AbJQD8kCQjiaMqiaUtWeVgmiccgsILOLIJvcw/0?wx_fmt=jpeg","tags":[]}],"total_count":8}
     */
    public JSONObject getMaterialList(String type, int offset, int count) {
        JSONObject param = new JSONObject();
        param.put("type", type);
        param.put("offset", offset);
        param.put("count", count);
        String uri = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + getAccessToken().getToken();
        String responseMsg = postJson(uri, param.toString());
        return JSONObject.parseObject(responseMsg);
    }

    /**
     * @param uri:请求链接
     * @param jsonData：请求json数据
     * @return
     */
    private String postJson(String uri, String jsonData) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonData, headers);
        String responseMsg = restTemplate.postForObject(uri, entity, String.class);
        return responseMsg;
    }


}
