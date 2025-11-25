package schedule;

import domain.meeting.Meeting;
import java.time.LocalDate;
import java.util.List;
import service.MeetingService;

public class RemindJob implements Job {

    private final MeetingService meetingService;

    public RemindJob(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Override
    public void run() {
        LocalDate tomorrowDate = LocalDate.now().plusDays(1);
        List<Meeting> tomorrowMeetings = meetingService.findTomorrowMeetings(tomorrowDate);

        if (tomorrowMeetings.isEmpty()) {
            System.out.println("\n[알림] 내일 시작되는 모임이 없습니다.");
            return;
        }
        System.out.println("\n[알림] 내일 시작되는 모임:");
        for (Meeting meeting : tomorrowMeetings) {
            System.out.println(meeting.toString());
        }
    }
}
