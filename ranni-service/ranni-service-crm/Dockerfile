FROM openjdk
MAINTAINER ymchen 781944445@qq.com

COPY ./target/ranni-service-crm-0.0.1-SNAPSHOT.jar /ranni/crm.jar

#EXPOSE 8861
#CMD ["java","-jar", "/ranni/crm.jar","--nacos.url=10.44.1.133","--mysql.url=10.44.1.155"]

ENTRYPOINT ["java","-jar", "/ranni/crm.jar"]