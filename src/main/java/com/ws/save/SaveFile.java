package com.ws.save;

import com.ws.file.GetFile;
import com.ws.model.*;
import us.codecraft.webmagic.Page;

/**
 * Created by laowang on 16-9-19.
 */
public class SaveFile {
    private Page page;

    public SaveFile(Page page){
        this.page = page;
    }

    private GetFile getFile=null;

    //存储ms的所有信息
    public synchronized void saveMs( String fileName){
        getFile = new GetFile(page);
        getFile.processPictureAndText(page,new MS());//文字采集
        getFile.processPicture(page,fileName);//图片采集
    }

    //存储cnmr所有信息
    public synchronized void saveCnmr( String fileName){
        getFile = new GetFile(page);
        getFile.processText(page,new CNMR());//文字采集
        getFile.processPicture(page,fileName);//第一张图片采集
        getFile.processSecondPicture(page,fileName+"1");//第二张图片采集
    }

    public synchronized void saveHnmr(String fileName){
        getFile = new GetFile(page);
        getFile.processText(page,new HNMR());//文字采集
        getFile.processPicture(page,fileName);//第一张图片采集
        getFile.processSecondPicture(page,fileName+"1");//第二张图片采集
    }

    public synchronized void saveIR(String fileName){
        getFile = new GetFile(page);
        getFile.processPicture(page,fileName);//图片采集
    }

    public synchronized  void saveRAMAN(String fileName){
        getFile = new GetFile(page);
        getFile.processPicture(page,fileName);//图片采集
    }

    public synchronized void saveESR(String fileName){
        getFile = new GetFile(page);
        getFile.processText(page,new ESR());//文字采集
        getFile.processPicture(page,fileName);//图片采集
    }
}
