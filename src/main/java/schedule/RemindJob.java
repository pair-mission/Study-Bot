package schedule;

import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import external.WebhookClient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import service.MeetingService;

public class RemindJob implements Job {

    private final MeetingService meetingService;
    private final WebhookClient webhookClient;

    public RemindJob(MeetingService meetingService, WebhookClient webhookClient) {
        this.meetingService = meetingService;
        this.webhookClient = webhookClient;
    }

    @Override
    public void run() {
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        List<MeetingParticipant> tomorrowMeetingParticipant = meetingService.findByMeetingParticipants(tomorrowDate);

        if (tomorrowMeetingParticipant.isEmpty()) {
            return;
        }

        Map<Meeting, List<Member>> meetingMembers = tomorrowMeetingParticipant.stream()
                .collect(Collectors.groupingBy(
                        MeetingParticipant::getMeeting,         // Map의 key: Meeting
                        Collectors.mapping(
                                MeetingParticipant::getMember,  // value: Member
                                Collectors.toList()
                        )
                ));

        String remindMessage = remindMessageBuilder(tomorrowDate, meetingMembers);
        webhookClient.sendMessage(remindMessage);
    }

    private String remindMessageBuilder(LocalDate tomorrowDate, Map<Meeting, List<Member>> meetingMembers) {
        StringBuilder sb = new StringBuilder();
        sb.append("📌 **내일 모임 안내**\n");
        sb.append("날짜: ").append(tomorrowDate).append("\n\n");

        for (Map.Entry<Meeting, List<Member>> entry : meetingMembers.entrySet()) {
            Meeting meeting = entry.getKey();
            List<Member> members = entry.getValue();

            String title = meeting.getTopic();
            String location = meeting.getPlace();
            LocalTime start = meeting.getMeetingTime().getStartTime();
            LocalTime end = meeting.getMeetingTime().getEndTime();

            String memberNames = members.stream()
                    .map(Member::getNickname)
                    .collect(Collectors.joining(", "));

            sb.append("• **")
                    .append("모임 이름 : ")
                    .append(title)
                    .append("**\n")
                    .append("  - 시간: ").append(start).append(" ~ ").append(end).append("\n")
                    .append("  - 장소: ").append(location).append("\n")
                    .append("  - 참여자(").append(members.size()).append("명): ")
                    .append(memberNames.isEmpty() ? "없음" : memberNames)
                    .append("\n\n");
        }
        return sb.toString();
    }
}
