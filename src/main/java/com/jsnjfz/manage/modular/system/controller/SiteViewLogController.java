package com.jsnjfz.manage.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.jsnjfz.manage.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.jsnjfz.manage.modular.system.model.SiteViewLog;
import com.jsnjfz.manage.modular.system.service.ISiteViewLogService;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2023-05-18 22:14:07
 */
@Controller
@RequestMapping("/siteViewLog")
public class SiteViewLogController extends BaseController {

    private String PREFIX = "/system/siteViewLog/";

    @Autowired
    private ISiteViewLogService siteViewLogService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "siteViewLog.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/siteViewLog_add")
    public String siteViewLogAdd() {
        return PREFIX + "siteViewLog_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/siteViewLog_update/{siteViewLogId}")
    public String siteViewLogUpdate(@PathVariable Integer siteViewLogId, Model model) {
        SiteViewLog siteViewLog = siteViewLogService.selectById(siteViewLogId);
        model.addAttribute("item",siteViewLog);
        LogObjectHolder.me().set(siteViewLog);
        return PREFIX + "siteViewLog_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return siteViewLogService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SiteViewLog siteViewLog) {
        siteViewLogService.insert(siteViewLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer siteViewLogId) {
        siteViewLogService.deleteById(siteViewLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SiteViewLog siteViewLog) {
        siteViewLogService.updateById(siteViewLog);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{siteViewLogId}")
    @ResponseBody
    public Object detail(@PathVariable("siteViewLogId") Integer siteViewLogId) {
        return siteViewLogService.selectById(siteViewLogId);
    }
}
