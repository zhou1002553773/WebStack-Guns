/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jsnjfz.manage.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.jsnjfz.manage.core.common.node.MenuNode;
import com.jsnjfz.manage.modular.system.model.Article;
import com.jsnjfz.manage.modular.system.model.Category;
import com.jsnjfz.manage.modular.system.model.Site;
import com.jsnjfz.manage.modular.system.model.SiteViewLog;
import com.jsnjfz.manage.modular.system.service.ISiteViewLogService;
import com.jsnjfz.manage.modular.system.service.impl.ArticleServiceImpl;
import com.jsnjfz.manage.modular.system.service.impl.CategoryServiceImpl;
import com.jsnjfz.manage.modular.system.service.impl.SiteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author jsnjfz
 * @Date 2019/7/25 21:23
 * 首页控制器
 */
@Controller
public class IndexController extends BaseController {

    @Value("${guns.domain:}")
    private String domain;

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private SiteServiceImpl siteService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ISiteViewLogService siteViewLogService;

    /**
     * 跳转到首页
     */
    @RequestMapping("/")
    public String index(Model model) {
        List<MenuNode> menus = categoryService.getCatogryNode(new HashMap<>());
        menus.forEach(item -> item.setDomain(""));
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        List<Category> categorySiteList = categoryService.getCategorySiteGood(null);
        model.addAttribute("categorySiteList", categorySiteList);
        model.addAttribute("titles", titles);
        model.addAttribute("showMore", true);
        return "/index.html";
    }

    /**
     * 跳转到关于页面
     */
    @RequestMapping("/about")
    public String about(Model model) {
        return "/about.html";
    }

    @RequestMapping("/site_detail/{siteId}")
    public String siteDetail(@PathVariable Integer siteId, Model model) {
        Site site = siteService.get(siteId);
        Article article = articleService.selectById(site.getArticleId());
        List<MenuNode> menus = categoryService.getCatogryNode(new HashMap<>());
        menus.forEach(item -> item.setDomain(domain));
        List<MenuNode> titles = MenuNode.buildTitle(menus);
//        List<Category> categorySiteList = categoryService.getCategorySiteGood(null);
//        model.addAttribute("categorySiteList", categorySiteList);
        model.addAttribute("titles", titles);
        model.addAttribute("site",site);
        model.addAttribute("article",article);

        // 浏览记录
        SiteViewLog siteViewLog = new SiteViewLog();
        siteViewLog.setSiteId(siteId);
        siteViewLog.setCreateTime(new Date());
        siteViewLog.setUpdateTime(new Date());
        siteViewLogService.insert(siteViewLog);

        return "/site_detail.html";
    }

    @RequestMapping("/site_list/{categoryId}")
    public String siteList(@PathVariable Integer categoryId, Model model) {
        List<MenuNode> menus = categoryService.getCatogryNode(new HashMap<>());
        menus.forEach(item -> item.setDomain(domain));
        List<MenuNode> titles = MenuNode.buildTitle(menus);

        List<Category> categorySiteList = categoryService.getCategorySite(categoryId);
        model.addAttribute("categorySiteList", categorySiteList);
        model.addAttribute("titles", titles);
        model.addAttribute("showMore", false);
        return "/site_list.html";
    }

}
