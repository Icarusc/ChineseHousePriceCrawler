package icarus.webmagic.mysql;

import icarus.webmagic.model.HouseDetail;
import icarus.webmagic.model.SimpleHouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlDatabaseHandler {

    public void saveHouseInfo(String houseLink) throws SQLException {
        Connection conn = null;
        String sql;
        String url = "jdbc:mysql://localhost:3306/lianjiahouse?" + "user=root&password=123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "select * from house where link='" + houseLink + "'";
            ResultSet resultSet = stmt.executeQuery(querySql);
            if (!resultSet.next()) {
                sql = "insert into house(link, isCrawled) values('" + houseLink + "', true)";
                int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
                if (result != -1) {
                    System.out.println("插入房源链接信息成功");
                }
            } else {
                System.out.println("该房屋已经存在！");
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public void deleteAllData() throws SQLException {
        Connection conn = null;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/lianjiahouse?" + "user=root&password=123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "delete from house";
            int result = stmt.executeUpdate(querySql);
            if (result == 1)
                System.out.println("成功删除house表中的所有数据");
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public List<SimpleHouse> getHouseToProcess() throws SQLException {
        List<SimpleHouse> houseList = new ArrayList<SimpleHouse>();
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/lianjiahouse?" + "user=root&password=123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "select * from house where isCrawled=true";
            ResultSet resultSet = stmt.executeQuery(querySql);
            while (resultSet.next()) {
                SimpleHouse sh = new SimpleHouse();
                String link = resultSet.getString("link");
                sh.setLink(link);
                //字段写反了 沃日
                sh.setCrawled(false);
                houseList.add(sh);
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return houseList;
    }

    public static final void main(String[] args) throws SQLException {
        MysqlDatabaseHandler handler = new MysqlDatabaseHandler();
        handler.saveHouseInfo("1234");
        handler.deleteAllData();
    }

    public void saveHouseDetailInfo(HouseDetail hd) throws SQLException {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/lianjiahouse?" + "user=root&password=123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "select * from house_detail where link='" + hd.getLink() + "';";
            ResultSet resultSet = stmt.executeQuery(querySql);
            if (resultSet.next()) {
                System.out.println("该房源详细信息已存在，不保存");
                return;
            }
            String saveSql = createSaveDetailSql(hd);
            int result = stmt.executeUpdate(saveSql);
            if (result != 1) {
                System.out.println("插入详细房源信息失败");
                return;
            }
            System.out.println("插入详细房源信息成功");
            String updateSql = "update house set isCrawled=false where link='" + hd.getLink() + "'";
            result = stmt.executeUpdate(updateSql);
            if (result != 1) {
                System.out.println("想要将房源信息标记为已爬取，但操作失败");
                return;
            }
            System.out.println("将房源信息标记为已爬取，本次爬取成功");
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return;
    }

    private String createSaveDetailSql(HouseDetail hd) {
        return "insert into house_detail(add_time, link, title, price_per_square, total_price, total_area, house_type, house_estate, built_year, house_towards, house_floor, seller, seller_phone_number) "
               + "values( now(), '"
               + hd.getLink()
               + "','"
               + hd.getTitle()
               + "', "
               + hd.getPricePerSquare()
               + ", "
               + hd.getTotalPrice()
               + ", "
               + hd.getTotalArea()
               + ", '"
               + hd.getHouseType()
               + "', '"
               + hd.getHouseEstate()
               + "', "
               + hd.getBuiltYear()
               + ", '"
               + hd.getHouseTowards()
               + "', '"
               + hd.getHouseFloor()
               + "', '"
               + hd.getSeller()
               + "', '"
               + hd.getSellerPhone() + "')";
    }

}
