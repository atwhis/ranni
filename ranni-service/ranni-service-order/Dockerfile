FROM openjdk
MAINTAINER ymchen 781944445@qq.com

COPY ./target/ranni-service-order-0.0.1-SNAPSHOT.jar /ranni/order.jar

#EXPOSE 8862
#CMD ["java","-jar", "/ranni/order.jar","--nacos.url=10.44.1.133","--mysql.url=10.44.1.155"]

ENTRYPOINT ["java","-jar", "/ranni/order.jar"]