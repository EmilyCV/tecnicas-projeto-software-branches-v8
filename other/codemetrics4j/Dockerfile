FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y unzip xsltproc 

WORKDIR /app

# Baixe o zip do codemetrics4j (ajuste a versão conforme necessário)
ADD https://github.com/codemetrics4j/codemetrics4j/releases/download/v1.3.0/codemetrics4j.zip /tmp/codemetrics4j.zip

RUN mkdir -p /app/codemetrics4j
RUN mkdir -p /app/root

RUN unzip /tmp/codemetrics4j.zip -d /app

RUN rm /tmp/codemetrics4j.zip && \
    apt-get remove -y unzip && \
    apt-get autoremove -y && \
    apt-get clean

WORKDIR /app/codemetrics4j

COPY entrypoint.sh /app/codemetrics4j/entrypoint.sh
COPY metrics-to-html.xslt /app/codemetrics4j/metrics-to-html.xslt

ENTRYPOINT ["/app/codemetrics4j/entrypoint.sh"]
