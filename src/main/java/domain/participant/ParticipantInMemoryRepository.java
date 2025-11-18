package domain.participant;

import domain.meeting.Meeting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParticipantInMemoryRepository {
    private final Map<Long, MeetingParticipant> participants = new HashMap<>();
    private Long sequence = 0L;

    public void save(MeetingParticipant participant) {
        MeetingParticipant newParticipant = MeetingParticipant.of(sequence, participant);
        participants.put(sequence, newParticipant);
        sequence++;
    }

    public List<MeetingParticipant> findAllParticipantsByMeetingId(Long meetingId) {
        return participants.values().stream()
                .filter(meetingParticipant -> meetingParticipant.isSameMeetingId(meetingId))
                .collect(Collectors.toList());
    }

    public boolean isHost(Long memberId, Long meetingId) {
        return participants.values().stream()
                .anyMatch(participant -> participant.isSameMemberId(memberId)
                        && participant.isSameMeetingId(meetingId) && participant.isHost());
    }

    public List<Meeting> findMeetingsByMember(Long id) {
        return participants.values().stream().filter(participants -> participants.isSameMemberId(id))
                .map(MeetingParticipant::getMeeting).toList();
    }
}
