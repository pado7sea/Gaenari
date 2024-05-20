FROM jenkins/jenkins:2.289.3-jdk11

USER root

RUN apt-get update \
 && apt-get -y install lsb-release

RUN usermod -u 1000 jenkins

USER jenkins