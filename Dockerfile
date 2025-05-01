FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY MyInfArith/ MyInfArith/
COPY raw_source/arbitraryarithmetic raw_source/arbitraryarithmetic
COPY src/ src/
COPY MIA.java .
COPY my_exe .
COPY infarith.py .
COPY pom.xml .
COPY report.pdf .
COPY README.html .

RUN mvn clean install

RUN apt update

RUN apt install -y python3 python3-pip

RUN javac raw_source/arbitraryarithmetic/*.java

RUN javac MIA.java