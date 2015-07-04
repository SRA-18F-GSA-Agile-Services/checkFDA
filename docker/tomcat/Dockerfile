FROM tomcat:8-jre8

# RUN apt-get update && apt-get install -y apt-utils && apt-get install -y telnet

# This gives us envsubst, which we use for tomcat configuration.
RUN apt-get update && apt-get install -y gettext-base

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

COPY ServerConfig-template.groovy /tmp/ServerConfig-template.groovy

COPY docker-entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

CMD ["catalina.sh", "run"]



