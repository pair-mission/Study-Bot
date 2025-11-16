package domain.meeting;

import domain.member.Member;
import domain.member.Role;

public class MeetingParticipant {
    private final Role role;
    private final Member member;
    private final Meeting meeting;
    private Long id;

    private MeetingParticipant(Role role, Member member, Meeting meeting) {
        this.role = role;
        this.member = member;
        this.meeting = meeting;
    }

    public static MeetingParticipant toEntity(Role role, Member member, Meeting meeting) {
        return new MeetingParticipant(role, member, meeting);
    }

    public boolean isSameMeetingId(Long meetingId) {
        return meeting.isSameById(meetingId);
    }

    void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }
}
