/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.web.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.google.common.collect.Lists;


/**
 * mybatis查询封装的分页信息实体对象。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class PageQuery extends RowBounds implements java.io.Serializable
{

    private static final long serialVersionUID = -8000900575354501298L;

    // 当前页
    private int pagenum;

    // 每页多少条
    private int pagesize = 10;

    // 排序规则
    private String orderbyrule;

    private List<SortRule> sortRuleList;

    // 是否需要计算总数,默认0为计算
    private String isCount = "0";

    // 是否需要计算总计,默认1为不计算
    private String isTotal = "1";

    // 无参构造器
    public PageQuery()
    {
        super();
    }

    /**
     * PageQuery有参构造器。
     * 
     * @param pagenum
     *            当前页。
     * @param pagesize
     *            每页显示多少条。
     */
    public PageQuery(int pagenum, int pagesize)
    {
        this.pagenum = pagenum;
        this.pagesize = pagesize;
        this.sortRuleList = new ArrayList<SortRule>();
    }

    /**
     * PageQuery有参构造器。
     * 
     * @param pagenum
     *            当前页。
     * @param pagesize
     *            每页显示多少条。
     * @param sortColumn
     *            排序对象。
     */
    public PageQuery(int pagenum, int pagesize, SortRule sortColumn)
    {
        this.pagenum = pagenum;
        this.pagesize = pagesize;
        this.sortRuleList = new ArrayList<SortRule>();
        if (sortColumn != null)
        {
            this.sortRuleList.add(sortColumn);
        }
    }

    /**
     * PageQuery有参构造器。
     * 
     * @param pagenum
     *            当前页。
     * @param pagesize
     *            每页显示多少条。
     * @param sortColumnList
     *            排序对象集合。
     */
    public PageQuery(int pagenum, int pagesize, List<SortRule> sortColumnList)
    {
        this.pagenum = pagenum;
        this.pagesize = pagesize;
        this.sortRuleList = sortColumnList;
    }

    /**
     * PageQuery有参构造器。
     * 
     * @param pagenum
     *            当前页。
     * @param pagesize
     *            每页显示多少条。
     * @param orderbyrule
     *            排序字符串。
     */
    public PageQuery(int pagenum, int pagesize, String orderbyrule)
    {
        this(pagenum, pagesize, SortRule.parseSortColumns(orderbyrule));
    }

    /**
     * 每页显示多少条。
     * 
     * @return 每页显示多少条。
     */
    public int getLimit()
    {
        return pagesize;
    }

    /**
     * sortRuleList的get方法。
     * 
     * @return 排序规则集合。
     */
    public List<SortRule> getSortRuleList()
    {
        if (CollectionUtils.isNotEmpty(this.sortRuleList))
        {
            return this.sortRuleList;
        }
        else
        {
            this.sortRuleList = Lists.newArrayList();
        }
        if (StringUtils.isNotBlank(this.orderbyrule))
        {
            String[] rulesAry = this.orderbyrule.split(",");
            for (String rule : rulesAry)
            {
                String[] ruleAry = rule.split("#");
                String orderType = "ASC";
                if (ruleAry.length > 1)
                {
                    orderType = ruleAry[1];
                }
                this.sortRuleList.add(new SortRule(ruleAry[0], orderType));
            }
        }
        return this.sortRuleList;
    }

    /**
     * 分页的偏移量。
     * 
     * @return 分页偏移多少条。
     */
    public int getOffset()
    {
        return (this.pagenum) * this.pagesize;
    }

    /**
     * curPage的get方法。
     * 
     * @return 当前页。
     */
    public int getPagenum()
    {
        return pagenum;
    }

    /**
     * curPage的set方法。
     */
    public void setPagenum(int pagenum)
    {
        this.pagenum = pagenum;
    }

    /**
     * iDisplayLength的get方法。
     */
    public int getPagesize()
    {
        return pagesize;
    }

    /**
     * iDisplayLength的set方法。
     */
    public void setPagesize(int pagesize)
    {
        this.pagesize = pagesize;
    }

    /**
     * SortTypes的get方法。
     */
    public String getOrderbyrule()
    {
        return orderbyrule;
    }

    /**
     * SortTypes的set方法。
     */
    public void setOrderbyrule(String orderbyrule)
    {
        this.orderbyrule = orderbyrule;
    }

    /**
     * isCount的get方法。
     */
    public String getIsCount()
    {
        return isCount;
    }

    /**
     * isCount的set方法。
     */
    public void setIsCount(String isCount)
    {
        this.isCount = isCount;
    }

    /**
     * isTotal的get方法。
     */
    public String getIsTotal()
    {
        return isTotal;
    }

    /**
     * isTotal的set方法。
     */
    public void setIsTotal(String isTotal)
    {
        this.isTotal = isTotal;
    }
}