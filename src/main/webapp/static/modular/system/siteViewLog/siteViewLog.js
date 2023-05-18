/**
 * 管理初始化
 */
var SiteViewLog = {
    id: "SiteViewLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SiteViewLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'siteId', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'updateTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SiteViewLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SiteViewLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
SiteViewLog.openAddSiteViewLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/siteViewLog/siteViewLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
SiteViewLog.openSiteViewLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/siteViewLog/siteViewLog_update/' + SiteViewLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
SiteViewLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/siteViewLog/delete", function (data) {
            Feng.success("删除成功!");
            SiteViewLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("siteViewLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
SiteViewLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SiteViewLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SiteViewLog.initColumn();
    var table = new BSTable(SiteViewLog.id, "/siteViewLog/list", defaultColunms);
    table.setPaginationType("client");
    SiteViewLog.table = table.init();
});
