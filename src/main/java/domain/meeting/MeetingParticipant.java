package domain.meeting;

import domain.member.Role;

public class MeetingParticipant {
    private final Role role;
    private final Long memberId;
    private final Long meetingId;
    private Long id;

    private MeetingParticipant(Role role, Long memberId, Long meetingId) {
        this.role = role;
        this.memberId = memberId;
        this.meetingId = meetingId;
    }

    public static MeetingParticipant toEntity(Role role, Long memberId, Long meetingId) {
        return new MeetingParticipant(role, memberId, meetingId);
    }

    void setId(Long id) {
        this.id = id;
    }
}
