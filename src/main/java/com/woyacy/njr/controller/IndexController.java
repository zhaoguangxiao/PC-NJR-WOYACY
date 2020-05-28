package com.woyacy.njr.controller;

import com.woyacy.njr.util.HttpUtil;
import com.woyacy.njr.util.JsonConvertObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/5/28 15:11
 */
@Controller
public class IndexController {



    @RequestMapping(value = "index")
    public String index(Model model){
        String url1="https://manage.zhou-yuanwai.com/comprehens/journalism.do";
        //查出全部文章
        String result = HttpUtil.sendGet(url1, "?uidpk=20");
        model.addAttribute("result", JsonConvertObject.toBaseBean(result));
        return "index";
    }

}
