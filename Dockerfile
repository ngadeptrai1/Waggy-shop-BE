# Sử dụng ảnh nền Ubuntu mới nhất cho giai đoạn xây dựng
FROM ubuntu:latest AS build

# Cập nhật danh sách gói
RUN apt-get update

# Cài đặt OpenJDK 17
RUN apt-get install openjdk-17-jdk -y

# Sao chép mã nguồn dự án vào thư mục làm việc
COPY . .

# Thư mục đích cho tệp JAR được xây dựng
WORKDIR /app

# Cài đặt Maven
RUN apt-get install maven -y

# Sao chép tệp pom.xml
COPY pom.xml ./

# Chạy lệnh Maven để xây dựng dự ảnh
RUN mvn package

# Chuyển sang ảnh nền OpenJDK 17 slim cho thời gian chạy
FROM openjdk:17-jdk-slim

# Mở cổng 8080
EXPOSE 8080

# Sao chép tệp JAR được xây dựng từ giai đoạn xây dựng sang ảnh mới
COPY --from=build /app/target/demo-1.jar app.jar

# Điểm vào chính cho ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]