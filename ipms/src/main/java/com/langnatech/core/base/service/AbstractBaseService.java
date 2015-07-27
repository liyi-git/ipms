/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.base.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.langnatech.core.base.dao.IBaseDao;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
/**
 * AbstractBaseService是一个抽象类，该类实现了IBaseService接口，作为此接口的实现类，
 * 如果业务层的service有实现类可以继承此抽象类，但同时需要重写getDao方法，为了供全局的其他方
 * 法进行统一调用，可以通过注入的dao来调用该抽象类中提供的统一方法，无需在重复定义，如果不能满足调
 * 用者的需求在进行相应方法的定义。
 * 
 * @param <T> 继承该类时，使用者传入当前业务接口使用的实体作为统一的泛型。
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public abstract class AbstractBaseService<T> implements IBaseService<T> {
    /**
     * 该方法返回子类注入的dao，该dao必须继承IBaseDao接口。<br/>
     * 详细描述：子类service继承该类后，需重写该方法，并把注入的dao作为该方法的返回值(注意：注入的dao必须继承IBaseDao)，
     *        然后就可以调用AbstractBaseService中提供的统一方法了。<br/>
     * 使用方式：重写该方法后，只需要把子类中注入的dao作为该方法的返回值，就可以直接调用AbstractBaseService中提供的方法了。
     * @return 子类注入的dao，但该dao必须继承IBaseDao接口，因为返回的类型是IBaseDao。
     */
    protected abstract IBaseDao<T> getDao();

    /**
     * 调用IBaseDao中的selectAllForList方法查询出所有数据。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectAllForList方法查询所有数据。<br/>
     * 使用方式：子类通过this.selectAllForList()方式进行调用。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
    public List<T> selectAllForList() {
        return this.getDao().selectAllForList();
    }
    
    /**
     * 调用IBaseDao中的selectByIdForEntity方法根据参数id查询出符合条件的实体类型数据。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByIdForEntity方法查询符合参数id的实体结果。<br/>
     * 使用方式：子类通过this.selectByIdForEntity(id)方式进行调用。
     * @param id 可以是任何一个实现了序列化接口的类型，可以是String、int等等。
     * @return 调用者在实现类上传入的实体类型，只返回一条数据。
     */
    public T selectByIdForEntity(Serializable id) {
        return (T)this.getDao().selectByIdForEntity(id);
    }
    
    /**
     * 调用IBaseDao中的selectByEntityForList方法根据参数t查询出符合条件的泛型集合。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByEntityForList方法查询符合参数t的泛型集合。<br/>
     * 使用方式：子类通过this.selectByEntityForList(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
    public List<T> selectByEntityForList(T t) {
        return this.getDao().selectByEntityForList(t);
    }
    
    /**
     * 调用IBaseDao中的selectByMapForList方法根据参数map查询出符合条件的泛型集合。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByMapForList方法查询符合参数map的泛型集合。<br/>
     * 使用方式：子类通过this.selectByMapForList(map)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @return 带有泛型的集合，集合内部类型是调用者在实现类上传入的实体类型。
     */
    public List<T> selectByMapForList(Map<String, Object> map) {
        return this.getDao().selectByMapForList(map);
    }
    
    /**
     * 调用IBaseDao中的selectByMapForPage方法根据参数map和pageQuery查询出符合条件的泛型集合并带有分页条件。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByMapForPage方法查询符合参数map和pageQuery的PageList对象。<br/>
     * 使用方式：子类通过this.selectByMapForPage(map,pageQuery)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @param pageQuery 分页对象，里面保存了分页和排序的信息，拦截器会自动实现分页查询功能。
     * @return 分页集合对象，里面包含了查询的泛型集合和分页对象信息。
     */
    public PageList<T> selectByMapForPage(Map<String, Object> map, PageQuery pageQuery) {
        return this.getDao().selectByMapForPage(map, pageQuery);
    }
    
    /**
     * 调用IBaseDao中的selectByMapForMap方法根据参数map和mapKey查询出符合条件的map。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectByMapForMap方法查询符合参数map和mapKey的map，
     *        如果mapKey值自定义的一个值，结果就只能返回最后一条记录，因为它是作为map的key重复后会被覆盖。如果传入的是
     *        结果的字段值。那查询出来的数据会根据表中该字段的值作为map中的key来保存。<br/>
     * 使用方式：子类通过this.selectByMapForList(map)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @param mapKey 该参数可以是自定义值或者使用结果中的字段。
     * @return 根据mapKey的值作为结果集中数据map的key值。
     */
    public Map<Object, Object> selectByMapForMap(Map<String, Object> map, String mapKey) {
        return this.getDao().selectByMapForMap(map, mapKey);
    }
    
    /**
     * 调用IBaseDao中的selectCountForInt方法根据参数map查询出符合条件的结果数。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用selectCountForInt方法查询符合参数map的结果数。<br/>
     * 使用方式：子类通过this.selectCountForInt(map)方式进行调用。
     * @param map mybatis遍历map把值添加到sql条件中。
     * @return 查询结果集数量。
     */
    public Integer selectCountForInt(Map<String, Object> map) {
        return this.getDao().selectCountForInt(map);
    }

    /**
     * 调用IBaseDao中的insert方法把参数t插入到数据库中。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用insert方法插入参数t到数据库。<br/>
     * 使用方式：子类通过this.insert(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     */
    public void insert(T t) {
        this.getDao().insert(t);
    }
    
    /**
     * 调用IBaseDao中的update方法把参数t更新到数据库中。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用update方法更新参数t到数据库。<br/>
     * 使用方式：子类通过this.update(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     */
    public void update(T t) {
        this.getDao().update(t);
    }
    
    /**
     * 调用IBaseDao中的deleteById方法根据参数id删除符合条件的记录。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用deleteById方法删除符合条件的记录。<br/>
     * 使用方式：子类通过this.deleteById(id)方式进行调用。
     * @param id 可以是任何一个实现了序列化接口的类型，可以是String、int等等。
     */
    public void deleteById(Serializable id) {
        this.getDao().deleteById(id);
    }
    
    /**
     * 调用IBaseDao中的deleteByEntity方法根据参数t删除符合条件的记录。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用deleteByEntity方法删除符合条件的记录。<br/>
     * 使用方式：子类通过this.deleteByEntity(t)方式进行调用。
     * @param t 可以是实体、map、list等。
     */
    public void deleteByEntity(T t) {
        this.getDao().deleteByEntity(t);
    }
    
    /**
     * 调用IBaseDao中的deleteByIds方法根据参数数组id删除符合条件的记录。<br/>
     * 详细描述：自定义service实现类继承该类后，可直接调用deleteByIds方法删除符合条件的记录。<br/>
     * 使用方式：子类通过this.deleteByEntity(t)方式进行调用。
     * @param id mybatis遍历数组把值添加到sql条件中。
     */
    public void deleteByIds(Serializable[] id) {
        this.getDao().deleteByIds(id);
    }
}