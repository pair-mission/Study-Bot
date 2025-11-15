package view;

import dto.MemberInfoDto;
import java.util.List;

public class OutputView {

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printMenu() {
        System.out.println("안녕하세요. 스터디봇입니다.\n"
                + "\n"
                + "1. 모임 등록\n"
                + "2. 모임 수정\n"
                + "3. 모임 삭제\n"
                + "4. 모임 전체 조회\n"
                + "5. 멤버 조회\n"
                + "6. 참여자 등록\n"
                + "7. 참여자 조회\n"
                + "8. 출석 체크\n"
                + "9. 내 모임 조회\n"
                + "10. 종료");
    }

    public void printAllMemberInfo(List<MemberInfoDto> memberInfos) {
        System.out.println("[스터디 멤버]");
        for (MemberInfoDto memberInfo : memberInfos) {
            System.out.println("- " + memberInfo.nickname());
        }
    }
}
