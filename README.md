# spring-boot-session-auth-exam

![Java](https://img.shields.io/badge/Java-21-blue?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.4-orange?style=flat-square)
![Redis](https://img.shields.io/badge/Redis-9.0.1-red?style=flat-square)

인증/인가(Authentication & Authorization)는 애플리케이션 보안에서 가장 핵심적인 요소 중 하나이지만, 실무에서는 이미 구축된 구조를 유지·개선하는 경우가 많아 처음부터 직접 설계하고 구현해볼 기회가 비교적 적습니다.

본 프로젝트는 애플리케이션을 처음부터 끝까지 구성하며 인증/인가 흐름을 깊이 이해하는 것을 목표로, 세션 기반 인증 구조를 직접 설계하고 구현한 예제입니다.

세션 방식은 JWT 기반 인증에 비해 상대적으로 보안성이 높지만, 서버 확장성과 유연성이 떨어진다는 단점이 있습니다.
이를 보완하기 위해 Redis를 활용한 공유 세션 구조를 적용하여 다중 서버 환경에서도 세션 기반 인증이 가능하도록 구성했습니다.

또한 JWT 방식과 비교했을 때, 세션 기반 인증은 구현 및 설정이 직관적이고 단순하다는 장점이 있어 해당 방식의 실무 활용 가능성까지 함께 고려했습니다.

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
