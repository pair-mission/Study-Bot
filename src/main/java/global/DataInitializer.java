package global;

import domain.meeting.Meeting;
import domain.meeting.MeetingParticipant;
import domain.meeting.MeetingRepository;
import domain.meeting.MeetingTime;
import domain.meeting.ParticipantInMemoryRepository;
import domain.member.Member;
import domain.member.MemberRepository;
import domain.member.Role;
import java.time.LocalDate;
import java.time.LocalTime;

public class DataInitializer {
    public void initialize(MemberRepository memberRepository, MeetingRepository meetingRepository,
                           ParticipantInMemoryRepository participantInMemoryRepository) {
        Member member1 = Member.of(0L, "제오");
        Member member2 = Member.of(1L, "제이");

        Meeting meeting1 = Meeting.of(0L, LocalDate.now().plusDays(1L),
                MeetingTime.of(LocalTime.now(), LocalTime.now().plusHours(2)), "자바 스터디", "스터디룸A");
        Meeting meeting2 = Meeting.of(1L, LocalDate.now().plusDays(1L),
                MeetingTime.of(LocalTime.now(), LocalTime.now().plusHours(2)), "코테", "디스코드");

        MeetingParticipant meetingParticipant1 = MeetingParticipant.toEntity(
                Role.MEMBER,
                member1,
                meeting1
        );

        MeetingParticipant meetingParticipant2 = MeetingParticipant.toEntity(
                Role.MEMBER,
                member2,
                meeting2
        );

        memberRepository.save(member1);
        memberRepository.save(member2);

        meetingRepository.save(meeting1);
        meetingRepository.save(meeting2);

        participantInMemoryRepository.save(meetingParticipant1);
        participantInMemoryRepository.save(meetingParticipant2);
    }
}
