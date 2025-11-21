package controller;

import domain.member.Member;
import dto.MemberInfoDto;
import global.Session;
import global.enums.MainMenu;
import global.exception.DataAccessException;
import global.utils.InputValidator;
import global.utils.parser.InputParser;
import service.MemberService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class MemberController extends AppController implements AuthHandler {

    private final MemberService memberService;

    public MemberController(MemberService memberService, InputView inputView, OutputView outputView, Session session) {
        super(inputView, outputView, session);
        this.memberService = memberService;
    }

    @Override
    protected void registerAction() {
        actions.put(MainMenu.MEMBER_LIST, this::showAllMembers);
        actions.put(MainMenu.REMIND_UPDATE, this::updateRemindDay);
    }

    @Override
    public void registerMember() {
        try {
            String userInput = inputView.getMemberNickname();
            String newUserInput = InputParser.parseToNonBlank(userInput);
            Member member = memberService.register(newUserInput);
            outputView.printRegisterSuccess(member.getNickname());
        } catch (DataAccessException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    @Override
    public void login() {
        String userNickname = inputView.getUserNickname();
        String trimmedNickname = InputParser.parseToNonBlank(userNickname);
        Member member = memberService.findByNickName(trimmedNickname);
        session.login(member);
    }

    private void showAllMembers() {
        try {
            List<MemberInfoDto> memberInfos = memberService.findAllMember();
            outputView.printAllMemberInfo(memberInfos);
        } catch (DataAccessException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void updateRemindDay() {
        String remindDayInput = inputView.getRemindDay();
        int remindDay = InputParser.parseToInt(remindDayInput);
        InputValidator.validateNegativeNumber(remindDay);
        Member loginMember = session.getLoginMember();
        memberService.updateRemindDay(loginMember, remindDay);
        outputView.printRemindUpdateSuccess(loginMember.getRemindDay());
    }

}
