package view;

import domain.meeting.Meeting;
import dto.MeetingAttendanceDto;
import dto.MeetingInfoDto;
import dto.MemberInfoDto;
import java.util.List;

public class OutputView {

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printMenu() {
        System.out.println(" [메뉴 목록]\n1. 모임 등록\n"
                + "2. 모임 수정\n"
                + "3. 모임 삭제\n"
                + "4. 모임 전체 조회\n"
                + "5. 멤버 조회\n"
                + "6. 멤버 등록\n"
                + "7. 참여자 등록\n"
                + "8. 참여자 조회\n"
                + "9. 내 모임 조회\n"
                + "10. 출석 체크\n"
                + "11. 출석 기록 조회\n"
                + "12. 나의 다음 모임 조회\n"
                + "14. 종료");
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

    public void printParticipantSuccess() {
        System.out.println("참여 완료되었습니다.");
    }

    public void printAllParticipants(List<String> participantNicknames) {
        System.out.println("[참여자 목록]");
        for (String participant : participantNicknames) {
            System.out.println(" - " + participant);
        }
    }

    public void printIsTomorrowMeetings(List<Meeting> meetings) {
        System.out.println("[내일 스터디 리마인드]");
        for (Meeting meeting : meetings) {
            System.out.println(" - " + meeting.toString());
        }
    }

    public void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }

    public void printAttendanceSuccess() {
        System.out.println("출석이 완료되었습니다.");
    }

    public void printAttendanceHistory(List<MeetingAttendanceDto> attendanceHistory) {
        System.out.println("[출석 기록]");
        for (MeetingAttendanceDto meetingAttendance : attendanceHistory) {
            System.out.println(" - " + meetingAttendance.meeting() + " / 현재 출석자: " + meetingAttendance.attenders());
        }
    }

    public void printMyNextMeeting(Meeting myNextMeeting) {
        System.out.println("[다가오는 모임]");
        System.out.println(myNextMeeting.toString());
    }
}