package com.micwsx.project.advertise.utility;

import java.io.*;

public abstract class IOUtil {

    public static void saveIntoDoc(String fileName, byte[] body) {
        try {
            File targetFile = FileUtil.getFileInDoc(fileName);
            // 存在则覆盖
            FileOutputStream outputStream = new FileOutputStream(targetFile, false);
            outputStream.write(body);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(String fileName, String message) {
        String fullPath = FileUtil.getResourceRoot() + File.separator + fileName;
        System.out.println(fullPath);
        Writer writer = null;
        try {
            writer = new FileWriter(new File(fullPath), false);
            writer.write(message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        //saveIntoDoc("ccc.txt", new byte[0]);

//        AccessToken accessToken = new AccessToken("fdafewawefae", 7200);
//        String jsonObject = JSONObject.toJSONString(accessToken);
//        save("access.json", jsonObject);
    }
}
