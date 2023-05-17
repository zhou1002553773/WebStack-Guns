package com.jsnjfz.manage.modular.system.service.impl;

import com.jsnjfz.manage.core.common.node.MenuNode;
import com.jsnjfz.manage.core.common.node.ZTreeNode;
import com.jsnjfz.manage.core.util.Pager;
import com.jsnjfz.manage.modular.system.dao.CategoryMapper;
import com.jsnjfz.manage.modular.system.dao.SiteMapper;
import com.jsnjfz.manage.modular.system.model.Category;
import com.jsnjfz.manage.modular.system.model.Site;
import com.jsnjfz.manage.modular.system.service.BaseService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author jsnjfz
 * @Date 2019/7/21 15:17
 * 分类相关业务接口实现类
 */
@Service
public class CategoryServiceImpl extends BaseService<Category> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SiteMapper siteMapper;

    public static Pager<Category> pager = null;


    public List<Category> getCatogry(Map<String, Object> map) {
        return categoryMapper.getCatogry(map);
    }

    public List<MenuNode> getCatogryNode(Map<String, Object> map) {
        return categoryMapper.getCatogryNode(map);
    }

    public List<ZTreeNode> tree() {
        return categoryMapper.tree();
    }


    public List<Category> getCategorySiteGood(Map<String, Object> map) {
        List<Category> categoryList = categoryMapper.getList(null);
        Map<String,Object> siteQueryMap = new HashMap<>();
        siteQueryMap.put("isGood",1);
        List<Site> siteList = siteMapper.getList(siteQueryMap);
        Map<Integer, List<Site>> siteGroup = siteList.stream().collect(Collectors.groupingBy(Site::getCategoryId));

        siteList.sort(Comparator.comparing(Site::getSort));
        for (Category category:categoryList) {
            List<Site> sites = siteGroup.get(category.getId());
            sites.sort(Comparator.comparing(Site::getSort));
            sites = sites.size() > 11 ? sites.subList(0,11) : sites;
            category.setSites(sites);
        }
        return categoryList;
    }

    public List<Category> getCategorySite(Integer categoryId) {
        Map<String,Object> categoryListMap = new HashMap<>();
        categoryListMap.put("id",categoryId);
        List<Category> categoryList = categoryMapper.getList(categoryListMap);
        Map<String,Object> siteQueryMap = new HashMap<>();
        siteQueryMap.put("categoryId",categoryId);
        List<Site> siteList = siteMapper.getList(siteQueryMap);
        Map<Integer, List<Site>> siteGroup = siteList.stream().collect(Collectors.groupingBy(Site::getCategoryId));

        for (Category category:categoryList) {
            List<Site> sites = siteGroup.get(category.getId());
            sites.sort(Comparator.comparing(Site::getSort));
            category.setSites(sites);
        }
        return categoryList;
    }

}
