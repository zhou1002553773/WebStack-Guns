/**
 * 初始化详情对话框
 */
var SiteViewLogInfoDlg = {
    siteViewLogInfoData : {}
};

/**
 * 清除数据
 */
SiteViewLogInfoDlg.clearData = function() {
    this.siteViewLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SiteViewLogInfoDlg.set = function(key, val) {
    this.siteViewLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SiteViewLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SiteViewLogInfoDlg.close = function() {
    parent.layer.close(window.parent.SiteViewLog.layerIndex);
}

/**
 * 收集数据
 */
SiteViewLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('siteId')
    .set('createTime')
    .set('updateTime');
}

/**
 * 提交添加
 */
SiteViewLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/siteViewLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.SiteViewLog.table.refresh();
        SiteViewLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.siteViewLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SiteViewLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/siteViewLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.SiteViewLog.table.refresh();
        SiteViewLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.siteViewLogInfoData);
    ajax.start();
}

$(function() {

});
