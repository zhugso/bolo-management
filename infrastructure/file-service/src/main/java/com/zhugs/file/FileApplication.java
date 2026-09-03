package com.zhugs.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.zhugs.file.infrastructure.persistence")
@SpringBootApplication
public class FileApplication {
    static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
