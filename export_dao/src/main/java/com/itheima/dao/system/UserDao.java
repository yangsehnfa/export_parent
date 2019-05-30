package com.itheima.dao.system;

import com.itheima.domian.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	int delete(String userId);

	//保存
	int save(User user);

	//更新
	int update(User user);

    List<String> RoleByUserId(String id);

	void changeRole(@Param("userid") String userid,@Param("roleid") String roleid);

	User findByEmail(String email);
}