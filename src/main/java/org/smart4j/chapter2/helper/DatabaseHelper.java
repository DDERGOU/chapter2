package org.smart4j.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.CollectionUtil;
import org.smart4j.chapter2.util.PropsUtil;
import org.smart4j.chapter2.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 * Created by ZDD on 2017/5/14.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER;
//---->改用Apache dbcp线程池
//    private static final String DRIVER;
//    private static final String URL;
//    private static final String USERNAME;
//    private static final String PASSWORD;
//
    //创建数据库连接变量放进ThreadLocal里，ThreadLocal隔离线程，保证线程安全
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    //定义DBCP数据源
    private static final BasicDataSource DATA_SOURCE;

    static {

//---->改用Apache dbcp线程池
//        Properties props = PropsUtil.loadProps("config.properties");
//        DRIVER = props.getProperty("jdbc.driver");
//        URL = props.getProperty("jdbc.url");
//        USERNAME = props.getProperty("jdbc.username");
//        PASSWORD = props.getProperty("jdbc.password");
//
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("can not load jdbc driver", e);
//        }

        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        DATA_SOURCE = new BasicDataSource();

        Properties props = PropsUtil.loadProps("config.properties");
        DATA_SOURCE.setDriverClassName(props.getProperty("jdbc.driver"));
        DATA_SOURCE.setUrl(props.getProperty("jdbc.url"));
        DATA_SOURCE.setUsername(props.getProperty("jdbc.username"));
        DATA_SOURCE.setPassword(props.getProperty("jdbc.password"));
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
//        利用驱动管理类生成新的连接变量
//        try {
//            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            LOGGER.error("get connection failure", e);
//        }

        //利用ThreaLocal存储数据库连接
//        if (conn == null){
//            try {
//                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            }catch (SQLException e){
//                LOGGER.error("get connection failure", e);
//                throw new RuntimeException();
//            }finally {
//                CONNECTION_HOLDER.set(conn);
//            }
//        }
        //---->改用Apache dbcp线程池
        if (conn == null){
            try {
                conn = DATA_SOURCE.getConnection();
            }catch (SQLException e){
                LOGGER.error("get connection from dbcp failure", e);
                throw new RuntimeException();
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * 正常接收连接，然后判断关闭
     */
    //----> 已使用DBCP线程池
//    public static void closeConnection(Connection conn){
//
//        if (conn != null){
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                LOGGER.error("close connection failure", e);
//            }
//        }
//    }

    /**
     * 关闭ThreadLocal里的数据库连接
     */
    //----> 已使用DBCP线程池
//    public static void closeConnection(){
//        Connection conn = CONNECTION_HOLDER.get();
//        if (conn != null){
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                LOGGER.error("close connection (ThreadLocal) failure", e);
//                throw new RuntimeException();
//            }finally{
//                CONNECTION_HOLDER.remove();//使用完毕之后需要移除ThreadLocal中持有的Connection
//            }
//        }
//    }

    /**
     * 获取客户列表 版本2：分离共性代码
     */
    public List<Customer> getCustomerList(){
        Connection conn = null;
        List<Customer> customerList = new ArrayList<Customer>();
        String sql = "SELECT * FROM tbl_customer";
        try {
            conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Customer customer = new Customer();
                customer.setPk_id(rs.getLong("pk_id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //----> 已使用DBCP线程池
//        finally {
//            DatabaseHelper.closeConnection(conn);
//        }
        return null;
    }

    /**
     * 版本3：查询实体列表
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    //<T>List<T>说明：第一个T 是声明方法引入了类型变量T    第二个T是List引入类型变量T
    public static <T>List<T> queryEntityList(Class<T> entityClass, Connection conn, String sql, Object...params){
        List<T> entityList = null;
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException();
        }
        //----> 已使用DBCP线程池
        // finally{
//            DatabaseHelper.closeConnection(conn);
//        }
        return entityList;
    }

    /**
     * 版本3.1：查询实体列表，利用ThreadLocal里的数据库连接
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    //<T>List<T>说明：第一个T 是声明方法引入了类型变量T    第二个T是List引入类型变量T
    public static <T>List<T> queryEntityList(Class<T> entityClass, String sql, Object...params){
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
        //----> 已使用DBCP线程池
//        finally{
//            closeConnection();
//        }
        return entityList;
    }

    /**
     * 查询单个实体
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T>T queryEntity(Class<T> entityClass, String sql, Object...params){
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass),params);
        } catch (Exception e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        }
        //----> 已使用DBCP线程池
//        finally {
//            closeConnection();
//        }
        return entity;
    }

    /**
     * 执行查询语句
     * @param sql    自定义的查询的Sql语句
     * @param params 按顺序将参数填充占位符
     * @return  返回一个List<Map<K, V>>对象，查询的字段对应映射到对象里（待验证）
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object...params){
        List<Map<String, Object>> result = null;
        Connection conn = getConnection();
        try {
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("executeQuery failure", e);
            throw new RuntimeException(e);
        }
        //----> 已使用DBCP线程池
//        finally{
//            closeConnection();
//        }
        return result;
    }

    /**
     * 执行更新语句（包括update, insert, delete）
     * @param sql     自定义更新swl语句
     * @param params  按顺序将参数填充到占位符
     * @return 返回受影响行数
     */
    public static int executeUpdate(String sql, Object...params){
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute Update failure", e);
            throw new RuntimeException(e);
        }
        //----> 已使用DBCP线程池
//        finally {
//            closeConnection();
//        }
        return rows;
    }

    /**
     * 插入单个实体
     */
    public static <T>boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap){
        if (CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not insert entity: fieldMap id empty");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder colums = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()){
            colums.append(fieldName).append(", ");
            values.append("?, ");
        }
        colums.replace(colums.lastIndexOf(", "), colums.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += colums + " VALUES " + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新单个实体
     */
    public static <T>boolean updateEntity(Class<T> entityClass, long pk_id, Map<String, Object> fieldMap){
        if (CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("can not uodate entity: fieldMap id empty");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder colums = new StringBuilder();
        for (String fieldName : fieldMap.keySet()){
            colums.append(fieldName).append(" = ?, ");
        }
        sql += colums.substring(0, colums.lastIndexOf(", ")) + " WHERE pk_id = ?";

        List<Object> objectList = new ArrayList<Object>();
        objectList.addAll(fieldMap.values());
        objectList.add(pk_id);
        Object[] params = objectList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除单个实体
     */
    public static <T>boolean deleteEntity(Class<T> classEntity, long pk_id){
        String sql = "DELETE FROM " + getTableName(classEntity) + " WHERE pk_id = ?";
        return  executeUpdate(sql, pk_id) == 1;
    }


    /**
     * 执行路径的sql文件
     * @param filePath
     */
    public static void executeSqlFile(String filePath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String sql;
        try {
            while (StringUtil.isNotEmpty(sql = reader.readLine())){
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

    private static final String getTableName(Class<?> entityElass){
        return entityElass.getSimpleName();
    }
}
