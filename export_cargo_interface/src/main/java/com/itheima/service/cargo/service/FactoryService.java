package com.itheima.service.cargo.service;

import com.itheima.domian.cargo.Factory;
import com.itheima.domian.cargo.FactoryExample;

import java.util.List;

/**
 */
public interface FactoryService {

	/**
	 * 保存
	 */
	void save(Factory factory);

	/**
	 * 更新
	 */
	void update(Factory factory);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	Factory findById(String id);

	//查询所有
	public List<Factory> findAll(FactoryExample example);

    Factory findByName(String factoryName);
}
