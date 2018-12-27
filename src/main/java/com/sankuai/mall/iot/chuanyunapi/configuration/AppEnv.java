package com.sankuai.mall.iot.chuanyunapi.configuration;

import com.sankuai.inf.octo.mns.Consts;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by yanglin on 2018-11-14.
 */
@Component
public class AppEnv {
    public static final String ENV_FILE = "/data/webapps/appenv";
    private static final Logger log = LoggerFactory.getLogger(AppEnv.class);
    private boolean appenvIsExist = false;
    private Properties properties;

    public AppEnv() {
        properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(ENV_FILE);
            properties.load(in);
            appenvIsExist = true;
        } catch (FileNotFoundException e) {
            appenvIsExist = false;
            log.debug(Consts.PIGEON_ENV_FILE + " does not exist");
        } catch (IOException e) {
            log.debug("Failed to load config from" + Consts.PIGEON_ENV_FILE, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.debug("Failed to close " + Consts.PIGEON_ENV_FILE, e);
                }
            }
        }
    }

    public boolean getAppenIsExist() {
        return appenvIsExist;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 统一环境说明：https://wiki.sankuai.com/pages/viewpage.action?pageId=730998124
     *
     * @return
     */
    public boolean isDev() {
        return isEnv("dev");
    }

    public boolean isTest() {
        return isEnv("test");
    }

    public boolean isProd() {
        return isEnv("product");
    }

    public boolean isStaging() {
        return isEnv("staging");
    }

    public boolean isEnv(String name) {
        return properties.containsKey("env") && properties.getProperty("env").equals(name);
    }

}
