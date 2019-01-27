package org.landy.springbootjmx.dynamic;

import org.landy.springbootjmx.standard.Hello;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class DynamicMBeanServer {

    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName objectName = new ObjectName("org.landy.springbootjmx.dynamic:type=Data");

        Data data = new DataManagedBean();
        //利用动态Bean就非常具有弹性空间了，Bean只要实现自己的接口即可。
        DynamicMBean dynamicMBean = new StandardMBean(data,Data.class);

        mBeanServer.registerMBean(dynamicMBean, objectName);

        System.out.println("DynamicMBeanServer is starting....");
        //为了便于测试,可以用JConsole查看运行结果
        TimeUnit.HOURS.sleep(1);

    }

}
