package com.ect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ect.service.QiangGouService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/qianggou")
public class QiangGouController {

   @Autowired
   private QiangGouService qiangGouService;

    @RequestMapping(value = "/qiang",method = RequestMethod.GET)
    @ResponseBody
    public String qiangGou(){

        qiangGouService.sendMsg("123","234");
        return "aaaa";

    }

    /**
     * 配置商品id 和 个数
     * @param num
     * @param productId
     * @return
     */
    @RequestMapping(value = "/peizhi",method = RequestMethod.GET)
    @ResponseBody
    public String peiZhi(@RequestParam(name = "num",required = true)int num,
                           @RequestParam(name = "productId",required = true)String productId){

        qiangGouService.peiZhi(num,productId);
        return "bbb";

    }



}
