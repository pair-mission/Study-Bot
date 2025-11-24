package controller;

import static global.enums.MainMenu.EXIT;
import static repository.meeting.MeetingFileRepository.MEETING_FILE_PATH;
import static repository.member.MemberFileRepository.MEMBER_FILE_PATH;
import static repository.participant.ParticipantFileRepository.PARTICIPANT_FILE_PATH;

import domain.meeting.Meeting;
import domain.member.Member;
import domain.participant.MeetingParticipant;
import global.utils.CsvReader;
import global.utils.parser.MeetingParser;
import global.utils.parser.MemberParser;
import global.utils.parser.ParticipantParser;
import java.io.IOException;
import java.util.List;
import repository.meeting.MeetingRepository;
import repository.member.MemberRepository;
import repository.participant.ParticipantRepository;

public class ExitController extends AppController {

    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;

    public ExitController(ControllerContext context,
                          ParticipantRepository participantRepository,
                          MemberRepository memberRepository,
                          MeetingRepository meetingRepository) {
        super(context);
        this.memberRepository = memberRepository;
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    protected void registerAction() {
        actions.put(EXIT, this::flushDataToFile);
    }

    public void flushDataToFile() {
        List<MeetingParticipant> allParticipants = participantRepository.findAll();
        List<Member> allMembers = memberRepository.findAll();
        List<Meeting> allMeetings = meetingRepository.findAll();

        try {
            CsvReader.updateAllCsv(allMembers, MEMBER_FILE_PATH, new MemberParser());
            CsvReader.updateAllCsv(allParticipants, PARTICIPANT_FILE_PATH,
                    new ParticipantParser(memberRepository, meetingRepository));
            CsvReader.updateAllCsv(allMeetings, MEETING_FILE_PATH, new MeetingParser());
            outputView.printExit();
        } catch (IOException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }
}
