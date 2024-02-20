package sixgaezzang.sidepeek.projects.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixgaezzang.sidepeek.projects.domain.member.Member;
import sixgaezzang.sidepeek.projects.dto.request.MemberSaveRequest;
import sixgaezzang.sidepeek.projects.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    MemberRepository memberRepository;

    public void saveAll(List<MemberSaveRequest> memberSaveRequests) {
        List<Member> members = memberSaveRequests.stream().map(
            member -> Member.builder()
                .nickname(member.nickname())
                .userId(member.id())
                .role(member.category())
                .build()
        ).toList();

        memberRepository.saveAll(members);
    }
}
