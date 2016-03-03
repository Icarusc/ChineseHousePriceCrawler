package icarus.webmagic;

import icarus.webmagic.model.SimpleHouse;
import icarus.webmagic.mysql.MysqlDatabaseHandler;
import icarus.webmagic.processor.LianjiaGetHousesProcessor;
import icarus.webmagic.processor.LianjiaHouseDetailProcessor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class CrawlerImpl {
    public static List<SimpleHouse> houseList;
    static {
        MysqlDatabaseHandler handler = new MysqlDatabaseHandler();
        try {
            houseList = handler.getHouseToProcess();
            System.out.println("本次共抽取房源" + houseList.size() + "条。");
        } catch (SQLException e) {
            System.out.println("query data error!");
        }
    }

    public void getHouseLink() {
        List<String> pageLinks = new ArrayList<String>();
        for (int i = 1; i <= 100; i++) {
            String pageLink = "http://sh.lianjia.com/ershoufang/pg" + i + "a2bp180ep210/";
            pageLinks.add(pageLink);
            Spider.create(new LianjiaGetHousesProcessor()).addUrl(pageLink)
                .addPipeline(new ConsolePipeline()).start();
        }
    }

    public void getHouseDetail() {
        Iterator<SimpleHouse> iter = houseList.iterator();
        while (iter.hasNext()) {
            List<SimpleHouse> list = new ArrayList<SimpleHouse>();
            for (int i = 0; i < 20; i++) {
                if (!iter.hasNext())
                    break;
                list.add(iter.next());
            }
            Spider.create(new LianjiaHouseDetailProcessor(list)).addUrl("http://www.lianjia.com")
                .addPipeline(new ConsolePipeline()).start();
        }
    }

    public static final void main(String[] args) {
        CrawlerImpl crawler = new CrawlerImpl();
        //crawler.getHouseLink();

        crawler.getHouseDetail();
    }
}
