package global.config;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import java.time.LocalDate;
import java.time.LocalTime;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantRepository;

public class DataInitializer {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    public DataInitializer(MemberRepository memberRepository, MeetingRepository meetingRepository,
                           ParticipantRepository participantRepository) {
        this.memberRepository = memberRepository;
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
    }

    public void initializeMemory() {

        Member member1 = Member.of(0L, "제오", 1); // meeting1의 호스트
        Member member2 = Member.of(1L, "제이", 3); // meeting2의 호스트, meeting3의 멤버
        Member member3 = Member.of(2L, "모모", 1); // meeting3의 호스트

        LocalTime start = LocalTime.of(20, 45);
        LocalTime end = LocalTime.of(21, 10);

        Meeting meeting1 = Meeting.of(0L, LocalDate.now().plusDays(10),
                MeetingTime.of(start, end), "자바 스터디", "스터디룸A");
        Meeting meeting2 = Meeting.of(1L, LocalDate.now().plusDays(20),
                MeetingTime.of(start, end), "코테", "디스코드");
        Meeting meeting3 = Meeting.of(2L, LocalDate.now().plusDays(3),
                MeetingTime.of(start, end), "우테코", "디스코드");

        MeetingParticipant meetingParticipant1 = MeetingParticipant.of(
                Role.HOST,
                member1,
                meeting1
        );

        MeetingParticipant meetingParticipant2 = MeetingParticipant.of(
                Role.HOST,
                member2,
                meeting2
        );

        MeetingParticipant meetingParticipant3 = MeetingParticipant.of(
                Role.MEMBER,
                member2,
                meeting3
        );

        MeetingParticipant meetingParticipant4 = MeetingParticipant.of(
                Role.HOST,
                member3,
                meeting3
        );

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        meetingRepository.save(meeting1);
        meetingRepository.save(meeting2);
        meetingRepository.save(meeting3);

        participantRepository.save(meetingParticipant1);
        participantRepository.save(meetingParticipant2);
        participantRepository.save(meetingParticipant3);
        participantRepository.save(meetingParticipant4);
    }
}
