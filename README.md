## Spring Boot RestTemplate Pooling example

* 기능 구현 목록
  + RestTemplate에 connection pool 기능을 넣어서 rest template 호출 되는지 셀프 테스트 한다.
  + 일반 RestTemplate을 만들어서 호출이 되는지 테스트 한다.
  + JMeter를 사용해서 동시 접속자수를 늘려가면서 둘의 처리 속도 및 부하정도를 확인 해 본다.
    - 정상적으로 풀에 따른 커넥션을 상대적으로 적게 사용하는것 테스트 확인
  + RestApi 응답 response에 대한 공통 처리 기능 추가
