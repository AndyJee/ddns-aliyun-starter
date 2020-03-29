package cn.andyjee.jnas.ddns;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author AndyJee
 */
@Configuration
@EnableConfigurationProperties(AutoConfigProperties.class)
@ConditionalOnClass(RefreshIpService.class)
public class AutoConfigClass {


    @Autowired
    private AutoConfigProperties autoConfigProperties;

    @ConditionalOnMissingBean
    @ConditionalOnExpression("${jnas.ddns.enabled:true}")
    @Bean
    public RefreshIpService getRefreshIpService() {

        DefaultProfile profile = DefaultProfile.getProfile(
                autoConfigProperties.getRegionId(),
                autoConfigProperties.getAccessKeyId(),
                autoConfigProperties.getAccessKeySecret());
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);

        RefreshIpService refreshIpService = new RefreshIpService(defaultAcsClient, autoConfigProperties.getDomainName());

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleWithFixedDelay(refreshIpService, 1, autoConfigProperties.getIntervalSeconds(), TimeUnit.SECONDS);

        return refreshIpService;
    }

}