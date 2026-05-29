package com.jiang.bbs_forum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jiang.bbs_forum.mapper")
public class BbsForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsForumApplication.class, args);
    }

}
