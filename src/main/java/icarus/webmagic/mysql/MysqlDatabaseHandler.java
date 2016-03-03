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
            // һ��Connection����һ�����ݿ�����
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "select * from house where link='" + houseLink + "'";
            ResultSet resultSet = stmt.executeQuery(querySql);
            if (!resultSet.next()) {
                sql = "insert into house(link, isCrawled) values('" + houseLink + "', true)";
                int result = stmt.executeUpdate(sql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ�
                if (result != -1) {
                    System.out.println("���뷿Դ������Ϣ�ɹ�");
                }
            } else {
                System.out.println("�÷����Ѿ����ڣ�");
            }
        } catch (SQLException e) {
            System.out.println("MySQL��������");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public void deleteAllData() throws SQLException {
        Connection conn = null;
        // MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ
        // ������������Ҫָ��useUnicode��characterEncoding
        // ִ�����ݿ����֮ǰҪ�����ݿ����ϵͳ�ϴ���һ�����ݿ⣬�����Լ�����
        // �������֮ǰ��Ҫ�ȴ���javademo���ݿ�
        String url = "jdbc:mysql://localhost:3306/lianjiahouse?" + "user=root&password=123456";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // һ��Connection����һ�����ݿ�����
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "delete from house";
            int result = stmt.executeUpdate(querySql);
            if (result == 1)
                System.out.println("�ɹ�ɾ��house���е���������");
        } catch (SQLException e) {
            System.out.println("MySQL��������");
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
            // һ��Connection����һ�����ݿ�����
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "select * from house where isCrawled=true";
            ResultSet resultSet = stmt.executeQuery(querySql);
            while (resultSet.next()) {
                SimpleHouse sh = new SimpleHouse();
                String link = resultSet.getString("link");
                sh.setLink(link);
                //�ֶ�д���� ����
                sh.setCrawled(false);
                houseList.add(sh);
            }
        } catch (SQLException e) {
            System.out.println("MySQL��������");
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
            // һ��Connection����һ�����ݿ�����
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String querySql = "select * from house_detail where link='" + hd.getLink() + "';";
            ResultSet resultSet = stmt.executeQuery(querySql);
            if (resultSet.next()) {
                System.out.println("�÷�Դ��ϸ��Ϣ�Ѵ��ڣ�������");
                return;
            }
            String saveSql = createSaveDetailSql(hd);
            int result = stmt.executeUpdate(saveSql);
            if (result != 1) {
                System.out.println("������ϸ��Դ��Ϣʧ��");
                return;
            }
            System.out.println("������ϸ��Դ��Ϣ�ɹ�");
            String updateSql = "update house set isCrawled=false where link='" + hd.getLink() + "'";
            result = stmt.executeUpdate(updateSql);
            if (result != 1) {
                System.out.println("��Ҫ����Դ��Ϣ���Ϊ����ȡ��������ʧ��");
                return;
            }
            System.out.println("����Դ��Ϣ���Ϊ����ȡ��������ȡ�ɹ�");
        } catch (SQLException e) {
            System.out.println("MySQL��������");
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
