/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.extend.pagination;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.util.LoggerUtil;
/**
 * 实现将分页List转换为json的Mapper。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class PageListJsonMapper extends ObjectMapper {
    private static final long serialVersionUID = 7263357893058902658L;
    private static final Logger logger = LoggerFactory.getLogger(PageListJsonMapper.class);
    
    /**
     * PageListJsonMapper默认的无参构造器。<br/>
     * 详细描述：PageListJsonMapper无参构造，为了实现注册序列化器。<br/>
     * 使用方式：实例化PageListJsonMapper类，自动调用无参构造。
     */
    public PageListJsonMapper() {
        SimpleModule module = new SimpleModule("PageListJSONModule", new Version(1, 0, 0, null,null, null));
        module.addSerializer(PageList.class, new PageListJsonSerializer());
        registerModule(module);
    }
    
    /**
     * @Title: getData
     * @Description: 拼装图表数据
     * @param list 数据库获取到的数据
     * @return ObjectNode 前台展示数据
     */
    public static String getDataTablesJson(PageQuery pageQuery, PageList<Map<String, Object>> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objNode = objectMapper.createObjectNode();
        objNode.put("iTotalRecords", list.getPaginator().getTotalCount());
        objNode.put("iTotalDisplayRecords", list.getPaginator().getTotalCount());
        try {
            objNode.putPOJO("aaData", objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            LoggerUtil.debug(logger, "拼装json数据异常：[{}]", e.getMessage());
        }
        return objNode.toString();
    }
}


@SuppressWarnings("rawtypes")
class PageListJsonSerializer extends JsonSerializer<PageList> {
    /**
     * 重写JsonSerializer中的初始化序列方法。<br/>
     * 详细描述：自定义Jackson的Serializer，使生成的json自动拼上totalCount和items的值转换为表格的数据格式。<br/>
     * 使用方式：直接实例化PageListJsonMapper，其操作类似于实例化ObjectMapper，在生成json数据。
     */
    public void serialize(PageList pageList, JsonGenerator jsonGen, SerializerProvider provider)
        throws IOException, JsonProcessingException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalCount", pageList.getPaginator().getTotalCount());
        map.put("data", pageList);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(jsonGen, map);
    }
}