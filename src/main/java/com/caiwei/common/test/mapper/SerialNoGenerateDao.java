package com.caiwei.common.test.mapper;

import com.caiwei.common.test.common.Utils;
import com.caiwei.common.test.module.SerialNoGenerate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-08
 */
@Component
@Slf4j
public class SerialNoGenerateDao {

    @Autowired
    private SerialNoGenerateMapper serialNoGenerateMapper;

    public String generateSerialNo(String appId) {
        SerialNoGenerate serialNoGenerate = serialNoGenerateMapper.queryNowSerialNo(appId);
        if (Objects.nonNull(serialNoGenerate)) {
            for(;;){
                serialNoGenerate.setAppId(appId);
                serialNoGenerate.setNowSerialNo(Utils.generateSerialNo(appId));
                serialNoGenerate.setUpdateTime(Utils.now());
                int result = serialNoGenerateMapper.updateSerialNo(serialNoGenerate);
                if (result == 1) {
                    break;
                }else {
                    log.warn("重复啦");
                }
            }

        }else {
            log.error("appId[{}]的应用流水号表没有进行初始化",appId);
        }
        return serialNoGenerate.getNowSerialNo();
    }
}
