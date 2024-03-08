package sixgaezzang.sidepeek.users.domain;

import static sixgaezzang.sidepeek.common.util.SetUtils.isSetPossible;
import static sixgaezzang.sidepeek.common.util.ValidationUtils.validateEmail;
import static sixgaezzang.sidepeek.common.util.ValidationUtils.validateGithubUrl;
import static sixgaezzang.sidepeek.users.exception.message.UserErrorMessage.EMAIL_FORMAT_INVALID;
import static sixgaezzang.sidepeek.users.exception.message.UserErrorMessage.USER_ALREADY_DELETED;
import static sixgaezzang.sidepeek.users.util.UserConstant.MAX_CAREER_LENGTH;
import static sixgaezzang.sidepeek.users.util.UserConstant.MAX_EMAIL_LENGTH;
import static sixgaezzang.sidepeek.users.util.UserConstant.MAX_INTRODUCTION_LENGTH;
import static sixgaezzang.sidepeek.users.util.UserConstant.MAX_JOB_LENGTH;
import static sixgaezzang.sidepeek.users.util.UserConstant.MAX_NICKNAME_LENGTH;
import static sixgaezzang.sidepeek.users.util.validation.UserValidator.validateBlogUrl;
import static sixgaezzang.sidepeek.users.util.validation.UserValidator.validateIntroduction;
import static sixgaezzang.sidepeek.users.util.validation.UserValidator.validateNickname;
import static sixgaezzang.sidepeek.users.util.validation.UserValidator.validateProfileImageUrl;

import io.micrometer.common.util.StringUtils;
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
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.password.PasswordEncoder;
import sixgaezzang.sidepeek.common.domain.BaseTimeEntity;
import sixgaezzang.sidepeek.users.dto.request.UpdateUserProfileRequest;

@Entity
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", length = MAX_NICKNAME_LENGTH, unique = true)
    private String nickname;

    @Column(name = "email", length = MAX_EMAIL_LENGTH, unique = true)
    private String email;

    @Embedded
    private Password password;

    @Column(name = "introduction", length = MAX_INTRODUCTION_LENGTH)
    private String introduction;

    @Column(name = "profile_image_url", columnDefinition = "TEXT")
    private String profileImageUrl;

    @Column(name = "job", length = MAX_JOB_LENGTH, columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private Job job;

    @Column(name = "career", length = MAX_CAREER_LENGTH, columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private Career career;

    @Column(name = "github_url", columnDefinition = "TEXT")
    private String githubUrl;

    @Column(name = "blog_url", columnDefinition = "TEXT")
    private String blogUrl;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Builder
    public User(String nickname, String email, Password password, String introduction,
                String profileImageUrl, Job job, Career career, String githubUrl, String blogUrl) {
        validateConstructorArguments(nickname, email);

        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.profileImageUrl = profileImageUrl;
        this.job = job;
        this.career = career;
        this.githubUrl = githubUrl;
        this.blogUrl = blogUrl;
    }

    public boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return password != null && password.check(rawPassword, passwordEncoder);
    }

    public void update(UpdateUserProfileRequest request) {
        // Required
        setNickname(request.nickname());

        // Option
        setIntroduction(request.introduction());
        setProfileImageUrl(request.profileImageUrl());
        setJob(request.job());
        setCareer(request.career());
        setGithubUrl(request.githubUrl());
        setBlogUrl(request.blogUrl());
    }

    public void softDelete() { // TODO: 회원탈퇴할 때 언젠가는 쓰일 것 같아서 구현
        if (Objects.isNull(this.deletedAt)) {
            this.deletedAt = LocalDateTime.now();
            return;
        }
        throw new IllegalStateException(USER_ALREADY_DELETED);
    }

    private void validateConstructorArguments(String nickname, String email) {
        if (nickname != null) {
            validateNickname(nickname);
        }

        if (email != null) {
            validateEmail(email, EMAIL_FORMAT_INVALID);
        }
    }

    private void setNickname(String nickname) {
        validateNickname(nickname);
        if (isSetPossible(this.nickname, nickname)) {
            this.nickname = nickname;
        }
    }

    private void setIntroduction(String introduction) {
        if (StringUtils.isNotBlank(introduction)) {
            validateIntroduction(introduction);
        }

        if (isSetPossible(this.introduction, introduction)) {
            this.introduction = introduction;
        }
    }

    private void setProfileImageUrl(String profileImageUrl) {
        if (StringUtils.isNotBlank(profileImageUrl)) {
            validateProfileImageUrl(profileImageUrl);
        }

        if (isSetPossible(this.profileImageUrl, profileImageUrl)) {
            this.profileImageUrl = profileImageUrl;
        }
    }

    private void setJob(String jobName) {
        Job newJob = null;
        if (StringUtils.isNotBlank(jobName)) {
            newJob = Job.get(jobName);
        }

        if (isSetPossible(this.job, newJob)) {
            this.job = newJob;
        }
    }

    private void setCareer(String careerDescription) {
        Career newCareer = null;
        if (StringUtils.isNotBlank(careerDescription)) {
            newCareer = Career.get(careerDescription);
        }

        if (isSetPossible(this.career, newCareer)) {
            this.career = newCareer;
        }
    }

    private void setGithubUrl(String githubUrl) {
        if (StringUtils.isNotBlank(githubUrl)) {
            validateGithubUrl(githubUrl);
        }

        if (isSetPossible(this.githubUrl, githubUrl)) {
            this.githubUrl = githubUrl;
        }
    }

    private void setBlogUrl(String blogUrl) {
        if (StringUtils.isNotBlank(blogUrl)) {
            validateBlogUrl(blogUrl);
        }

        if (isSetPossible(this.blogUrl, blogUrl)) {
            this.blogUrl = blogUrl;
        }
    }

}
