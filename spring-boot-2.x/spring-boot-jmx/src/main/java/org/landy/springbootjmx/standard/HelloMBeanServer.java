package org.landy.springbootjmx.standard;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class HelloMBeanServer {

    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = new ObjectName("org.landy.springbootjmx.standard:type=Hello");

        mBeanServer.registerMBean(new Hello(), objectName);

        System.out.println("HelloMBeanServer is starting....");
        //为了便于测试,可以用JConsole查看运行结果
        TimeUnit.HOURS.sleep(1);
    }

}
