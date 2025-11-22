package repository.participant;

import domain.meeting.Meeting;
import domain.participant.MeetingParticipant;
import global.exception.DataAccessException;
import global.utils.CsvReader;
import global.utils.parser.ParticipantParser;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static global.enums.ErrorMessage.INVALID_FILE;

public class ParticipantFileRepository implements ParticipantRepository {

    public static final String PARTICIPANT_FILE_PATH = "src/main/resources/participants.csv";

    private Long sequence;
    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private Map<Long, MeetingParticipant> participants = new HashMap<>();

    public ParticipantFileRepository(MemberRepository memberRepository, MeetingRepository meetingRepository) {
        this.memberRepository = memberRepository;
        this.meetingRepository = meetingRepository;

        try {
            List<MeetingParticipant> csvParticipants = CsvReader.readCsv(PARTICIPANT_FILE_PATH,
                    new ParticipantParser(memberRepository, meetingRepository));

            for (MeetingParticipant participant : csvParticipants) {
                this.participants.put(participant.getId(), participant);
            }

            this.sequence = readNextSequence(csvParticipants);
        } catch (IOException e) {
            throw new DataAccessException(INVALID_FILE);
        }
    }

    private Long readNextSequence(List<MeetingParticipant> participants) {
        return participants.stream().mapToLong(MeetingParticipant::getId).max().orElse(-1L) + 1L;
    }

    @Override
    public MeetingParticipant save(MeetingParticipant participant) {
        MeetingParticipant newParticipant = MeetingParticipant.of(sequence, participant);
        participants.put(sequence, newParticipant);
        sequence++;
        return newParticipant;
    }

    @Override
    public List<MeetingParticipant> findAll() {
        return new ArrayList<>(participants.values());
    }

    @Override
    public List<MeetingParticipant> findAllParticipantsByMeetingId(Long meetingId) {
        return participants.values().stream()
                .filter(meetingParticipant -> meetingParticipant.isSameMeetingId(meetingId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isHost(Long memberId, Long meetingId) {
        return participants.values().stream()
                .anyMatch(participant -> participant.isSameMemberId(memberId)
                        && participant.isSameMeetingId(meetingId) && participant.isHost());
    }

    @Override
    public List<Meeting> findMeetingsByMember(Long memberId) {
        return participants.values().stream().filter(participants -> participants.isSameMemberId(memberId))
                .map(MeetingParticipant::getMeeting).toList();
    }

    @Override
    public Optional<MeetingParticipant> findParticipantByMeetingIdAndMemberId(Long meetingId, Long memberId) {
        return participants.values().stream()
                .filter(participants -> participants.getMember().isSameId(memberId) && participants.getMeeting()
                        .isSameById(meetingId))
                .findFirst();
    }

}
