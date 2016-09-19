package com.ws.process;


import com.ws.model.*;
import com.ws.save.SaveFile;
import com.ws.util.ImageDownloadUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.util.List;

import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * Created by laowang on 16-9-18.
 */
public class SDBSPicPageProcessor implements PageProcessor {
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/52.0.2743.116 Chrome/52.0.2743.116 Safari/537.36")
            .setSleepTime(1000)
            .setRetryTimes(3)
            .addCookie("SDBS_ENTRANCE_CK","ZHxo9OjZIFY")
            .addCookie("SDBS_IMG","d1xPwybPXNc")
            .addCookie("SDBS_CK","gdLDgdBhLsc")
            .addCookie("__utmt_cloud","1")
            .addCookie("__utmt_aist","1")
            .addCookie("SDBS_LANG","eng")
            .addCookie("__utma","200088940.653950373.1474173549.1474173549.1474173549.1")
            .addCookie("__utmb","200088940.26.9.1474173628923")
            .addCookie("__utmc","200088940")
            .addCookie("__utmz","200088940.1474173549.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");



    @Override
    public void process(Page page) {
        System.out.println("=============爬虫开始工作=============");

        String url = page.getUrl().toString();

        SaveFile saveFile = new SaveFile(page);

        //定义抽取信息，并保存信息
        if(url.contains("imgdir=ms")){
            saveFile.saveMs("a");
        }else if(url.contains("imgdir=cds")){
            saveFile.saveCnmr("b");
        }else if(url.contains("imgdir=hsp")){
            saveFile.saveHnmr("c");
        }else if(url.contains("imgdir=ir")){
            saveFile.saveIR("d");
        }else if(url.contains("imgdir=rm")){
            saveFile.saveRAMAN("e");
        }else if(url.contains("mgdir=esr")){
            saveFile.saveESR("f");
        }
        System.out.println("=============爬虫工作结束=============");
        //因为没有考虑下一页，这里不想着添加下一页
    }

    @Override
    public Site getSite() {
        return site;
    }

}