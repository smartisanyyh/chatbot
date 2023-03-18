package com.chatbot.repository.converter;

import com.chatbot.domain.SysConfig;
import com.chatbot.repository.SysConfigRepositoryImpl;

public class ConfigConverter {

    public static SysConfig convert(SysConfigRepositoryImpl sysConfigRepositoryImpl) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigName(sysConfigRepositoryImpl.getConfigName());
        sysConfig.setConfigKey(sysConfig.getConfigKey());
        sysConfig.setConfigValue(sysConfig.getConfigValue());
        sysConfig.setConfigType(sysConfig.getConfigType());
        return sysConfig;
    }
}
