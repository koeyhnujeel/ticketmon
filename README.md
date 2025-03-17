<img width="1315" alt="main" src="https://github.com/user-attachments/assets/de352661-9ebe-4e8b-b20e-c7a40c7e7830" />

### 📋 프로젝트 개요

티켓몬은 대규모 트래픽을 안정적으로 처리할 수 있는 공연 티켓 예매 서비스입니다. <br/>
사용자들은 실시간으로 공연 정보를 조회하고, 대기열을 거쳐 원하는 좌석을 빠르게 예매할 수 있습니다. <br/>
인터파크 티켓과 같은 대형 예매 플랫폼을 벤치마킹하였습니다. 예매 연습 사이트로 확장할 예정입니다. <br/>

<br/>

### ⚙️ 주요 기능

#### 1. 실시간 대기열 시스템

대기열 시스템을 활용하여 순차적인 입장을 보장합니다. <br/>
Redis 기반의 ZSet을 이용하여 효율적인 대기열 관리를 수행합니다.
<p float="left">
  <img src="https://github.com/user-attachments/assets/284d0c83-69a1-4b16-ad83-1d043ff97a8e" width="300" />  
</p>

<br/><br/>

#### 2. 티켓 예약 및 결제

사용자는 공연 좌석을 선택하고 예약할 수 있습니다. <br>
예약된 티켓은 일정 시간 내 결제가 완료되지 않으면 자동 취소됩니다.
<p float="left">
  <img src="https://github.com/user-attachments/assets/f7cfca8c-1965-4b89-9798-ff3727fcbcc9" width="300" />
  <img src="https://github.com/user-attachments/assets/f549d9ab-c54a-4b0d-906d-690109cb5675" width="300" />
</p>
　　　　　　좌석 선택 시뮬레이션　　　　　　　　　　　　　　랜덤한 결제 결과

<br/><br/>

#### 3. OAuth2 소셜 로그인

Google 및 Kakao와 Naver OAuth2를 활용하여 간편한 로그인 기능을 제공합니다. <br/>
사용자는 별도의 회원가입 없이 소셜 계정을 통해 빠르게 접근할 수 있습니다.
<p float="left">
  <img src="https://github.com/user-attachments/assets/f9b376a7-89e3-40fe-b7ca-286fb27c204b" width="300" />
  <img src="https://github.com/user-attachments/assets/c35f2274-0d34-44cc-a977-3eb27b74bd56" width="300" />
  <img src="https://github.com/user-attachments/assets/1c508948-466c-489b-b557-23dcb13e0572" width="300" />
</p>

<br/><br/>

#### 4. JWT 기반 인증 및 인가

OAuth2 인증 후 JWT를 활용하여 인증 및 인가를 수행합니다. <br/>
Spring Security 및 Spring Cloud Gateway를 연계하여 보안성을 강화하였습니다.
***
<br/>

![스크린샷 2025-03-17 오후 5 34 45](https://github.com/user-attachments/assets/af3ecdc9-875e-4b83-ba0b-e2b693f24a95)

##### 대기열 서버
https://github.com/koeyhnujeel/queue-server

##### 게이트웨이 서버
https://github.com/koeyhnujeel/gateway-server
***
<br/>

### 🚶‍♂️🚶‍♀️ 대기열 플로우
![image](https://github.com/user-attachments/assets/194a106e-ebf4-4d75-afca-49cb3852e6d2)

#### 1. 초기 연결 요청: 사용자가 예매하기 버튼을 클릭하면 클라이언트는 웹소켓 연결 요청을 게이트웨이 서버로 전송합니다.
#### 2. 사용자 인증: 게이트웨이 서버는 요청을 받은 후 사용자 인증 과정을 수행합니다. 이 단계에서 비로그인 사용자를 차단합니다.
#### 3. 요청 전달: 인증이 완료된 사용자의 요청은 게이트웨이 서버에서 대기열 서버로 전달됩니다.
#### 4. 웹소켓 연결: 대기열 서버는 클라이언트와 웹소켓 연결을 합니다.
#### 5. 실시간 업데이트: 연결 후 대기열 서버는 사용자의 대기 상태(대기 순번)를 실시간으로 클라이언트에게 전송합니다.
***
<br/>

### 🎫 예매 플로우
![image](https://github.com/user-attachments/assets/e591c156-8f5b-4527-be35-40f226a7542f)

#### 1. 입장 허용: 대기열 서버가 사용자의 차례가 되었을 때 입장 허용 신호를 클라이언트에게 전송합니다.
#### 2. 날짜/회차 선택: 사용자는 원하는 날짜와 회차를 선택합니다.
#### 3. 좌석 정보 제공: 티켓팅 서버는 선택된 날짜/회차에 대한 가용 좌석 정보를 응답합니다.
#### 4. 좌석 선택: 사용자가 원하는 좌석을 선택합니다.
- 좌석 선택 결과 처리:
  - 성공 시: 티켓팅 서버는 선택 정보 요약(공연, 날짜, 회차, 좌석, 가격 등)을 클라이언트에게 전달합니다.
  - 실패 시: 티켓팅 서버는 실패 사유(이미 예약된 좌석)를 클라이언트에게 전달합니다.
#### 5. 결제 요청: 좌석 선택이 성공하면 사용자는 결제하기 버튼을 클릭하여 결제를 진행합니다.
- 결제 결과 처리: 티켓팅 서버는 다양한 결제 결과(성공, 잔액 부족, 카드 정보 불일치, 네트워크 오류, 한도 초과 등)를 클라이언트에게 전달합니다.
***
<br/>

### 📊 ERD
![ticketmon-erd](https://github.com/user-attachments/assets/5ad5c321-9266-4341-8272-5e6343d2fc45)
***
<br/>

source <br/>
<a href="https://www.flaticon.com/kr/free-icons/" title="연구 아이콘">연구 아이콘 제작자: Freepik - Flaticon</a> <br/>
<a href="https://www.flaticon.com/kr/free-icons/-" title="섬기는 사람 아이콘">섬기는 사람 아이콘 제작자: smashingstocks - Flaticon</a> <br/>
<a href="https://www.flaticon.com/kr/free-icons/" title="게이트웨이 아이콘">게이트웨이 아이콘 제작자: Freepik - Flaticon</a> <br/>
<a href="https://www.flaticon.com/kr/free-icons/-" title="데이터 서버 아이콘">데이터 서버 아이콘 제작자: The Chohans Brand - Flaticon</a>
