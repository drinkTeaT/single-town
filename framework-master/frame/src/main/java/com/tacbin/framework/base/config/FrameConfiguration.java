package com.tacbin.framework.base.config;

import com.tacbin.framework.base.utils.UploadImageUtil;
import com.tacbin.framework.base.utils.UserInfoBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description :
 * @Author : Administrator
 * @Date : 2020-03-10 9:09
 **/
@Configuration
public class FrameConfiguration {
    @Bean
    public WebFileConfig webImageFileConfig() {
        return new WebFileConfig();
    }

    @Bean
    public UploadImageUtil uploadImageUtil() {
        return new UploadImageUtil();
    }

    @Bean
    public UserInfoBeanUtil getBeanUtil() {
        return new UserInfoBeanUtil();
    }
}
