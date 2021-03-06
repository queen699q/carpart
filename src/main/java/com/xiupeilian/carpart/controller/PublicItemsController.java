package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicItemsController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private ItemsService itemsService;

    @RequestMapping("/publicItems")
    public String publicItems(Items items, Integer pageSize, Integer pageNo, HttpServletRequest request,String brandName){
        pageSize=pageSize==null?8:pageSize;
        pageNo=pageNo==null?1:pageNo;
        //分页
        PageHelper.startPage(pageNo,pageSize);
        //满足条件的全部数据即可
        List<Items> itemsList=itemsService.findItemsByQueryVo(items);
        PageInfo<Items> pageInfo=new PageInfo<>(itemsList);
        //分页数据存request
        request.setAttribute("page",pageInfo);
        //搜索条件回填
        request.setAttribute("items",items);
        List<Brand> brandList=brandService.findBrandAll();
        List<Parts> partsList=brandService.findPartAll();
//品牌列表，配件类别
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("brandName",brandName);

        return "public/publicItems";

    }


}
