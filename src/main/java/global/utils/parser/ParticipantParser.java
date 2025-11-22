package global.utils.parser;

import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import domain.participant.Role;
import global.enums.ErrorMessage;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;

import java.io.BufferedWriter;
import java.io.IOException;

public class ParticipantParser implements CsvParser<MeetingParticipant> {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;

    public ParticipantParser(MemberRepository memberRepository, MeetingRepository meetingRepository) {
        this.memberRepository = memberRepository;
        this.meetingRepository = meetingRepository;
    }

    @Override
    public MeetingParticipant parse(String[] csv) {
        Long id = Long.parseLong(csv[0]);

        Long memberId = Long.parseLong(csv[1]);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEMBER_NOT_FOUND.getMessage()));

        Long meetingId = Long.parseLong(csv[2]);
        Meeting meeting = meetingRepository.findById(meetingId).
                orElseThrow(() -> new IllegalArgumentException(ErrorMessage.MEETING_NOT_FOUND.getMessage()));

        Role role = Role.valueOf(csv[3]);

        return MeetingParticipant.of(id, role, member, meeting);
    }

    @Override
    public void write(MeetingParticipant participant, BufferedWriter writer, boolean isEmptyFile) throws IOException {
        if (isEmptyFile) {
            writer.write("id,member_id,meeting_id,role\n");
        }

        writer.write(participant.getId() + "," + participant.getMember().getId() + "," + participant.getMeeting().getId() + "," + participant.getRole());
        writer.newLine();
    }

    @Override
    public void update(MeetingParticipant participant, BufferedWriter writer, String[] csv) throws IOException {

    }
}
