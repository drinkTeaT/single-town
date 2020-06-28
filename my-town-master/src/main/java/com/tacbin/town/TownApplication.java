package com.tacbin.town;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-09 20:34
 **/
@SpringBootApplication
@MapperScan({"com.tacbin.framework.data.mapper","com.tacbin.town.data.mapper"})
@EnableSwagger2
public class TownApplication {

    public static void main(String[] args) {
        SpringApplication.run(TownApplication.class, args);
    }

}
