package com.chatbot.domain.repository;

import com.chatbot.domain.SysConfig;

import java.util.Optional;

public interface ConfigRepository {

    Optional<SysConfig> getConfigByKey(String configKey);
}
