# Assumes the dist archive has been extracted to ./files/reddit-lite
# You will need to mount an application.conf file into the container at the path specified by $CONFIG_FILE
# You may optionally also mount in a logback.xml file to /opt/reddit-lite/conf/ and then mount the respective log dir so
# the log files can be accessed from the host (if you're using a file appender).

FROM openjdk:8-jre-slim

ENV CONFIG_FILE /opt/application.conf

WORKDIR /opt/reddit-lite

COPY files/reddit-lite /opt/reddit-lite

EXPOSE 9000

CMD /opt/reddit-lite/bin/reddit-lite -Dconfig.file=$CONFIG_FILE
