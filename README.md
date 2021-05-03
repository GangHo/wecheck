# WeCheck
### GPS 기반 출석 앱
###### Admin Page , API , Android 中 API
#### 주요내용
  - 회원가입
  - 교회 내부에서 사용할 GPS 기반 출석
  - 게시판 및 광고 배너
  - 댓글
  - FCM을 활용한 Push알람 ( 댓글, 예배시간 알람 )

#### 구현내용
  - Token 기반의 로그인 방식
  - 배너만료 및 예배시간 알림을 위해 Job Scheduler (Cron)활용
  - App PUSH 알람을 위해 FCM(Firebase Cloud Messaging)활용
  - 데이터베이스 테이블 설계
  - 게시판 구현시 페이징 처리


##### - 회원가입

<img src="https://user-images.githubusercontent.com/23518342/116940656-38f59080-aca9-11eb-9caf-f469d478090f.png" width = "480" heigh = "360">

##### - 게시판 및 배너

<img src="https://user-images.githubusercontent.com/23518342/116944703-f637b680-acb0-11eb-9797-2905704c6c14.png" width = "360" heigh = "280">

##### - GPS 기반 출석
######    GPS를 킨 채로 버튼 클릭 -> 지정된 위치와 거리 계산 -> 출석완료
<img src="https://user-images.githubusercontent.com/23518342/116945421-8a564d80-acb2-11eb-9ef5-d6880aeb59fb.png" width = "360" heigh = "280">

##### - 댓글
<img src="https://user-images.githubusercontent.com/23518342/116946140-5714be00-acb4-11eb-968e-3c345bb59118.png" width = "360" heigh = "280">


