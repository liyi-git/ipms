/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.base.dao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
/**
 * IBaseDao是一个公共dao接口类，被其他接口继承使用，无需重复定义操作数据库的方法，
 * 该接口提供了常用的操作数据库的方法，如果业务接口无需实现类完全基于接口编程直接继承使用
 * 即可，如果业务接口需要实现类，那么需要配合AbstractBaseDao继承使用，这样无需调用
 * 者在去实现IBaseDao里面的方法，因为AbstractBaseDao已经实现了IBaseDao接口
 * 的所有方法。
 * 
 * @param <T> 继承该类时，使用者传入当前业务接口使用的实体作为统一的泛型。
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public interface IBaseDao<T> {
    /**
     * 查询所有数据并返回泛型集合。<br/>
     * 详细描述：根据mapper中配置的id为selectAllForList的sql，查询出带有泛型的集合。<br/>
     * 使用方式：子类通过this.selectAllForList()方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
	public List<T> selectAllForList();
	
	/**
     * 根据参数id查询出符合条件的实体类型数据。<br/>
     * 详细描述：根据mapper中配置的id为selectByIdForEntity的sql，查询符合参数id的实体结果，mybatis会把参数id添加到sql条件中。<br/>
     * 使用方式：子类通过this.selectByIdForEntity(id)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param id 可以是任何一个实现了序列化接口的类型，可以是String、int等等。
     * @return 调用者在实现类上传入的实体类型，只返回一条数据。
     */
	public T selectByIdForEntity(Serializable id);
	
	/**
     * 根据参数t查询出符合条件的泛型集合。<br/>
     * 详细描述：根据mapper中配置的id为selectByEntityForList的sql，查询符合参数t的泛型集合，mybatis遍历对象t把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.selectByEntityForList(t)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param t 可以是实体、map、list等。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
	public List<T> selectByEntityForList(T t);
	
	/**
     * 根据参数map查询出符合条件的泛型集合。<br/>
     * 详细描述：根据mapper中配置的id为selectByMapForList的sql，查询符合参数map的泛型集合，mybatis遍历map把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.selectByMapForList(map)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @return 带有泛型的集合,集合内部类型是调用者在实现类上传入的实体类型。
     */
	public List<T> selectByMapForList(Map<String,Object> map);
	
	/**
     * 根据参数map和pageQuery查询出符合条件的泛型集合并带有分页条件。<br/>
     * 详细描述：根据mapper中配置的id为selectByMapForPage的sql，查询符合参数map和pageQuery的PageList对象，mybatis遍历map和pageQuery把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.selectByMapForPage(map,pageQuery)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @param pageQuery 分页对象，里面保存了分页和排序的信息，拦截器会自动实现分页查询功能。
     * @return 分页集合对象，里面包含了查询的泛型集合和分页对象信息。
     */
	public PageList<T> selectByMapForPage(Map<String,Object> map,PageQuery pageQuery);
	
	/**
     * 根据参数map和mapKey查询出符合条件的map。<br/>
     * 详细描述：根据mapper中配置的id为selectByMapForMap的sql，查询符合参数map和mapKey的map，如果mapKey值自定义的一个值，结果就只能返回最后一条记录，
     *        因为它是作为map的key重复后会被覆盖。如果传入的是结果的字段值，那查询出来的数据会根据表中该字段的值作为map中的key来保存，该查询方法只能在impl中调用
     *        selectMap方法才能执行，可直接使用AbstractBaseDao中的方法。<br/>
     * 使用方式：子类通过this.selectByMapForList(map)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @param mapKey 该参数可以是自定义值或者使用结果中的字段。
     * @return 根据mapKey的值作为结果集中数据map的key值。
     */
	public Map<Object,Object> selectByMapForMap(Map<String,Object> map, String mapKey);
	
	/**
     * 根据参数map查询出符合条件的结果数。<br/>
     * 详细描述：根据mapper中配置的id为selectCountForInt的sql，查询符合参数map的结果数，mybatis遍历map把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.selectCountForInt(map)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @return 查询结果集数量。
     */
	public Integer selectCountForInt(Map<String, Object> map);
	
	/**
     * 把参数t插入到数据库中。<br/>
     * 详细描述：根据mapper中配置的id为insert的sql，插入参数t到数据库，mybatis遍历对象t把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.insert(t)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param t 可以是实体、map、list等。
     */
	public void insert(T t);
	
	/**
     * 把参数t更新到数据库中。<br/>
     * 详细描述：根据mapper中配置的id为update的sql，更新参数t到数据库，mybatis遍历对象t把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.update(t)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param t 可以是实体、map、list等。
     */
	public void update(T t);
	
	/**
     * 根据参数id删除符合条件的记录。<br/>
     * 详细描述：根据mapper中配置的id为deleteById的sql，删除符合条件的记录，mybatis会把参数id添加到sql条件中。<br/>
     * 使用方式：子类通过this.deleteById(id)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param id 可以是任何一个实现了序列化接口的类型,可以是String、int等等。
     */
	public void deleteById(Serializable id);
	
	/**
     * 根据参数t删除符合条件的记录。<br/>
     * 详细描述：根据mapper中配置的id为deleteByEntity的sql，删除符合条件的记录，mybatis遍历对象t把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.deleteByEntity(t)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param t 可以是实体、map、list等。
     */
	public void deleteByEntity(T t);
	
	/**
     * 根据参数数组id删除符合条件的记录。<br/>
     * 详细描述：根据mapper中配置的id为deleteByEntity的sql，删除符合条件的记录，mybatis遍历数组id把值添加到sql条件中。<br/>
     * 使用方式：子类通过this.deleteByEntity(t)方式进行调用，同时也可以使用getSqlSession()调用mybatis的api。
     * @param id mybatis遍历数组把值添加到sql条件中。
     */
	public void deleteByIds(Serializable[] id);
}