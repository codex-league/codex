package org.codex.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class PrintingCodexInfo implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger log = LoggerFactory.getLogger(PrintingCodexInfo.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            Environment env = event.getApplicationContext().getEnvironment();
            try {
                log.info("\n----------------------------------------------------------\n\t" +
                                "欢迎使用Code-x\n\t" +
                                "Github:【codex-league/codex】\n\t" +
                                "Local: \t\thttp://localhost:{}/codex.html\n\t" +
                                "External: \thttp://{}:{}/codex.html\n----------------------------------------------------------",
                        env.getProperty("server.port"),
                        InetAddress.getLocalHost().getHostAddress(),
                        env.getProperty("server.port"));
            } catch (UnknownHostException e) {
                log.error("无法获取本地IP");
            }

        }

        //或者下面这种方式
        if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
            System.out.println("\n\n\n_________\n\n加载一次的 \n\n ________\n\n\n\n");
        }

    }

}