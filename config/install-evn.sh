TOOLS_HOME=/nfs/tools

rpm -ivh ${TOOLS_HOME}/jdk-8u112-linux-x64.rpm
echo "jdk installed"

mv /usr/java/latest/jre/lib/security/local_policy.jar /usr/java/latest/jre/lib/security/local_policy.jar-bak
mv /usr/java/latest/jre/lib/security/US_export_policy.jar /usr/java/latest/jre/lib/security/US_export_policy.jar-bak
cp ${TOOLS_HOME}/security/*.jar /usr/java/latest/jre/lib/security/
echo "jre security configured for wx"


unzip ${TOOLS_HOME}/tomcat/apache-tomcat-8.5.5.zip -d /tmp
mv /tmp/apache-tomcat-8.5.5 /usr/tomcat
cp -f ${TOOLS_HOME}/tomcat/conf/* /usr/tomcat/conf/
cp -f ${TOOLS_HOME}/tomcat/bin/* /usr/tomcat/bin/
echo "tomcat set up"

mkdir -p /woyao/upload
echo "/woyao/upload created"