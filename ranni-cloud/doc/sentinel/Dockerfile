FROM java:8
MAINTAINER ymchen 781944445@qq.com

ENV JAVA_OPT=""
ENV PROJECT_NAME sentinel-dashboard
ENV SERVER_HOST localhost
ENV SERVER_PORT 8080
ENV USERNAME sentinel
ENV PASSWORD sentinel
ENV SENTINEL_LOGS /opt/logs

COPY ./sentinel-dashboard-1.8.1.jar  /opt/sentinel-dashboard-1.8.1.jar
COPY ./sentinel-entrypoint.sh /opt/sentinel-entrypoint.sh
RUN chmod +x /opt/sentinel-entrypoint.sh \
&& ln -s /opt/sentinel-entrypoint.sh /usr/local/bin/sentinel-entrypoint.sh

VOLUME /opt/logs


ENTRYPOINT ["sentinel-entrypoint.sh"]