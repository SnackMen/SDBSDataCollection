package com.ws.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by laowang on 16-9-18.
 */
public class ImageDownloadUtils {
    private static Logger logger = Logger.getLogger(ImageDownloadUtils.class);

    public static synchronized void downLoadImage(String url,String filePath,String fileName,String picType) throws Exception{
        logger.info("----------文件开始下载-----------");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if(url==null || url.equals(""))
            return;
        //创建目标才存储目录
        File desPathFile = new File(filePath);
        System.out.println("filePath:"+filePath);
        if(!desPathFile.exists()){
            desPathFile.mkdirs();
            //创建此抽象路径指定的目录，包括所有必须但不存在的父目录
        }
        //得到文件绝对路径
        String fullPath = filePath+File.separator+fileName+"."+picType;
        System.out.println("完整路径:"+fullPath);
        System.out.println("下载url:"+url);
        logger.info("文件路径："+filePath);
        logger.info("文件名："+fileName);
        logger.info("源文件url"+url);
        //从源网址下载图片
        HttpGet httpget = new HttpGet(url);
//        httpget.setHeader("Accept","image/webp,image/*,*/*;q=0.8");
//        httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8");
//        httpget.setHeader("Accept-Encoding","gzip, deflate, sdch");
//        httpget.setHeader("Connection","keep-alive");
        httpget.setHeader("Cookie","SDBS_ENTRANCE_CK=Jb96qC306Mc; SDBS_CK=0JbgXEdZTos; SDBS_IMG=x/ae/QAPpno; __utma=200088940.1199044543.1473568841.1474203323.1474270090.280; __utmz=200088940.1473568841.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); SDBS_LANG=eng");
//        httpget.setHeader("Host","sdbs.db.aist.go.jp");
//        httpget.setHeader("Referer","http://sdbs.db.aist.go.jp/sdbs/cgi-bin/img_disp.cgi?disptype=disp1&amp;imgdir=ms&amp;fname=MSNW1671&amp;sdbsno=1");
//        httpget.setHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/52.0.2743.116 Chrome/52.0.2743.116 Safari/537.36");
        HttpResponse response = httpClient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        //设置下载地址
        File file = new File(fullPath);

        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp,0,l);
            }
            fout.flush();
            fout.close();
        }catch (FileNotFoundException e){
            logger.error("图片下载过程出错:"+url);
            e.printStackTrace();
        } finally {

            in.close();
        }
        logger.info("-------------------文件下载结束---------------------");



    }
}
