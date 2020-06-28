package com.tacbin.town.controller.entity;

import com.tacbin.framework.base.common.ResponseInfo;
import com.tacbin.framework.base.common.Status;
import com.tacbin.framework.base.utils.UploadImageUtil;
import com.tacbin.town.data.entity.Category;
import com.tacbin.town.data.entity.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tacbin.town.data.Iservice.IProductService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description :商品操作
 * @Author : Administrator
 * @Date : 2020-03-01 14:50
 **/
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private UploadImageUtil uploadImageUtil;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ApiOperation(value = "添加商品", notes = "添加商品")
    public ResponseInfo addOne(@RequestParam long categoryId, @RequestParam BigDecimal price, @RequestParam String description, @RequestParam String imgSrc1, @RequestParam(required = false) String imgSrc2, @RequestParam(required = false) String imgSrc3) {
        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setPrice(price);
        product.setDescription(description);
        product.setImg1(imgSrc1);
        product.setImg2(imgSrc2);
        product.setImg3(imgSrc3);
        productService.addOne(product);
        return ResponseInfo.builder().message("添加成功").status(Status.SUCCESS).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/del")
    @ApiOperation(value = "删除商品", notes = "删除商品")
    public ResponseInfo delOne(@RequestParam Long productId) {
        productService.delOne(productId);
        return ResponseInfo.builder().message("操作成功").status(Status.SUCCESS).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @ApiOperation(value = "编辑商品", notes = "编辑商品")
    public ResponseInfo editOne(Product product, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String url = uploadImageUtil.uploadSingleImageToServer(file);
        product.setImg1(url);
        productService.saveOrAddById(product);
        return ResponseInfo.builder().message("操作成功").status(Status.SUCCESS).build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/view")
    @ApiOperation(value = "查看商品", notes = "查看商品")
    public ResponseInfo viewOne(@RequestParam Long productId) {
        Product product = productService.viewOne(productId);
        return ResponseInfo.builder().message("操作成功").status(Status.SUCCESS).data(product).build();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/viewPrdByParentCatId")
    @ApiOperation(value = "根据父类目及用户ID查看下方商品数量", notes = "父类目id")
    public ResponseInfo<List<Product>> viewPrdByParentCatId(@RequestParam Long parentCatId) {
        List<Product> products = productService.viewPrdByParentCatId(parentCatId);
        return new ResponseInfo<>("查询成功", Status.SUCCESS, products);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/viewPrdByParentCatIdAndUserId")
    @ApiOperation(value = "根据一级类目及用户ID查看下方商品", notes = "父类目id")
    public ResponseInfo<List<Category>> viewPrdByCatIdAndUserId(@RequestParam Long catId) {
        List<Category> products = productService.viewPrdByCatIdAndUserId(catId);
        return new ResponseInfo<>("查询成功", Status.SUCCESS, products);
    }
}
