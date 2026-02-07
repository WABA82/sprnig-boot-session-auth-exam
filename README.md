# Session-Based Authentication with Spring Security

스프링 시큐리티 세션 기반 인증 예제입니다. Redis를 이용한 분산 환경의 공유 세션을 지원합니다.

## 프로젝트 개요

- Java 21, Spring Boot 3.5.x
- Spring Security 기반 세션 인증
- Redis를 통한 공유 세션 관리
- MySQL 데이터베이스
- REST API 방식의 로그인/회원가입

## 핵심 기능

### 세션 관리 (Redis)

Spring Session Data Redis를 사용하여 분산 환경에서 세션을 공유합니다.

- 세션 저장소: Redis
- 세션 타임아웃: 30분 (설정 가능)
- 세션 고정 공격 방지: 로그인 시 세션 ID 변경
- 동시 세션 제어: 사용자당 최대 1개 세션 유지

### 보안 기능

**CSRF 보호**
- 쿠키 기반 CSRF 토큰 사용
- 로그인/회원가입 API는 CSRF 검증 제외

**CORS 정책**
- 허용 출처 설정 가능
- Credentials 포함 요청 지원

**인증 및 권한**
- 비밀번호: BCrypt 암호화
- 권한 기반 접근 제어 (USER, ADMIN)
- 인증 실패 시 401, 권한 없음 시 403 반환

## API 엔드포인트

| 메서드 | 엔드포인트 | 설명 | 인증 필요 |
|--------|-----------|------|---------|
| POST | `/api/auth/signup` | 회원가입 | X |
| POST | `/api/auth/login` | 로그인 | X |
| POST | `/api/auth/logout` | 로그아웃 | O |
| GET | `/api/auth/me` | 현재 사용자 정보 | O |

## 로그인 프로세스

1. 사용자 이메일과 비밀번호로 로그인 요청
2. 비밀번호 검증
3. SecurityContext 생성 및 인증 정보 설정
4. HttpSession에 SecurityContext 저장
5. Redis에 세션 정보 저장
6. 세션 쿠키(JSESSIONID) 클라이언트에 전달

### CORS 허용 출처 수정

`SecurityConfig.java`의 `corsConfigurationSource()` 메서드에서 허용할 출처를 설정합니다.

```java
config.setAllowedOrigins(Arrays.asList(
    "http://localhost:3000",
    "https://your-frontend-domain.com"
));
```

## 기술 스택

- Spring Boot 3.5.x
- Spring Security
- Spring Session Data Redis
- Spring Data JPA
- MySQL 8.0
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## 문서

- [아키텍처 가이드](docs/ARCHITECTURE.md)
- [테스트 전략](docs/TESTING.md)
