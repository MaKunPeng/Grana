package com.grana;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 * 
 * @author Aaron Ma
 * @Date 2021-3-2 14:36:10
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.grana.user.infrastructure.persistence.mybatis")
public class GranaApplication {
    private static final String PID_FILE_NAME = "grana.pid";

    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(GranaApplication.class, args);

        startSuccess();
    }

    /**
     * 启动成功
     */
    private static void startSuccess() {
        createPIDFile();

        System.out.println("Grana runtime start success.     \n" +
                        "   _____                       \n" +
                        "  / ____|                      \n" +
                        " | |  __ _ __ __ _ _ __   __ _ \n" +
                        " | | |_ | '__/ _` | '_ \\ / _` |\n" +
                        " | |__| | | | (_| | | | | (_| |\n" +
                        "  \\_____|_|  \\__,_|_| |_|\\__,_|\n" +
                        "                               "
                );
    }

    /**
     * 创建保存当前程序进程ID的文件
     */
    private static void createPIDFile() {
        String pid = "";
        String name = ManagementFactory.getRuntimeMXBean().getName();
        int i = name.indexOf('@');
        if (i > 0) {
            pid = name.substring(0, i);
        }
        try {
            File file = new File(PID_FILE_NAME);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
                bw.write(pid);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
