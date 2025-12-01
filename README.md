# Tux Game Log System

게임 로그를 자동으로 파싱하고 MariaDB에 저장하는 Spring Boot 애플리케이션

## 프로젝트 구조

```
game-log-system/
├── src/main/java/com/tuxgaming/gamelogsystem/
│   ├── entity/          # 엔티티 클래스
│   ├── repository/      # JPA Repository
│   ├── service/         # 비즈니스 로직
│   ├── controller/      # REST API
│   ├── parser/          # 로그 파서
│   └── GameLogSystemApplication.java
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## 사전 준비

### 1. MariaDB 설치 및 설정

```bash
# MariaDB 설치
sudo apt update
sudo apt install mariadb-server

# MariaDB 시작
sudo systemctl start mariadb
sudo systemctl enable mariadb

# 보안 설정
sudo mysql_secure_installation
```

### 2. 데이터베이스 생성

```bash
sudo mysql -u root -p
```

```sql
-- 데이터베이스 생성
CREATE DATABASE game_logs CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 사용자 생성 및 권한 부여
CREATE USER 'gameuser'@'localhost' IDENTIFIED BY 'gamepass123';
GRANT ALL PRIVILEGES ON game_logs.* TO 'gameuser'@'localhost';
FLUSH PRIVILEGES;

EXIT;
```

### 3. Java 17 설치

```bash
sudo apt install openjdk-17-jdk
java -version
```

### 4. Maven 설치

```bash
sudo apt install maven
mvn -version
```

## 빌드 및 실행

### 1. 프로젝트 빌드

```bash
cd game-log-system
mvn clean package
```

### 2. 애플리케이션 실행

```bash
java -jar target/game-log-system-1.0.0.jar
```

또는

```bash
mvn spring-boot:run
```

## 설정

`src/main/resources/application.properties` 파일에서 설정 변경 가능:

```properties
# 데이터베이스 정보
spring.datasource.url=jdbc:mariadb://localhost:3306/game_logs
spring.datasource.username=gameuser
spring.datasource.password=gamepass123

# 로그 파일 경로 (Raspberry Pi 기준)
game.log.neverball=/home/pi/.neverball/easy.txt
game.log.etr=/home/pi/.config/etr/highscore
game.log.frozenbubble=/home/pi/.frozen-bubble/highscores

# 로그 스캔 주기 (밀리초, 기본 10초)
game.log.scan.interval=10000
```

## API 엔드포인트

### 1. Neverball 로그 조회
```
GET http://localhost:8080/api/logs/neverball
```

### 2. ETR 로그 조회
```
GET http://localhost:8080/api/logs/etr
```

### 3. Frozen Bubble 로그 조회
```
GET http://localhost:8080/api/logs/frozen-bubble
```

### 4. 헬스 체크
```
GET http://localhost:8080/api/logs/health
```

## 동작 방식

1. Spring Boot 애플리케이션이 시작되면 10초마다 로그 파일을 스캔
2. 파일이 변경되었으면 파싱 시작
3. 파싱된 데이터를 MariaDB에 저장
4. REST API를 통해 저장된 데이터 조회 가능

## 테이블 구조

### neverball_logs
- id (PK)
- player_name
- time_ms, time_sec
- coins
- level_name
- created_at

### etr_logs
- id (PK)
- player_name
- course_name
- points
- herrings (물고기)
- time
- created_at

### frozen_bubble_logs
- id (PK)
- player_name
- level
- piclevel
- time
- created_at

## 문제 해결

### MariaDB 연결 오류
```bash
# MariaDB 상태 확인
sudo systemctl status mariadb

# 포트 확인
sudo netstat -tlnp | grep 3306
```

### 로그 파일 경로 오류
- application.properties의 경로가 실제 파일 경로와 일치하는지 확인
- 파일 읽기 권한 확인

## 개발자
정우 - Busan Software Meister High School

## 라이선스
MIT License
# parser
