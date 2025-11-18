package controller;

import domain.member.Member;
import dto.MemberInfoDto;
import global.exception.DataAccessException;
import global.utils.parser.InputParser;
import service.MemberService;
import view.InputView;
import view.OutputView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static global.enums.ErrorMessage.INVALID_MENU_INPUT;

public class MemberController implements AppController {

    private final InputView inputView;
    private final OutputView outputView;
    private final MemberService memberService;
    private final Map<Integer, Runnable> actions;

    public MemberController(InputView inputView, OutputView outputView, MemberService memberService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.memberService = memberService;
        this.actions = new HashMap<>();
        registerAction();
    }

    private void registerAction() {
        actions.put(5, this::showAllMembers);
        actions.put(6, this::registerMember);
    }

    @Override
    public void controlAction(int option) {
        handleOption(option);
    }

    @Override
    public void handleOption(int option) {
        Runnable action = actions.get(option);
        if (action == null) {
            outputView.printErrorMessage(INVALID_MENU_INPUT.getMessage());
            return;
        }
        action.run();
    }

    private void registerMember() {
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
}
