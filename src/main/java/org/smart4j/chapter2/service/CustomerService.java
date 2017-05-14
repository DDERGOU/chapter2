package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;




/**
 * 提供 用户数据 服务
 * （在标准的MVC框架总是没哟服务层的，我们将该层作为衔接控制器层和数据库之间的桥梁）
 * Created by ZDD on 2017/5/13.
 * 版本1：CustomerService，原始做法，弊端：1、CustomerService类中读取config.properties文件，违反了类单一责任原则；
 *                        2、代码重复，每次执行sql都必须获取和关闭连接，还要使用try...catch...。
 *
 * 版本2：DatabaseHelper,减缓版本1的弊端1，分离共性代码。使用方法独立数据库连接的创建和关闭，解耦出静态代码和公共代码；
 *
 * 版本3：DatabaseHelper,利用DbUtils类库（反射映射匹配集合），解决版本2没解决的弊端2，
 *
 */
public class CustomerService {
    /**
     * 获取客户列表 版本2：建立关闭数据库连接代码已删除
     */
//    public List<Customer> getCustomerList(){
//        Connection conn = null;
//        List<Customer> customerList = new ArrayList<Customer>();
//        String sql = "SELECT * FROM tbl_customer";
//        try {
//            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()){
//                Customer customer = new Customer();
//                customer.setId(rs.getLong("pk_id"));
//                customer.setName(rs.getString("name"));
//                customer.setContact(rs.getString("contact"));
//                customer.setTelephone(rs.getString("telephone"));
//                customer.setEmail(rs.getString("email"));
//                customer.setRemark(rs.getString("remark"));
//                customerList.add(customer);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            if (conn != null)
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error("close connection failure", e);
//                }
//        }
//        return customerList;
//    }

    /**
     * 获取客户列表 版本3.0
     */
//    public List<Customer> getCustomerList(){
//        Connection conn = null;
//        List<Customer> customerList = null;
//        try {
//            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            String sql = "SELECT * FROM tbl_customer";
//            customerList = DatabaseHelper.queryEntityList(Customer.class, conn, sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            DatabaseHelper.closeConnection(conn);
//        }
//        return customerList;
//    }

    /**
     * 获取客户列表 版本3.1  利用使用ThreadLocal管理数据库连接的queryEntityList
     */
    public List<Customer> getCustomerList(){
        String sql = "SELECT * FROM Customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }


    /**
     * 获取客户
     */
    public Customer getCustomer(long id){
        StringBuffer sql = new StringBuffer("SELECT * FROM Customer WHERE pk_id = ");
        sql.append(String.valueOf(id));
        return DatabaseHelper.queryEntity(Customer.class, sql.toString());
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap){
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    /**
     * 更新用户
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap){
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * 删除用户
     */
    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
