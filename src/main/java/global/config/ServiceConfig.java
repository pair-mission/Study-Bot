package global.config;

import service.AttendanceService;
import service.MeetingService;
import service.MemberService;

public class ServiceConfig {
    private final MemberService memberService;
    private final MeetingService meetingService;
    private final AttendanceService attendanceService;

    public ServiceConfig(RepositoryConfig repositoryConfig
    ) {
        this.memberService = new MemberService(repositoryConfig.getMemberRepository());
        this.meetingService = new MeetingService(repositoryConfig.getMeetingRepository(),
                repositoryConfig.getParticipantRepository());
        this.attendanceService = new AttendanceService(repositoryConfig.getAttendanceRepository(),
                repositoryConfig.getParticipantRepository());
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public MeetingService getMeetingService() {
        return meetingService;
    }

    public AttendanceService getAttendanceService() {
        return attendanceService;
    }
}
