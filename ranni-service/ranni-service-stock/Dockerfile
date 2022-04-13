FROM openjdk
MAINTAINER ymchen 781944445@qq.com

COPY ./target/ranni-service-stock-0.0.1-SNAPSHOT.jar /ranni/stock.jar

#EXPOSE 8863
#CMD ["java","-jar", "/ranni/stock.jar","--nacos.url=10.44.1.133","--mysql.url=10.44.1.155"]

ENTRYPOINT ["java","-jar", "/ranni/stock.jar"]