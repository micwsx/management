package com.micwsx.project.advertise.utility;

import java.io.File;

public abstract class FileUtil {

    public static final String DOC = "doc";
    public static final String UPLOAD = "upload";

    public static File getResourceRoot() {
        File file = new File(FileUtil.class.getClassLoader().getResource("").getPath());
        return file;
    }

    /**
     * 获取doc目录下的文件File对象
     *
     * @param fileName:文件名包括后缀；e.g. demo.txt
     * @return
     */
    public static File getFileInDoc(String fileName) {
        return getFile(fileName,DOC);
    }

    public static File getFileInUpload(String fileName) {
        return getFile(fileName,UPLOAD);
    }


    /**
     *获取static目录一级目录下的文件File对象
     * @param fileName 文件名包括后缀；e.g. demo.txt
     * @param relativePathName
     * @return
     */
    private static File getFile(String fileName,String relativePathName) {
        File relativePath=getRelativePath(relativePathName);
        String fullPath = relativePath+File.separator + fileName;
        File targetFile = new File(fullPath);
        return targetFile;
    }

    private static File getRelativePath(String path) {
        String relativePath = FileUtil.getResourceRoot() + File.separator +
                "static" + File.separator + path;
        File targetFile = new File(relativePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        return targetFile;
    }

    /**
     * 根据文件获取文件的相对路径
     * @param file:
     * @return
     */
    public static String getRelativePath(File file) {
        String staticPath = FileUtil.getResourceRoot() + File.separator + "static";
        return file.getPath().replace(staticPath, "").replace(File.separator, "/");
    }


    public static void main(String[] args) {


//        File fileInDoc = getFileInDoc("ccc.txt");
//        System.out.println(fileInDoc.getPath());
//        System.out.println(fileInDoc.exists());
//
//        String relativePath = getRelativePath(fileInDoc);
//        System.out.println(relativePath);

    }


}
