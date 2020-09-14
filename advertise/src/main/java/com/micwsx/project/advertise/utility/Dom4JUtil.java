package com.micwsx.project.advertise.utility;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Dom4JUtil {
    private static Logger logger = LoggerFactory.getLogger(Dom4JUtil.class);

    /**
     * 解析XML文件
     *
     * @param fullPath
     * @return
     */
    public static Element getRootElement(String fullPath) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new File(fullPath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        return rootElement;
    }


    /**
     * 解析XML字符串
     *
     * @param xml
     * @return
     */
    public static Element parse(String xml) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        return rootElement;
    }

    public static Map<String, String> parseXML2Map(String xml) {
        logger.info("xml:" + xml);
        Element root = parse(xml);
        HashMap<String, String> map = (HashMap<String, String>) root.elements()
                .stream()
                .collect(Collectors.toMap(obj -> ((DefaultElement) obj).getName(),
                        obj -> ((DefaultElement) obj).getText()));
        return map;
    }


    public static void main(String[] args) {
        String xml = "<xml>" +
                "<ToUserName><![CDATA[gh_f4634c7744c9]]></ToUserName>" +
                "<FromUserName><![CDATA[os7enjoshdMPTJIKWPpWcGXDiEIo]]></FromUserName>" +
                "<CreateTime>1547288550</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[subscribe]]></Event>" +
                "<EventKey><![CDATA[]]></EventKey>" +
                "</xml>";
        Map<String, String> map = parseXML2Map(xml);

        for (Map.Entry<String, String> kv : map.entrySet()) {
            System.out.println(kv.getKey() + ":" + kv.getValue());
        }


    }

}
