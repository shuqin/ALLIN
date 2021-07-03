package cc.lovesq.controller;

import cc.lovesq.model.CreativeDO;
import cc.lovesq.query.CreativeQuery;
import cc.lovesq.result.BaseResult;
import cc.lovesq.result.JsonResult;
import cc.lovesq.result.PagerJsonResult;
import cc.lovesq.service.CreativeService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/api/creatives")
public class CreativeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CreativeController.class);

    @Resource
    private CreativeService creativeService;

    @RequestMapping(value = "/get/{creativeId}")
    public String get(@PathVariable Long creativeId, ModelMap model) {
        Assert.notNull(creativeId, "请选择要查询的创意id");
        CreativeDO creativeObj = creativeService.get(creativeId);
        model.put("creative", creativeObj);
        return "/creative/detail";
    }

    /**
     * 新增创意时跳转到新增页面
     */
    @RequestMapping(value = "/addview")
    public String addView() {
        return "/creative/addview";
    }

    /**
     * 新增创意时提交的action
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public BaseResult save(CreativeDO creative) {
        Assert.notNull(creative, "创意对象不能为空");
        Assert.isTrue(StringUtils.isNotBlank(creative.getTitle()), "创意标题不能为空");

        creativeService.save(creative);
        return BaseResult.succ("创意新增成功");
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public PagerJsonResult list(CreativeQuery query) {

        int total = creativeService.count(query);
        this.initPages(query, total);
        return new PagerJsonResult(this, creativeService.search(query));

    }

    @RequestMapping(value = "/listAll")
    @ResponseBody
    public JsonResult listAll(CreativeQuery query) {
        query.setPageSize(null);
        query.setFirstRow(null);
        List<CreativeDO> data = creativeService.search(query);
        return new JsonResult(data);

    }

    /**
     * 编辑创意时跳转到编辑页面
     */
    @RequestMapping(value = "/updateview/{creativeId}")
    public String updateView(@PathVariable Long creativeId, ModelMap model) {
        CreativeDO creativeDO = creativeService.get(creativeId);
        model.put("creative", creativeDO);
        return "/creative/updateview";
    }

    /**
     * 编辑创意后提交的action
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public BaseResult update(CreativeDO creative) {
        Assert.notNull(creative, "对象不能为空");
        Assert.notNull(creative.getCreativeId(), "创意ID不能为空");
        creativeService.update(creative);
        return BaseResult.succ("创意更新成功");
    }

    @RequestMapping(value = "/updateByMap")
    @ResponseBody
    public BaseResult updateByMap(@RequestParam Map creative) {
        Assert.notNull(creative, "对象不能为空");
        Assert.notNull(creative.get("creativeId"), "创意ID不能为空");
        CreativeDO creativeObj = JSONObject.parseObject(JSONObject.toJSONString(creative), CreativeDO.class);
        creativeService.update(creativeObj);
        return BaseResult.succ("创意更新成功");
    }

    @RequestMapping(value = "/searchForSelect")
    @ResponseBody
    public Map<String, Object> searchForSelect(
            @RequestParam(value = "k", required = false) String title,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer pageSize) {
        CreativeQuery query = buildCreativeQuery(title, page, pageSize);
        return searchForSelect2(query,
                (q) -> creativeService.search(q),
                (q) -> creativeService.count(q));
    }

    public Map<String, Object> searchForSelect2(CreativeQuery query,
                                                Function<CreativeQuery, List<CreativeDO>> getListFunc,
                                                Function<CreativeQuery, Integer> getTotalFunc) {
        List<CreativeDO> creativeDTOs = getListFunc.apply(query);
        Integer total = getTotalFunc.apply(query);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", (null == creativeDTOs) ? new ArrayList<CreativeDO>() : creativeDTOs);
        map.put("total", (null == total) ? 0 : total);
        return map;
    }


    /*
     * NOTE: can be placed in class QueryBuilder
     */
    public CreativeQuery buildCreativeQuery(String title, Integer page, Integer pageSize) {
        CreativeQuery query = new CreativeQuery();
        query.setTitle(title);
        query.setPageNum(page);
        query.setPageSize(pageSize);
        query.setFirstRow((page - 1) * pageSize);
        return query;
    }

}
