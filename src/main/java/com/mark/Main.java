package com.mark;


import com.mark.cafe.actions.Recipes;
import com.mark.cafe.menus.MenuItem;
import com.mark.cafe.menus.dessert.ChocoCake;
import com.mark.cafe.menus.dessert.StrawberryCake;
import com.mark.cafe.menus.drink.*;
import com.mark.cafe.player.Player;

import java.util.*;

public class Main {
    public static void main(String[] args){

        // 랜덤으로 플레이어 초기자금과 메뉴 선택을 위해서 선언
        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        // 0~9 나오는걸 1 더해서 1 ~ 10으로 변경
        int money = (random.nextInt(10) + 1) * 500;

        System.out.println("당신은 카페 아르바이트를 하게 됐습니다.");
        System.out.println("들어오는 주문을 처리해주세요.");
        System.out.println("일정금액 이상 매출 금액을 달성 퇴근입니다.");
        System.out.println("일정금액 이하로 매출을 떨어트리면 쫓겨납니다.");

        Player player = new Player(money, 10000, 0);

        // 메뉴를 랜덤으로 뽑기 쉽게하기 위함, 다형성을 이용해서 상위 타입 참조변수로 하위 타입들 다루기 위함
        MenuItem[] menuItems = new MenuItem[]{
                new IceAmericano(),
                new Americano(),
                new GreenTeaLatte(),
                new IceGreenTeaLatte(),
                new ChocoCake(),
                new StrawberryCake()
        };


        player.checkResult();

        while(true){
            System.out.println("주문이 들어왔습니다.");
            boolean success = false;

            int menuSelect = random.nextInt(menuItems.length);

            // 선택된 메뉴 밑에서 다루기 쉽게 하기 위해서 변수에 담음
            MenuItem menu = menuItems[menuSelect];

            System.out.println("메뉴 이름은 " + menu.getName() + "입니다.");
            System.out.println("레시피를 확인하시겠습니까? Y / N");

            String checkRecipe = sc.next();

            // Y를 소문자로 쓸 수도 있으니까 upperCase 사용 및 Y를 제외한 문자는 레시피를 안보는 것으로 처리
            if("Y".equals(checkRecipe.toUpperCase())){
                menu.showRecipe();
            }

            System.out.println("제조를 시작합니다.");

            // 각 메뉴의 레시피 크기만큼의 선택지 주어지게 함 레시피 크기 이상의 반복문일때 조건이 참이 될수가 없기 때문에
            for(int i = 0; i < menu.getRecipeSteps().size(); i++){
                System.out.println("제조 방식을 올바른 레시피 순서대로 골라주세요");

                // Collections.shuffle은 리스트 원본 자체를 섞으므로 값을 새로운 리스트 객체에 할당해서 셔플
                // 각 메뉴의 올바른 제조 순서는 바뀌지 않아야 하기 때문
                List<Recipes> shuffleRecipe = new ArrayList<Recipes>(menu.getRecipeSteps());
                Collections.shuffle(shuffleRecipe);

                for(int j = 0; j < menu.getRecipeSteps().size(); j++){
                    System.out.println(j + " : " + shuffleRecipe.get(j).getStep());
                }

                System.out.println("선택지를 골라주세요");
                int userChoice;

                // 유저가 숫자가 아닌 값을 입력했을 때 InputMismatchException 예외처리를 하기 위해서 추가
                try{
                     userChoice = sc.nextInt();
                } catch (InputMismatchException e){
                    System.out.println("잘못된 행동을 했습니다 돈을 잃습니다");
                    player.loseMoney(menu.getPrice());
                    sc.nextLine();
                    break;
                }

                // 유저가 선택지 이외의 값을 입력했을 때 (에러가 아닐 때) 처리
                if(userChoice > menu.getRecipeSteps().size() -1 || userChoice < 0){
                    System.out.println("잘못된 행동을 했습니다 돈을 잃습니다");
                    player.loseMoney(menu.getPrice());
                    break;
                }

                if(!menu.checkSteps(shuffleRecipe.get(userChoice), i)){
                    System.out.println("제조 방법이 틀렸습니다 돈을 잃습니다.");
                    player.loseMoney(menu.getPrice());
                    break;
                } else {
                    menu.executeStep(i);
                }
                if(i == menu.getRecipeSteps().size() - 1){
                    success = true;
                }
            }
            if(success) {
                System.out.println("메뉴 제조에 성공했습니다");
                player.earnMoney(menu.getPrice());
            }
            player.checkResult();
            if(player.getMoney() <= player.getFailAmount()){
                System.out.println("매출을 너무 떨어트렸습니다 쫓겨났습니다");
                break;
            } else if (player.getMoney() >= player.getSuccessAmount()){
                System.out.println("매출을 성공적으로 올렸습니다 퇴근입니다 축하합니다");
                break;
            }
        }
        sc.close();
    }
}