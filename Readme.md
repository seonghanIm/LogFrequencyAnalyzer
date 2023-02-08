# 로그 빈도수 분석기(POJO 기반)

## 목차

1. 클래스 별 세부 설명
2. 동작 흐름 설명

## 1. 클래스 별 세부 설명

### **Main Class**

- 프로그램의 시작점으로 File input , File output 실행을 담당 합니다.
- File Input시 input.log 파일이 존재하지 않을 경우 logArrayList에 Null 값을 주입하고 이를 체크하여 프로그램이 종료 하게 됩니다.
- ApiKey, ServiceId, browser 클래스들의 findMaxFrequency를 실행시켜 반환값을 StringBuilder에 저장 후 writeFile 메소드를 사용합니다.

### **Log Class**

- log들의 정보가 담기는 클래스로 변수가 4개이고 이를 생성자를 사용하여 생성시 가독성이 떨어지기 때문에  builder 패턴을 구현하였습니다.
- getter 메소드들을 구현하여 변수에 접근할 수 있습니다.
- 상태코드 200 여부를 확인하는 checkStatusCode를 구현하였습니다.

### **FileIO Class**

- readFile, writeFile을 정적 메소드로 구현하였습니다.
- fileReader 와 BufferedReader를 사용하여 파일을 읽고 이를 커스텀 Split 객체를 사용하여 분리한뒤 ArrayList<Log> 형태로 반환하였습니다.
- input.log 파일이 존재 하지 않을 경우를 대비하여 예외 처리를 하였고, 예외 발생 시 writeFile 메소드를 이용해 output.log 파일에 Exception stack과 에러 문구를 입력하고 프로그램을 종료 하게 구현하였습니다.

### **Frequency Interface**

- 로그 정보의 병렬적 확장, 즉 로그 정보의 종류가 추가되었을 때, 그것의 빈도수를 측정할 경우를 대비하여 팩토리 메소드 패턴을 구현하였습니다.
- 추상 메소드로 findMaxFrequency를 가지고 있습니다.
- Java8 이후 부터 interface 내부에 static 메소드가 구현 가능하게 되어 하위 클래스의 공통된 기능들을 두가지의 static 메소드로 구현하였습니다.
- findFrequency 메소드는 String Array를 입력받아 그 값들을 HashMap key로 사용합니다. HashMap의 value는 containsKey 메소드를 활용하여 Array값의 빈도수로서 저장하였습니다.
    - Array 내부의 값들의 접근을 위해 Java8의 기능 중 하나인 stream을 사용하였습니다.
- sortHashKeySet 메소드는 hashMap의 정렬을 수행합니다.
    - keySet() 메소드를 활용하여 List에 key 값들을 저장합니다.
    - List의 sort 메소드를 활용하여 정렬합니다. sort 메소드는 List Interface 내부에 default 메소드로 구현되어 있습니다. 때문에 이를 자바8 기능 중 하나인 람다식으로 재정의 하였습니다.
    - value의 값에 따라 정렬된 Key가 담긴 KeySet list를 반환합니다.

### Split Class

- 문자열을 자르기 위한 클래스입니다.
- splitString 메소드는 input.log 문자열 한줄씩 나누는 역할을 합니다.
    - 나누는 기준은 대괄호 기준이며, 여는 대괄호 ‘[’ 를 지우고 닫는 대괄호 “]”를 기준으로  문자열을 자릅니다.
    - 나눠진 문자열들은 Log의 빌더 메소드를 통해 Log 객체에 저장 됩니다.
    - 저장된 Log 객체는 logArrayList에 추가 후 반환됩니다.
- splitApiKey 메소드는 URL에서 apiKey를 추출하기 위해 사용합니다.
    - 나누기 전 Log 객체의 checkStatusCode 메소드를 사용하여 상태코드 200 여부를 확인합니다.
    - 나누는 기준은 ‘=’ 과 ‘&’ 기준입니다.
    - 올바른 로그일 경우 상태코드가 200일 때, 올바른 URL을 가지게 됩니다. 하지만 예상치 못한 예외를 처리하기 위해 ‘=’ 와 ‘&’ 가 없는 경우 output 파일에 에러 내용을 입력하고 프로그램을 종료합니다.
    - subString 메소드를 사용하여 위의 기준으로 URL을 자릅니다.
    - 나눠진 문자를 String array 형식으로 반환합니다.
- splitServiceId 메소드는 URL에서 serviceId를 추출하기 위해 사용합니다.
    - 나누기 전 Log 객체의 checkStatusCode 메소드를 사용하여 상태코드 200 여부를 확인합니다.
    - 나누는 기준은  ’[http://apis.daum.net/search/](http://apis.daum.net/search/)’ ,  ‘?’ 입니다.
    - 올바른 로그일 경우 상태코드가 200일 때, 올바른 URL을 가지게 됩니다. 하지만 예상치 못한 예외를 처리하기 위해 ‘?’ 가 없는 경우 output 파일에 에러 내용을 입력하고 프로그램을 종료합니다.
    - subString 메소드를 사용하여  위의 기준으로 URL을 자릅니다.
    - 나눠진 문자를 String array 형식으로 반환합니다.
- makeBrowserArray 메소드는 Log 객체의 browser 변수의 값들을 Array로 만들기위해 동작합니다.
    - browser는  Log 객체의 getWebBrowser 메소드로 가져와 가공 없이 사용할 수 있지만 apiKey, serviceId 와의  코드 재사용성을 높이기 위하여 String Array 형태로 변환하였습니다.

### ApiKey Class

- Frequency interface를 상속한 클래스입니다.
- findMaxFrequency는 interface에서 상속한 메소드입니다.
    - Split 클래스의 splitApiKey 메소드를 사용하여 String Array를 반환받습니다.
    - String Array는 빈도수를 찾기 위한 Frequency 인터페이스의 findFrequency의 메개변수로 사용됩니다. findFrequency는 빈도수가 저장된 HashMap을 반환합니다.
    - HashMap은 Frequency 인터페이스의 sortHashKeySet 메소드를 통해 정렬된 KeySet을 반환받습니다.
    - KeySet의 가장 첫번째 인덱스를 StringBuilder에 저장 후 반환합니다.

### **Browser Class**

- Frequency interface를 상속한 클래스입니다.
- findMaxFrequency는 interface에서 상속한 메소드입니다.
    - Split 클래스의 makeBrowserArray 메소드를 사용하여 String Array를 반환받습니다.
    - String Array는 빈도수를 찾기 위한 Frequency 인터페이스의 findFrequency의 메개변수로 사용됩니다. findFrequency는 빈도수가 저장된 HashMap을 반환합니다.
    - HashMap은 Frequency 인터페이스의 sortHashKeySet 메소드를 통해 정렬된 KeySet을 반환받습니다.
    - KeySet의 모든 값들의 비율을 계산하여 StringBuilder에 저장 후 반환합니다.
    - 나누기 연산의 0으로 나눠지는 경우를 예외 처리하기 위하여, ArithmeticException 예외 처리합니다.

### **ServiceId Class**

- Frequency interface를 상속한 클래스입니다.
- findMaxFrequency는 interface에서 상속한 메소드입니다.
    - Split 클래스의 splitServicedId 메소드를 사용하여 String Array를 반환받습니다.
    - String Array는 빈도수를 찾기 위한 Frequency 인터페이스의 findFrequency의 메개변수로 사용됩니다. findFrequency는 빈도수가 저장된 HashMap을 반환합니다.
    - HashMap은 Frequency 인터페이스의 sortHashKeySet 메소드를 통해 정렬된 KeySet을 반환받습니다.
    - 정렬된 KeySet 중 0~2 까지의 인덱스를 StringBuilder에 저장 후 반환합니다.

## 2. 동작 흐름 설명

1. Main클래스의  Main Method 에서 FIileIO 클래스의 readFile 메소드를 실행하여 파일을 입력받습니다.(FileNotFoundException 으로 예외 처리)
    1. 입력받은 파일을  Log 객체로 생성(가독성을 위해 builder 사용) 후 그 Log 객체를 ArrayList에 담아 반환 합니다.
    2. apiKey, browser, serviceId 객체의 findMaxFrequency 실행, 메소드 인자로 ArrayList<Log> 사용합니다.
2. findMaxFrequency는 인터페이스 Frequency의 추상 메소드입니다.
    1. api 클래스의  findMaxFrequency는 splitApiKey를 호출하여, apikey를 apiArray에 저장 합니다.
    2. Frequency 인터페이스의  findFrequency static 메소드를 사용하여  apiArray를 Map<apiKey, 빈도수> 형태로 저장합니다.
    3. Frequency 인터페이스의 sortHashKeySet static 메소드를 사용하여 Map을 빈도수 기준 정렬한 KeySet 을 반환받습니다.
    4. browser, serviceId 클래스도 makeBrowserArray, splitServiceId 를 사용하여 Array 생성 후 b,c 와 동일하게 동작합니다.
3. 2번 동작의 결과를  모두 StringBuilder output에 저장 후 FileIO 객체의 writeFile 메소드를 사용하여 output 파일 생성