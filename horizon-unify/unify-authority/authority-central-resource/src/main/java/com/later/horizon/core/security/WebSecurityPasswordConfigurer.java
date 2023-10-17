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
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

@Slf4j
@Component
public class WebSecurityPasswordConfigurer implements BeanFactoryPostProcessor, EnvironmentAware, Ordered {

    private static final String Encrypt_Regex_Exp = "(?<=ENC\\().+(?=\\))";

    private static void decryptRefreshProperties(PropertySource<?> propertySource, Environment environment, MutablePropertySources propertySources, String publicKey) {
        @SuppressWarnings("unchecked") Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
        Properties properties = new Properties();
        for (String key : source.keySet()) {
            Object value = source.get(key);
            if (ObjectUtils.isEmpty(value)) {
                continue;
            }
            if (value instanceof String) {
                if (CommonHelper.matchRegex(Encrypt_Regex_Exp, String.valueOf(value))) {
                    properties.put(key, RSAHelper.decrypt(CommonHelper.findRegex(Encrypt_Regex_Exp, String.valueOf(value)), publicKey, false));
                }
            }
            if (value instanceof OriginTrackedValue) {
                Object finalValue = ((OriginTrackedValue) value).getValue();
                if (CommonHelper.matchRegex(Encrypt_Regex_Exp, String.valueOf(finalValue))) {
                    properties.put(key, RSAHelper.decrypt(CommonHelper.findRegex(Encrypt_Regex_Exp, String.valueOf(finalValue)), publicKey, false));
                }
            }
        }

        if (!properties.isEmpty()) {
            for (Object key : properties.keySet()) {
                String finalKey = (String) key;
                propertySources.remove(finalKey);
                Properties finalProperties = new Properties();
                finalProperties.setProperty(finalKey, properties.getProperty(finalKey));
                propertySources.addFirst(new PropertiesPropertySource(finalKey, finalProperties));
                BindResult<Properties> bindResult = Binder.get(environment).bind(finalKey, Bindable.of(Properties.class));
                if (bindResult.isBound()) {
                    if (log.isDebugEnabled()) {
                        log.debug("in WebSecurityDecryptConfigurer decryptRefreshProperties key:{} coverage complete.", finalKey);
                    }
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 是否启用明文密码
        String cfgPlaintextDecrypt = environment.getProperty(Constants.Env_Cfg_PlaintextDecrypt);
        if (StringUtils.hasText(cfgPlaintextDecrypt) && Boolean.parseBoolean(cfgPlaintextDecrypt)) {
            String rsaPasswordSeed = environment.getProperty(Constants.Env_RSA_PasswordSeed);
            if (StringUtils.hasText(rsaPasswordSeed)) {
                // 获取加密公钥
                String publicKey = RSAHelper.getPublicKey(rsaPasswordSeed);

                MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
                for (PropertySource<?> propertySource : propertySources) {
                    if (propertySource instanceof MapPropertySource) {
                        decryptRefreshProperties(propertySource, environment, propertySources, publicKey);
                    }
                }

                // 移除加密公钥
                RSAHelper.removeKey(publicKey);
            } else {
                throw new BizException(Constants.BizStatus.Cfg_Decrypt_Obtain_Fail);
            }
        }
    }

    @Override
    public int getOrder() {
        // 让其初始化顺序最低，即最后初始化。
        return Ordered.LOWEST_PRECEDENCE;
    }

}
