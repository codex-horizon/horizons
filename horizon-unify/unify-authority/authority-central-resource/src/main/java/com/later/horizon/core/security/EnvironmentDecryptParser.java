package com.later.horizon.core.security;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BizException;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.helper.RSAHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Slf4j
@Component
public class EnvironmentDecryptParser implements BeanFactoryPostProcessor, Ordered, EnvironmentAware {

    private static final String Encrypt_Regex_Exp = "(?<=ENC\\().+(?=\\))";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE; // 让其初始化顺序最低，即最后初始化。
    }

    @Override
    public void setEnvironment(Environment environment) {
        String cfgPlaintextDecrypt = environment.getProperty(Constants.Env_Cfg_PlaintextDecrypt); // 是否启用明文密码
        if (StringUtils.hasText(cfgPlaintextDecrypt) && Boolean.parseBoolean(cfgPlaintextDecrypt)) {
            String rsaPasswordSeed = environment.getProperty(Constants.Env_RSA_PasswordSeed);
            if (StringUtils.hasText(rsaPasswordSeed)) {
                String publicKey = RSAHelper.getPublicKey(rsaPasswordSeed); // 获取加密公钥
                this.environmentParser(environment, publicKey);
                RSAHelper.removeKey(publicKey); // 移除加密公钥
            } else {
                throw new BizException(Constants.BizStatus.Cfg_Decrypt_Obtain_Fail);
            }
        }
    }

    private void environmentParser(Environment environment, String publicKey) {
        MutablePropertySources mutablePropertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        Properties finalProperties = new Properties();
        for (PropertySource<?> propertySources : mutablePropertySources) {
            if (propertySources instanceof OriginTrackedMapPropertySource) { // 该处解析容器原生配置文件
                finalProperties.clear();
                finalProperties.putAll(((OriginTrackedMapPropertySource) propertySources).getSource());
                this.decryptParser(finalProperties, publicKey, mutablePropertySources, environment);
            }
            if (propertySources instanceof CompositePropertySource) { // 该处解析容器个性配置文件
                finalProperties.clear();
                CompositePropertySource compositePropertySource = (CompositePropertySource) propertySources;
                compositePropertySource.getPropertySources().forEach(propertySource -> {
                    Properties properties = (Properties) propertySource.getSource();
                    properties.keySet().forEach(key -> finalProperties.put(key, OriginTrackedValue.of(compositePropertySource.getProperty((String) key), PropertySourceOrigin.get(propertySource, (String) key))));
                });
                this.decryptParser(finalProperties, publicKey, mutablePropertySources, environment);
            }
        }
    }

    private void decryptParser(Properties properties, String publicKey, MutablePropertySources mutablePropertySources, Environment environment) {
        Properties finalProperties = new Properties();
        properties.keySet().forEach(key -> {
            OriginTrackedValue originTrackedValue = (OriginTrackedValue) properties.get(key);
            String value = originTrackedValue.toString();
            if (CommonHelper.matchRegex(Encrypt_Regex_Exp, value)) {
                finalProperties.put(key, RSAHelper.decrypt(CommonHelper.findRegex(Encrypt_Regex_Exp, value), publicKey, false));
            }
        });
        finalProperties.forEach((key, value) -> {
            String finalKey = (String) key;
            mutablePropertySources.remove(finalKey);
            Properties exProperties = new Properties();
            exProperties.setProperty(finalKey, finalProperties.getProperty(finalKey));
            mutablePropertySources.addFirst(new PropertiesPropertySource(finalKey, exProperties));
            BindResult<Properties> bindResult = Binder.get(environment).bind(finalKey, Bindable.of(Properties.class));
            if (bindResult.isBound()) {
                if (log.isDebugEnabled()) {
                    log.debug("in WebSecurityDecryptConfigurer decryptParser key:{} coverage complete.", finalKey);
                }
            }
        });
    }

}
