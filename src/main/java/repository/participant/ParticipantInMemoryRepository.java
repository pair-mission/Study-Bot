package repository.participant;

import domain.participant.MeetingParticipant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParticipantInMemoryRepository implements ParticipantRepository {

    private final Map<Long, MeetingParticipant> participants = new HashMap<>();
    private Long sequence = 0L;

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
    public List<MeetingParticipant> findMeetingsByMember(Long memberId) {
        return participants.values().stream().filter(participants -> participants.isSameMemberId(memberId)).toList();
    }

    @Override
    public Optional<MeetingParticipant> findParticipantByMeetingIdAndMemberId(Long meetingId, Long memberId) {
        return participants.values().stream()
                .filter(participants -> participants.getMember().isSameId(memberId) && participants.getMeeting()
                        .isSameById(meetingId))
                .findFirst();
    }

    @Override
    public List<MeetingParticipant> findMeetingParticipantByDate(LocalDate date) {
        return participants.values().stream()
                .filter(participants -> participants.getMeeting().getDate().equals(date))
                .toList();
    }
}
