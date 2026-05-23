# 회고

## 멀티 스레드 도입
카페 시뮬레이터 구조를 설계하면서, 현실의 카페처럼 주문은 제조와 별개로 계속 들어오고 제조만 순차적으로 진행되는 구조를 구현하고 싶었습니다.그래서 주문을 적재하는 서비스를 별도 스레드로 분리했습니다.
### 문제상황
1. 주문적재 서비스는 지속적으로 주문을 큐에 저장하고, 제조 서비스는 동일한 큐에서 주문을 하나씩 가져와 처리하도록 구성했습니다.
2. 제조 서비스가 주문이 들어오기 전에 큐를 먼저 읽는 상황이 발생했고, 이때 null 값을 가져와 내부 값을 참조하면서 NPE가 발생했습니다.

<img width="1192" height="69" alt="img" src="https://github.com/user-attachments/assets/7029396c-d8c9-45ad-9e1f-57f60bd0690b" />

### 해결
1. 해결 방법으로 두가지를 고려했습니다.
   - 큐의 길이를 계속 확인하면서 값이 있을 때만 가져오는 방식
   - BlockingQueue를 사용해 값이 들어올 때까지 자동으로 대기하는 방식
2. 그 결과, 큐 상태를 반복적으로 확인하는 방식보다 BlockingQueue를 사용해 데이터가 들어올 때까지 스레드를 block 상태로 대기시키는 방식이 더 효율적이라고 판단했습니다.
3. 적용 후 주문이 아직 들어오지 않았다면 제조 서비스에서 대기하고, 주문을 주문 서비스에서 넣었을때만 서비스가 실행돼서 같은 문제로 NPE가 발생하지 않았습니다.

```java
        //  주문 서비스
        try {
            Order order = orderService.receiveOrder();
            outputView.printMessage(DISPLAY_ORDER);
            boolean isInserted = orderQueue.offer(order);
            if(!isInserted){
                outputView.printMessage(DOWN_RATING);
                player.downRating();
            }
        }
            
        // 제조 서비스
        try {
            order = orderQueue.take();
        } 
```

## 1주차 코드리뷰 반영 사항
1. 리스트 반환시에 List.copyOf를 사용해서 외부에서 객체의 실제 리스트를 참조할 수 없게 변경했습니다.
```java

    public List<Recipes> getRecipeSteps(){
        return List.copyOf(recipeSteps);
    }

```
2. Main Class의 책임 분리
- InputView(입력 책임)
- OutPutView(출력 책임)
- OrderService(주문 생성 책임)
- PlayService(게임 플레이 책임)
- OrderProducer(주문 적재 책임)
- 기존 구조는 MainClass가 너무 많은 책임을 지고 있어서 기능 수정 및 에러 발생시 파악이 어려웠습니다. 그래서 각 책임을 분리하여 각 객체의 역할을 명확하게 하고, 기능 수정이나 에러 발생 시 영향 범위를 쉽게 파악할 수 있도록 했습니다.

<img width="155" height="122" alt="image" src="https://github.com/user-attachments/assets/91c92b27-fa90-4caa-98c3-0eef9d771062" />

3. 메뉴들을 상속으로 하나씩 생성하지 않고 열거형으로 분리
- 기존에 메뉴별로 클래스를 생성해서 Drink, Dessert의 추상 클래스를 상속받아서 사용하던것을 열거형을 사용해서 메뉴별로 분리
- 코드리뷰 기반으로 생각해보니 현재 구조에서는 나중에 메뉴 추가까지 생각했을때 열거형으로 메뉴별로 분리하는게 맞다고 생각해서 변경했습니다. 만약 기존 구조를 유지했다면 메뉴가 100개생기면 클래스도 100개가 생겼을 것 같습니다.
<img width="429" height="183" alt="image" src="https://github.com/user-attachments/assets/46f365bc-a5e0-4d63-8575-4cda435b90e1" />

<img width="509" height="241" alt="image" src="https://github.com/user-attachments/assets/eaa6adfa-72ed-4e06-b926-559f42dc1c20" />

4. 상수화 가능한 값들 상수화 처리
- 게임 목표 금액, 게임 실패 금액, 단순 문자열 출력(상수) 등을 전부 상수화 했습니다.
- 재사용 할 수도 있고, 나중에 안내 내용이 바뀔수도 있는 값들을 분리하는게 향후에 유지보수 하기 편할 것 같아서 변경했습니다.
  <img width="606" height="274" alt="image" src="https://github.com/user-attachments/assets/abc04f9b-5a4f-4376-856c-d848e3c503a6" />


## 질문
1. 현재 프로젝트는 스레드 하나만 추가로 사용하는 구조라 ExecutorService는 과하다고 판단해 사용하지 않았습니다. 이후 스레드가 늘어날 가능성을 미리 생각해서 지금부터 ExecutorService 같은 다중 스레드 관리 구조를 도입하는 게 더 좋은 설계일까요? 아니면 현재 요구사항에 맞게 단순하게 구현하고, 필요할 때 확장하는 방향이 더 적절할까요?
2. 출력 책임 분리를 위해 OutputView를 만들었는데, PlayService처럼 중간 계층에서 사용하는 건 괜찮다고 생각했습니다. 그런데 OrderProducer에서도 주문 밀림 시 직접 OutputView로 출력하고 있는데, 비즈니스 로직 객체가 출력 책임까지 가져도 괜찮은 구조인지, 아니면 밀림 상태만 전달하고 다른 계층에서 출력하도록 분리하는게 더 좋은 구조인지 고민이 됩니다. 또한 출력뿐만이 아니라 주문밀림시에 player의 평점도 OrderProducer에서 같이 낮추고 있는데 이것도 마찬가지로 밀림 상태를 반환한 뒤에 다른 계층에서 player의 평점을 낮추는쪽으로 분리하는게 맞는지도 궁금합니다.

## 의도
### BlockingQueue의 구현체를 ArrayBlockingQueue로 한 이유
최대 주문 대기 수를 명확하게 제한하고 싶어서 BlockingQueue 구현체로 ArrayBlockingQueue를 선택했습니다.LinkedBlockingQueue도 크기 제한은 가능하지만, 내부적으로 노드를 계속 생성하는 구조라 추가적인 메모리 할당 비용이 발생한다고 판단했습니다. ArrayBlockingQueue는 고정 크기 배열 기반이라 현재 프로젝트처럼 최대 큐 크기가 명확한 상황에서 더 적합하다고 생각했습니다.

### PlayService에서 BlockingQueue에 대한 값을 가져올때 take() 메소드를 사용한 이유
take() 메소드의 경우엔 큐에 값이 없으면 계속해서 대기하지만, 주문이 5초~15초 간격으로 계속 생성되는 제 현재 구조상 생성하는쪽이 항상 빠르다고 생각해서 poll을 사용하지 않았습니다.

### OrderPuducer에서 BlockingQueue에 대한 값을 넣을때 offer() 메소드를 사용한 이유
게임 룰중 하나인 주문 밀림시 평점 감소에 대한 처리를 위해 사용했습니다. put()을 사용한 경우 큐가 가득차면 계속해서 대기하는데, offer를 사용하게 되면 큐가 가득찼다면 바로 false를 반환해서 그걸 이용해서 주문 밀림에대한 처리를 위해서 사용했습니다.

### RecipeConverter를 따로 분리한 이유
출력을 담당하는 OutputView는 최대한 복잡한 동작을 하지않고 출력에 집중하게끔 하고싶어서 만들었습니다. 그리고 RecipeConverter는 상태를 가지지 않고 단순 변화 기능만 수행하는 유틸성 클래스라고 판단해서 메서드를 static으로 선언했습니다.

### Player Class에 rating을 AtomicType으로 선언한이유
player의 rating을 낮추고, 올리는 동작을 다른 두 스레드에서 수행하기 때문에 주문 밀림 시점(rating 감소)에 유저가 음식제조를 성공(rating 상승)하거나 제조를 성공했음에도 주문시간에서 많이 지났을 때(rating 감소)의 경우에 동시에 같은 변수에 접근해서 race condition이 발생할 수 있기 때문에 멀티 스레드환경에서 안전한 AtomicType으로 선언했습니다.

### 아쉬운 점
- 설계를 나름 해보려고 했는데, 막상 개발중에 객체간의 책임이 모호해지거나(orderProducer에서 OutputView 사용, Player rating 감소)하는 부분이 생겨서 이런 설계적인 부분을 더 보완하고 싶습니다.

### 잘한 점
- 기술이나 클래스를 사용할때 그게 왜 필요한지 계속 고민하면서 개발했습니다. 예전에 개발할때보다 그래도 의도를 가지고 개발하는 능력은 점점 생기는 것 같아서 좋았습니다.






