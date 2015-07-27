/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.base.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
/**
 * IBaseService是一个公共service接口类，被其他service接口继承使用，
 * 无需重复定义，该接口提供了常用的业务层方法，调用者自定义service实现类需要
 * 继承AbstractBaseService配合使用，因为该类已经实现类IBaseService
 * 接口中所有的方法，无需调用者在去实现IBaseService中的方法。
 * 
 * @param <T> 继承该类时，使用者传入当前业务接口使用的实体作为统一的泛型。
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public interface IBaseService<T> {
    /**
     * 调用IBaseDao中的selectAllForList方法查询出所有数据。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectAllForList方法查询所有数据。<br/>
     * 使用方式：子类通过this.selectAllForList()方式进行调用。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
    public List<T> selectAllForList();
    
    /**
     * 调用IBaseDao中的selectByIdForEntity方法根据参数id查询出符合条件的实体类型数据。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByIdForEntity方法查询符合参数id的实体结果。<br/>
     * 使用方式：子类通过this.selectByIdForEntity(id)方式进行调用。
     * @param id 可以是任何一个实现了序列化接口的类型，可以是String、int等等。
     * @return 调用者在实现类上传入的实体类型，只返回一条数据。
     */
    public T selectByIdForEntity(Serializable id);
    
    /**
     * 调用IBaseDao中的selectByEntityForList方法根据参数t查询出符合条件的泛型集合。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByEntityForList方法查询符合参数t的泛型集合。<br/>
     * 使用方式：子类通过this.selectByEntityForList(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
    public List<T> selectByEntityForList(T t);
    
    /**
     * 调用IBaseDao中的selectByMapForList方法根据参数map查询出符合条件的泛型集合。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByMapForList方法查询符合参数map的泛型集合。<br/>
     * 使用方式：子类通过this.selectByMapForList(map)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
    public List<T> selectByMapForList(Map<String,Object> map);
    
    /**
     * 调用IBaseDao中的selectByMapForPage方法根据参数map和pageQuery查询出符合条件的泛型集合并带有分页条件。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByMapForPage方法查询符合参数map和pageQuery的PageList对象。<br/>
     * 使用方式：子类通过this.selectByMapForPage(map,pageQuery)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @param pageQuery 分页对象，里面保存了分页和排序的信息，拦截器会自动实现分页查询功能。
     * @return 分页集合对象，里面包含了查询的泛型集合和分页对象信息。
     */
    public PageList<T> selectByMapForPage(Map<String,Object> map,PageQuery pageQuery);
    
    /**
     * 调用IBaseDao中的selectByMapForMap方法根据参数map和mapKey查询出符合条件的map。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByMapForMap方法查询符合参数map和mapKey的map，
     *        如果mapKey值自定义的一个值，结果就只能返回最后一条记录，因为它是作为map的key重复后会被覆盖。如果传入的是
     *        结果的字段值，那查询出来的数据会根据表中该字段的值作为map中的key来保存。<br/>
     * 使用方式：子类通过this.selectByMapForList(map)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @param mapKey 该参数可以是自定义值或者使用结果中的字段。
     * @return 根据mapKey的值作为结果集中数据map的key值。
     */
    public Map<Object,Object> selectByMapForMap(Map<String,Object> map,String mapKey);
    
    /**
     * 调用IBaseDao中的selectCountForInt方法根据参数map查询出符合条件的结果数。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectCountForInt方法查询符合参数map的结果数。<br/>
     * 使用方式：子类通过this.selectCountForInt(map)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @return 查询结果集数量。
     */
    public Integer selectCountForInt(Map<String, Object> map);
    
    /**
     * 调用IBaseDao中的insert方法把参数t插入到数据库中。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用insert方法插入参数t到数据库。<br/>
     * 使用方式：子类通过this.insert(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     */
    public void insert(T t);
    
    /**
     * 调用IBaseDao中的update方法把参数t更新到数据库中。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用update方法更新参数t到数据库。<br/>
     * 使用方式：子类通过this.update(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     */
    public void update(T t);
    
    /**
     * 调用IBaseDao中的deleteById方法根据参数id删除符合条件的记录。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用deleteById方法删除符合条件的记录。<br/>
     * 使用方式：子类通过this.deleteById(id)方式进行调用。
     * @param id 可以是任何一个实现了序列化接口的类型，可以是String、int等等。
     */
    public void deleteById(Serializable id);
    
    /**
     * 调用IBaseDao中的deleteByEntity方法根据参数t删除符合条件的记录。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用deleteByEntity方法删除符合条件的记录。<br/>
     * 使用方式：子类通过this.deleteByEntity(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     */
    public void deleteByEntity(T t);
    
    /**
     * 调用IBaseDao中的deleteByIds方法根据参数数组id删除符合条件的记录。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用deleteByIds方法删除符合条件的记录。<br/>
     * 使用方式：子类通过this.deleteByEntity(t)方式进行调用。
     * @param id mybatis遍历数组把值添加到sql条件中。
     */
    public void deleteByIds(Serializable[] id);
}