package controller;

import domain.member.Member;
import dto.MemberInfoDto;
import global.Session;
import global.exception.DataAccessException;
import global.utils.parser.InputParser;
import service.MemberService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class MemberController extends AppController {

    private final MemberService memberService;

    public MemberController(MemberService memberService, InputView inputView, OutputView outputView, Session session) {
        super(inputView, outputView, session);
        this.memberService = memberService;
    }

    @Override
    protected void registerAction() {
        actions.put(1, this::login);
        actions.put(2, this::registerMember);
        actions.put(5, this::showAllMembers);
        actions.put(13, this::updateRemindDay);
    }

    public void registerMember() {
        try {
            String userInput = inputView.getMemberNickname();
            String newUserInput = InputParser.parseToValidString(userInput);
            Member member = memberService.register(newUserInput);
            outputView.printRegisterSuccess(member.getNickname());
        } catch (DataAccessException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void showAllMembers() {
        try {
            List<MemberInfoDto> memberInfos = memberService.findAllMember().stream().map(MemberInfoDto::from).toList();
            outputView.printAllMemberInfo(memberInfos);
        } catch (DataAccessException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    public void login() {
        String userNickname = inputView.getUserNickname();
        Member member = memberService.findByNickName(userNickname);
        session.login(member);
    }

    private void updateRemindDay() {
        String remindDayInput = inputView.getRemindDay();
        int remindDay = InputParser.parseToInt(remindDayInput);
        Member loginMember = session.getLoginMember();
        loginMember.updateRemindDay(remindDay);
        outputView.printRemindUpdateSuccess(loginMember.getRemindDay());
    }

}
