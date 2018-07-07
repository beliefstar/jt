package com.jtboot.sso.mapper;

import com.jtboot.sso.pojo.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
//    List<User>
    User findUserByUP(@Param("username") String username, @Param("password") String password);
}
