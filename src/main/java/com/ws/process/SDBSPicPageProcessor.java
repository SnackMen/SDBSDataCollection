package com.ws.process;


import com.ws.save.SaveFile;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * Created by laowang on 16-9-18.
 */
public class SDBSPicPageProcessor implements PageProcessor {
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/52.0.2743.116 Chrome/52.0.2743.116 Safari/537.36")
            .setSleepTime(2000)
            .setRetryTimes(3)
            .setCharset("utf-8")
            .setCycleRetryTimes(10)
            .addCookie("SDBS_ENTRANCE_CK","Jb96qC306Mc")
            .addCookie("SDBS_IMG","x/ae/QAPpno")
            .addCookie("SDBS_CK","0JbgXEdZTos")
            .addCookie("__utmt_cloud","1")
            .addCookie("__utmt_aist","1")
            .addCookie("SDBS_LANG","eng")
            .addCookie("__utma","200088940.1199044543.1473568841.1474203323.1474270090.280")
            .addCookie("__utmb","200088940.26.9.1474173628923")
            .addCookie("__utmc","200088940")
            .addCookie("__utmz","200088940.1473568841.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");

    Logger logger = Logger.getLogger(SDBSPicPageProcessor.class);

    @Override
    public void process(Page page) {
        System.out.println("=============爬虫开始工作=============");
        String url = page.getUrl().toString();
        SaveFile saveFile = new SaveFile(page);
        //定义抽取信息，并保存信息
        try{
            if(url.contains("imgdir=ms")){
//                String []strngUrls = url.split("sdbsno=");
//                saveFile.saveMs("ms/"+strngUrls[1]+"_ms",Integer.valueOf(strngUrls[1]));
                System.out.println("ms");
            }else if(url.contains("imgdir=cds")){
//                String []strngUrls = url.split("sdbsno=");
//                saveFile.saveCnmr("cnmr/"+strngUrls[1]+"_cnmr",Integer.valueOf(strngUrls[1]));
                    System.out.println("cds");
            }else if(url.contains("imgdir=hsp" )|| url.contains("imgdir=hpm")){
//                String []strngUrls = url.split("sdbsno=");
//                saveFile.saveHnmr("hnmr/"+strngUrls[1]+"_hnmr",Integer.valueOf(strngUrls[1]));
                System.out.println("hpm");
            } else if(url.contains("imgdir=ir2")){
                String []strngUrls = url.split("sdbsno=");
                saveFile.saveIR2("ir/"+strngUrls[1]+"_ir",Integer.valueOf(strngUrls[1]));
            }else if(url.contains("imgdir=rm")){
//                String []strngUrls = url.split("sdbsno=");
//                saveFile.saveRAMAN("raman/"+strngUrls[1]+"_raman",Integer.valueOf(strngUrls[1]));
                System.out.println("rm");
            }else if (url.contains("mgdir=esr")) {
//                    String[] strngUrls = url.split("sdbsno=");
//                    saveFile.saveESR("esr/" + strngUrls[1] + "_esr", Integer.valueOf(strngUrls[1]));
                System.out.println("esr");
            } else if(url.contains("imgdir=ir")){
                System.out.println("ir");
            } else {
                logger.warn("url无法匹配:" + url);
                throw new IndexOutOfBoundsException("url无法匹配:" + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=============爬虫工作结束=============");
    }

    @Override
    public Site getSite() {
        return site;
    }


}