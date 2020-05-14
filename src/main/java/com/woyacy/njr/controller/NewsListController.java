package com.woyacy.njr.controller;

import com.alibaba.fastjson.JSON;
import com.sun.istack.internal.NotNull;
import com.woyacy.njr.bean.ComprehensBean;
import com.woyacy.njr.util.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/5/14 14:33
 */
@Controller
@RequestMapping("new")
public class NewsListController {



    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String toNewList(Model model) throws IOException {
        String url1="https://manage.zhou-yuanwai.com/comprehens/journalism.do";
        //查出全部文章
        String result = HttpUtil.sendGet(url1, "?uidpk=20");
        model.addAttribute("news",toBaseBean(result));
        return "news";
    }




    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public String newsDetails(@PathVariable("id") @NotNull Long id,
                              Model model){
        String url="https://manage.zhou-yuanwai.com/comprehens/findOne.do";
        if (null != id){
            String result = HttpUtil.sendGet(url, "?uidpk="+id);
            model.addAttribute("newsDetails",JSON.parseObject(result,ComprehensBean.class));
        }
        return "newsInfo";
    }



    /**
     *  把json 数组转化为 实体集合对象
     * @param str json数组
     * @return 返回 List<ComprehensBean>实体
     */
    private List<ComprehensBean> toBaseBean(String str){
        return JSON.parseArray(str, ComprehensBean.class);
    }

}
