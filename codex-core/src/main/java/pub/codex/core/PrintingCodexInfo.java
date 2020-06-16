package pub.codex.core;

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
        //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
        Environment env = event.getApplicationContext().getEnvironment();
        try {
            log.info("\n----------------------------------------------------------\n\t" +
                            "\t\t欢迎使用Code-x\n\t" +
                            "关注官方网站:【http://www.codex.pub】\n\t" +
                            "关注Github:【https://github.com/codex-league/codex】\n\t\n\t" +
                            "本地访问Code-x: \thttp://localhost:{}/codex/index.html (非常重要)\n\t" +
                            "外网访问Code-x: \thttp://{}:{}/codex.html\n----------------------------------------------------------",
                    env.getProperty("server.port") != null? env.getProperty("server.port"):"8080",
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port") != null? env.getProperty("server.port"):"8080");
        } catch (UnknownHostException e) {
            log.error("无法获取本地IP");
        }


    }

}