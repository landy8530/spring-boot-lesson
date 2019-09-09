rem DEL /q C:\03_code\idea_workspace\spring-boot-lesson\tomcat-instances\tomcat-jndi\logs\*.*
set "JAVA_HOME=C:\01_soft\java\jdk1.8.0_202"
set "CATALINA_HOME=C:\05_webserver\apache-tomcat-8.5.45"
set "CATALINA_BASE=C:\03_code\idea_workspace\spring-boot-lesson\tomcat-instances\tomcat-jndi"
set "TITLE=Tomcat JNDI Demo"
SET CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=7776
set "JAVA_OPTS=%JAVA_OPTS% -Dlog.path=C:\03_code\idea_workspace\spring-boot-lesson\tomcat-instances\tomcat-jndi\logs -server -Xms1024m -Xmx1024m"
call "%CATALINA_HOME%\bin\startup.bat"
rem -XX:+TraceClassLoading -XX:+PrintClassHistogram
rem -Djavax.net.ssl.trustStore=C:/data/resources/certs/cacerts -Djavax.net.ssl.keyStore=C:\data\resources\certs\mgkeycert.jks -Djavax.net.ssl.keyStorePassword=Key2St0re -Djdk.tls.client.protocols=TLSv1.2 -Dsun.security.ssl.allowUnsafeRenegotiation=false -Dhttps.protocols=TLSv1.2