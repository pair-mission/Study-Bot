package repository.participant;

import domain.participant.MeetingParticipant;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepository {

    MeetingParticipant save(MeetingParticipant participant);

    List<MeetingParticipant> findAll();

    List<MeetingParticipant> findAllParticipantsByMeetingId(Long meetingId);

    boolean isHost(Long memberId, Long meetingId);

    List<MeetingParticipant> findMeetingsByMember(Long memberId);

    Optional<MeetingParticipant> findParticipantByMeetingIdAndMemberId(Long meetingId, Long memberId);

}
