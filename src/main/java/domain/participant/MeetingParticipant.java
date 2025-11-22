package domain.participant;

import domain.meeting.Meeting;
import domain.member.Member;

public class MeetingParticipant {

    private final Role role;
    private final Member member;
    private final Meeting meeting;
    private final Long id;

    private MeetingParticipant(Role role, Member member, Meeting meeting) {
        this(null, role, member, meeting);
    }

    private MeetingParticipant(Long id, Role role, Member member, Meeting meeting) {
        this.role = role;
        this.member = member;
        this.meeting = meeting;
        this.id = id;
    }

    public static MeetingParticipant of(Role role, Member member, Meeting meeting) {
        return new MeetingParticipant(role, member, meeting);
    }

    public static MeetingParticipant of(Long id, MeetingParticipant participant) {
        Role role = participant.getRole();
        Member member = participant.getMember();
        Meeting meeting = participant.getMeeting();

        return new MeetingParticipant(id, role, member, meeting);
    }

    public static MeetingParticipant of(Long id, Role role, Member member, Meeting meeting) {
        return new MeetingParticipant(id, role, member, meeting);
    }

    public boolean isSameMeetingId(Long meetingId) {
        return meeting.isSameById(meetingId);
    }

    public boolean isSameMemberId(Long memberId) {
        return member.isSameId(memberId);
    }

    public boolean isHost() {
        return role == Role.HOST;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public Member getMember() {
        return member;
    }

    public Meeting getMeeting() {
        return meeting;
    }
}
