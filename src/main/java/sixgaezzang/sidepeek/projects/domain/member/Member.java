package sixgaezzang.sidepeek.projects.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sixgaezzang.sidepeek.projects.domain.Project;
import sixgaezzang.sidepeek.users.domain.User;

@Entity
@Table(name = "project_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "authority", nullable = false, length = 15, columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    @Column(name = "role", nullable = false, length = 15)
    private String role;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Builder
    public Member(User user, Project project, AuthorityType authority, String role, String nickname) {
        this.user = user;
        this.project = project;
        this.authority = authority;
        this.role = role;
        this.nickname = nickname;
    }

}