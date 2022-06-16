package com.douzone.mysite.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.douzone.config.app.DBConfig;
import com.douzone.config.app.MyBatisConfig;

@Configuration
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
