package org.smart4j.chapter2.service;

import org.smart4j.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * 提供 用户数据 服务
 * （在标准的MVC框架总是没哟服务层的，我们将该层作为衔接控制器层和数据库之间的桥梁）
 * Created by ZDD on 2017/5/13.
 */
public class CustomerService {
    /**
     * 获取客户列表
     */
    public List<Customer> getCustomerList(){
        //TODO
        return null;
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(long id){
        //TODO
        return null;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap){
        //TODO
        return false;
    }

    /**
     * 更新用户
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap){
        //TODO
        return false;
    }

    /**
     * 删除用户
     */
    public boolean deleteCustomer(long id){
        //TODO
        return false;
    }
}
