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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/5/14 14:33
 */
@Controller
@RequestMapping("new")
public class NewsListController {

    private static final String URL = "https://manage.zhou-yuanwai.com/comprehens/journalism.do";
    private static final String FIND_ONE = "https://manage.zhou-yuanwai.com/comprehens/findOne.do";


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String toNewList(@RequestParam Map<String, String> map,
                            Model model) throws IOException {


        //查出全部文章
        List<ComprehensBean> comprehensBeans = getComprehensBeans();


        if (map.size() == 0 || map.get("currentPage").isEmpty()) {
            map.put("currentPage", "0");
        }

        // 参数为当前页码、每页显示条数、查询的列表集合
        PageUtil pageInfo = new PageUtil(Integer.valueOf(map.get("currentPage")), 10, comprehensBeans);
        model.addAttribute("userDTOList", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);

        return "news";
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String newsDetails(@PathVariable("id") Long id,
                              Model model) {

        String result = HttpUtil.sendGet(FIND_ONE, "?uidpk=" + id);
        ComprehensBean bean = JSON.parseObject(result, ComprehensBean.class);
        model.addAttribute("newsDetails", bean);

        // 查出全部文章
        List<ComprehensBean> comprehensBeans = getComprehensBeans();
        model.addAttribute("comprehensBeans", comprehensBeans);

        //模糊查询keyword
        List<ComprehensBean> beans = rnadomByTitle(bean, comprehensBeans);
        model.addAttribute("randomNews", beans);
        return "newsInfo";
    }

    private List<ComprehensBean> rnadomByTitle(ComprehensBean bean, List<ComprehensBean> comprehensBeans) {
        ArrayList<ComprehensBean> results = new ArrayList<>();
        //计算出索引集合
        HashSet<Integer> indexs = indexFor(comprehensBeans.size(), 5);

        indexs.stream().forEach(each -> {
            if (!comprehensBeans.get(each).getUidpk().equals(bean.getUidpk())) {
                results.add(comprehensBeans.get(each));
            }
        });
        return results;
    }

    private HashSet<Integer> indexFor(int length, int maxlength) {
        HashSet<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() <= maxlength - 1) {
            set.add(random.nextInt(length));
        }
        return set;
    }

    private List<ComprehensBean> getComprehensBeans() {
        //查出全部文章
        String result = HttpUtil.sendGet(URL, "?uidpk=20");
        return JsonConvertObject.toBaseBean(result);
    }

    public static void main(String[] args) {
        Integer i1=100;
        Integer i2=100;

        Integer i3=1000;
        Integer i4=1000;

        System.out.println(i1=0-(-127)+1);
    }

}
