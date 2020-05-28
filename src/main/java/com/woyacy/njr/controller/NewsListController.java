package com.woyacy.njr.controller;

import com.alibaba.fastjson.JSON;
import com.woyacy.njr.bean.ComprehensBean;
import com.woyacy.njr.util.HttpUtil;
import com.woyacy.njr.util.JsonConvertObject;
import com.woyacy.njr.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/5/14 14:33
 */
@Controller
@RequestMapping("new")
public class NewsListController {



    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String toNewList(@RequestParam Map<String, String> map,
                            Model model) throws IOException {


        String url1="https://manage.zhou-yuanwai.com/comprehens/journalism.do";
        //查出全部文章
        String result = HttpUtil.sendGet(url1, "?uidpk=20");
        List<ComprehensBean> comprehensBeans = JsonConvertObject.toBaseBean(result);


        if (map.size() == 0 || map.get("currentPage").isEmpty()) {
            map.put("currentPage", "0");
        }

        // 参数为当前页码、每页显示条数、查询的列表集合
        PageUtil pageInfo = new PageUtil(Integer.valueOf(map.get("currentPage")), 10, comprehensBeans);
        model.addAttribute("userDTOList", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);

        return "news";
    }




    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public String newsDetails(@PathVariable("id") Long id,
                              Model model){
        String url="https://manage.zhou-yuanwai.com/comprehens/findOne.do";
        if (null != id){
            String result = HttpUtil.sendGet(url, "?uidpk="+id);
            model.addAttribute("newsDetails",JSON.parseObject(result,ComprehensBean.class));
        }
        return "newsInfo";
    }





}
