package global.config;

import domain.meeting.Meeting;
import domain.meeting.MeetingTime;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantInMemoryRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class DataInitializer {
    public void initialize(MemberRepository memberRepository, MeetingRepository meetingRepository,
                           ParticipantInMemoryRepository participantInMemoryRepository) {
        Member member1 = Member.of(0L, "제오");
        Member member2 = Member.of(1L, "제이");

        LocalTime start = LocalTime.of(20, 45);
        LocalTime end = LocalTime.of(21, 10);

        Meeting meeting1 = Meeting.of(0L, LocalDate.now().plusDays(10),
                MeetingTime.of(start, end), "자바 스터디", "스터디룸A");
        Meeting meeting2 = Meeting.of(1L, LocalDate.now().plusDays(20),
                MeetingTime.of(start, end), "코테", "디스코드");
        Meeting meeting3 = Meeting.of(2L, LocalDate.now().plusDays(2),
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

        memberRepository.save(member1);
        memberRepository.save(member2);

        meetingRepository.save(meeting1);
        meetingRepository.save(meeting2);
        meetingRepository.save(meeting3);

        participantInMemoryRepository.save(meetingParticipant1);
        participantInMemoryRepository.save(meetingParticipant2);
        participantInMemoryRepository.save(meetingParticipant3);
    }
}
