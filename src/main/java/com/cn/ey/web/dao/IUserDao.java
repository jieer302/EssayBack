package com.cn.ey.web.dao;

import java.util.List;
import java.util.Map;

import com.cn.ey.web.pojo.User;

/****
 *creater : wuliu
 *date : 2019/08/23 
 ****/

public interface IUserDao {
  
    int insert(Map map);
    
    public List<User> selectByUserName(String userName);
    
    public int updateById(User user);
    
    public List<User> selectByState(int state);
   
  
}