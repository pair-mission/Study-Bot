package repository.participant;

import domain.meeting.Meeting;
import domain.participant.MeetingParticipant;

import java.util.*;
import java.util.stream.Collectors;

public class ParticipantInMemoryRepository {
    private final Map<Long, MeetingParticipant> participants = new HashMap<>();
    private Long sequence = 0L;

    public MeetingParticipant save(MeetingParticipant participant) {
        MeetingParticipant newParticipant = MeetingParticipant.of(sequence, participant);
        participants.put(sequence, newParticipant);
        sequence++;
        return newParticipant;
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

    public Optional<MeetingParticipant> findParticipantByMeetingIdAndMemberId(Long meetingId, Long memberId) {
        return participants.values().stream()
                .filter(participants -> participants.getMember().isSameId(memberId) && participants.getMeeting()
                        .isSameById(meetingId))
                .findFirst();
    }

    public List<MeetingParticipant> findAll() {
        return new ArrayList<>(participants.values());
    }
}
