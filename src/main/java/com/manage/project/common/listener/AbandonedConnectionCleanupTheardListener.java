package com.manage.project.common.listener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 清除数据库链接线程。该现象目前只发现在MySQL驱动上存在
 * MySQL官方BUG信息：https://bugs.mysql.com/bug.php?id=69526
 * 避免Tomcat reload application的是无法清除MySQL连接线程时抛出一下异常：
 */
@WebListener
@Profile({"test", "release"})
public class AbandonedConnectionCleanupTheardListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(AbandonedConnectionCleanupTheardListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("AbandonedConnectionCleanupTheardListener is started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        Driver driver = null;

        // 清除驱动
        while (drivers.hasMoreElements()) {
            try {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ex) {
                logger.error("注销数据库连接线程失败。这种情况通常出现在MySQL上。");
            }
        }

        AbandonedConnectionCleanupThread.shutdown();
    }
}
