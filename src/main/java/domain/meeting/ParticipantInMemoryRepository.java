package domain.meeting;

import java.util.HashMap;
import java.util.Map;

public class ParticipantInMemoryRepository {
    private final Map<Long, MeetingParticipant> participants = new HashMap<>();
    private Long sequence = 0L;

    public void save(MeetingParticipant participant) {
        participant.setId(sequence);
        participants.put(sequence, participant);
        sequence++;
    }
}
