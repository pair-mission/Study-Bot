package service;

import static global.enums.ErrorMessage.MEMBER_NOT_FOUND;

import domain.member.Member;
import domain.member.MemberRepository;
import global.enums.ErrorMessage;
import global.utils.InputValidator;
import global.utils.parser.InputParser;
import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member register(String nickname) {
        boolean existsByNickname = memberRepository.existsBy(nickname);

        if (existsByNickname) {
            throw new IllegalArgumentException(ErrorMessage.MEMBER_ALREADY_EXISTS.getMessage());
        }

        Member member = Member.from(nickname);
        memberRepository.save(member);

        return member;
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    public Member findByNickName(String nickname) {
        InputValidator.validateBlankInput(nickname);
        String trimmedNickname = InputParser.parseToValidString(nickname);

        Member member = memberRepository.findByNickName(trimmedNickname);

        if (member == null) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }

        return member;
    }

    public Member findById(long id) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            throw new IllegalArgumentException(MEMBER_NOT_FOUND.getMessage());
        }

        return member;
    }

}
