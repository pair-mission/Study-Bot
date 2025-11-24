package global.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import repository.attendance.AttendanceInMemoryRepository;
import repository.attendance.AttendanceRepository;
import repository.meeting.MeetingFileRepository;
import repository.meeting.MeetingInMemoryRepository;
import repository.meeting.MeetingRepository;
import repository.member.MemberFileRepository;
import repository.member.MemberInMemoryRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantFileRepository;
import repository.participant.ParticipantInMemoryRepository;
import repository.participant.ParticipantRepository;

public class RepositoryConfig {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
    private final AttendanceRepository attendanceRepository;

    public RepositoryConfig() {
        Properties properties = new Properties();
        String resourceName = "config.properties";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new RuntimeException("Resource not found: " + resourceName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("설정 파일 로딩 중 오류 발생", e);
        }

        String mode = properties.getProperty("repository.mode", "memory");

        if (mode.equalsIgnoreCase("file")) {
            memberRepository = new MemberFileRepository();
            meetingRepository = new MeetingFileRepository();
            participantRepository = new ParticipantFileRepository(memberRepository, meetingRepository);
            attendanceRepository = new AttendanceInMemoryRepository();
        } else {
            memberRepository = new MemberInMemoryRepository();
            meetingRepository = new MeetingInMemoryRepository();
            participantRepository = new ParticipantInMemoryRepository();
            attendanceRepository = new AttendanceInMemoryRepository();
            new DataInitializer(memberRepository, meetingRepository, participantRepository).initializeMemory();
        }
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public MeetingRepository getMeetingRepository() {
        return meetingRepository;
    }

    public ParticipantRepository getParticipantRepository() {
        return participantRepository;
    }

    public AttendanceRepository getAttendanceRepository() {
        return attendanceRepository;
    }

}
