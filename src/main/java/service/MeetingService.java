package service;

import domain.Meeting;
import dto.MeetingCreateDto;
import java.time.LocalDate;
import repository.MeetingRepository;

public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
    }

    public void save(MeetingCreateDto meetingCreateDto) {
        Meeting meeting = Meeting.toEntity(meetingCreateDto);
        meetingRepository.save(meeting);
    }
}
