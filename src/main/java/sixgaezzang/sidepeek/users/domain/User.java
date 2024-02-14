package sixgaezzang.sidepeek.users.domain;

import static sixgaezzang.sidepeek.common.ValidationUtils.validateEmail;
import static sixgaezzang.sidepeek.common.ValidationUtils.validateMaxLength;
import static sixgaezzang.sidepeek.common.ValidationUtils.validateNotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import sixgaezzang.sidepeek.common.BaseTimeEntity;

@Entity
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    private static final int MAX_NICKNAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", length = MAX_NICKNAME_LENGTH, nullable = false, unique = true)
    private String nickname;

    @Column(name = "provider", length = 50, nullable = false, columnDefinition = "VARCHAR")
    private LoginType loginType;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Embedded
    private Password password;

    @Column(name = "introduction", length = 100)
    private String introduction;

    @Column(name = "profile_image_url", columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(name = "job", length = 30, columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private Job job;

    @Column(name = "career", length = 30, columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private Career career;

    @Column(name = "github_url", columnDefinition = "TEXT")
    private String githubUrl;

    @Column(name = "blog_url", columnDefinition = "TEXT")
    private String blogUrl;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Builder
    public User(String nickname, LoginType loginType, String email, Password password) {
        validateConstructorArguments(nickname, email);

        this.nickname = nickname;
        this.loginType = loginType;
        this.email = email;
        this.password = password;
    }

    private void validateConstructorArguments(String nickname, String email) {
        validateNickname(nickname);
        validateEmail(email, "이메일 형식이 올바르지 않습니다.");
    }

    private void validateNickname(String nickname) {
        validateNotBlank(nickname, "닉네임은 필수값입니다.");
        validateMaxLength(nickname, MAX_NICKNAME_LENGTH,
            "닉네임은 " + MAX_NICKNAME_LENGTH + "자 이하여야 합니다.");
    }

}
