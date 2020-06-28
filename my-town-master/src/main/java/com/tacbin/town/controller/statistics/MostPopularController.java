package com.tacbin.town.controller.statistics;

import com.tacbin.town.data.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description :数据统计相关
 * @Author : Administrator
 * @Date : 2020-03-01 19:28
 **/
@Controller
@RequestMapping("/statistics")
public class MostPopularController {
    public List<Product> topProduct(int num) {
        return null;
    }
}
