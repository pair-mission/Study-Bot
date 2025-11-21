package global.config;

import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantInMemoryRepository;

public class DataInitializer {
    public void initialize(MemberRepository memberRepository, MeetingRepository meetingRepository,
                           ParticipantInMemoryRepository participantInMemoryRepository) {

        Member member = memberRepository.findByNickName("제이")
                .orElseThrow(() -> new IllegalStateException());

        Meeting meeting = meetingRepository.findById(3L)
                .orElseThrow(() -> new IllegalStateException());

        MeetingParticipant meetingParticipant1 = MeetingParticipant.of(
                Role.HOST,
                member,
                meeting
        );

        participantInMemoryRepository.save(meetingParticipant1);

//        memberRepository.save(member1);
//        memberRepository.save(member2);

//        meetingRepository.save(meeting1);
//        meetingRepository.save(meeting2);
//        meetingRepository.save(meeting3);
//
//        participantInMemoryRepository.save(meetingParticipant1);
//        participantInMemoryRepository.save(meetingParticipant2);
//        participantInMemoryRepository.save(meetingParticipant3);
    }
}
