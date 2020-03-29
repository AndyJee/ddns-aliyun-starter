package cn.andyjee.jnas.ddns;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author AndyJee
 */
@ConfigurationProperties("jnas.ddns")
@Setter
@Getter
public class AutoConfigProperties {

    private String regionId;

    private String accessKeyId;

    private String accessKeySecret;

    private String domainName;

    private Long intervalSeconds;

}