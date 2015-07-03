FROM tomcat:8.0

# RUN apt-get update && apt-get install -y apt-utils && apt-get install -y telnet

RUN rm -rf /usr/local/tomcat/webapps/*

# COPY configure.sh /tmp/configure.sh

# RUN sh /tmp/configure.sh

COPY ServerConfig.groovy /usr/local/tomcat/webapps/ServerConfig.groovy

# RUN rm /tmp/configure.sh

COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

# COPY searchfda-entrypoint.sh /configure.sh
# RUN chmod +x /*.sh

# CMD ["/configure.sh"]



