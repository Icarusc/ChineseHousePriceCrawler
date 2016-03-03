package icarus.webmagic.model;

import icarus.webmagic.mysql.MysqlDatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class SimpleHouseHolder {
    public static List<SimpleHouse>     houseList = new ArrayList<SimpleHouse>();
    public static Iterator<SimpleHouse> houseIter = houseList.iterator();
    static {
        MysqlDatabaseHandler handler = new MysqlDatabaseHandler();
        try {
            houseList = handler.getHouseToProcess();
        } catch (SQLException e) {
            System.out.println("query data error!");
        }
    }
}
