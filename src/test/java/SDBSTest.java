import com.ws.process.SDBSPicPageProcessor;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;

/**
 * Created by laowang on 16-9-18.
 */
public class SDBSTest {
    public static void main(String args[]){
        PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        Spider.create(new SDBSPicPageProcessor())
                .addUrl("http://sdbs.db.aist.go.jp/sdbs/cgi-bin/img_disp.cgi?disptype=disp1&amp;imgdir=esr&amp;fname=ESR0981&amp;sdbsno=96")
                .thread(3)
                .run();
    }
}
