package tacbin.framespringbootstarter;

import com.tacbin.framework.base.config.FrameConfiguration;
import com.tacbin.framework.base.exception.GlobalExceptionHandler;
import com.tacbin.framework.data.Iservice.IUserPermissionService;
import com.tacbin.framework.data.serviceImpl.IUserInfoServiceImpl;
import com.tacbin.framework.data.serviceImpl.IUserPermissionServiceImpl;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.tacbin.framework.base.permission.config.ShiroAuthFilterConfig;
import com.tacbin.framework.base.permission.config.ShrioSessionCacheConfig;
import com.tacbin.framework.base.permission.operation.Login;
import com.tacbin.framework.base.redis.RedisConfiguration;
import com.tacbin.framework.base.swagger.SwaggerConfig;
import com.tacbin.framework.controller.LoginController;
import com.tacbin.framework.controller.ToolController;
import com.tacbin.framework.data.Iservice.IUserInfoService;

@Configuration
@AutoConfigureBefore(ShiroWebAutoConfiguration.class)
@EnableConfigurationProperties(FrameProperties.class)
@Import({FrameConfiguration.class, ShiroAuthFilterConfig.class, ShrioSessionCacheConfig.class, RedisConfiguration.class, SwaggerConfig.class})
public class FrameAutoConfig {

    @Bean
    public Login getLogin() {
        return new Login();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public ToolController toolController() {
        return new ToolController();
    }

    @Bean
    public LoginController loginController() {
        return new LoginController();
    }

    @Bean
    public IUserInfoService userInfoService() {
        return new IUserInfoServiceImpl();
    }

    @Bean
    public IUserPermissionService userPermissionService() {
        return new IUserPermissionServiceImpl();
    }
}
