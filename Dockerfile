FROM openjdk:8

ARG SPARK_VERSION=2.2.1
ARG SPARK_BINARY_ARCHIVE_NAME=spark-2.2.1-bin-hadoop2.7
ARG SPARK_BINARY_DOWNLOAD_URL=http://apache.volia.net/spark/spark-2.2.1/spark-2.2.1-bin-hadoop2.7.tgz


RUN \
	apt-get update && \
    apt-get install scala -y && \
    wget -qO - ${SPARK_BINARY_DOWNLOAD_URL} | tar -xz -C /usr/local/ && \
	cd /usr/local/ && \
	ln -s ${SPARK_BINARY_ARCHIVE_NAME} spark

USER root
WORKDIR /root

COPY target/AK_DB-1.0-SNAPSHOT.jar AK_DB-1.0-SNAPSHOT.jar
COPY followers.txt followers.txt


CMD ["/usr/local/spark/bin/spark-submit", "--class", "UDFTrianglesMain", "--master", "local", "AK_DB-1.0-SNAPSHOT.jar", "followers.txt"]
