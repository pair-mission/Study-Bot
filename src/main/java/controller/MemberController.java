package controller;

import domain.member.Member;
import dto.MemberInfoDto;
import global.exception.DataAccessException;
import global.utils.parser.InputParser;
import java.util.List;
import service.MemberService;
import view.InputView;
import view.OutputView;

public class MemberController extends AppController {

    private final MemberService memberService;

    public MemberController(MemberService memberService, InputView inputView, OutputView outputView) {
        super(inputView, outputView);
        this.memberService = memberService;
    }

    @Override
    protected void registerAction() {
        actions.put(1, this::login);
        actions.put(2, this::registerMember);
        actions.put(5, this::showAllMembers);
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
        loginMember = memberService.findByNickName(userNickname);
    }
}
