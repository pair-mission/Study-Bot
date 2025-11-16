package view;

import domain.meeting.Meeting;
import dto.MeetingInfoDto;
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
                + "6. 멤버 등록\n"
                + "7. 참여자 등록\n"
                + "8. 참여자 조회\n"
                + "9. 출석 체크\n"
                + "10. 내 모임 조회\n"
                + "11. 종료");
    }

    public void printAllMemberInfo(List<MemberInfoDto> memberInfos) {
        System.out.println("[스터디 멤버]");
        for (MemberInfoDto memberInfo : memberInfos) {
            System.out.println("- " + memberInfo.nickname());
        }
    }

    public void printRegisterSuccess(String nickname) {
        System.out.println(nickname + "님 등록이 완료되었습니다.");
    }

    public void printAllMeetingInfo(List<MeetingInfoDto> meetingInfos) {
        System.out.println("[전체 모임 목록]");
        for (MeetingInfoDto meetingInfo : meetingInfos) {
            System.out.println("-" + meetingInfo.meetingInfo() + " / 참여인원 " + meetingInfo.participantsCount() + "명");
        }
    }

    public void printMeetingRegisterSuccess() {
        System.out.println("모임이 등록되었습니다.");
    }

    public void printMeetingUpdateSuccess() {
        System.out.println("모임 정보가 수정되었습니다.");
    }

    public void printMeetingDeleteMessage() {
        System.out.println("모임이 삭제 되었습니다.");
    }

    public void printMyMeetings(List<Meeting> myMeetings) {
        System.out.println("[내가 참여 중인 모임 목록]");

        for (Meeting meeting : myMeetings) {
            System.out.println(meeting.toString());
        }
    }
}