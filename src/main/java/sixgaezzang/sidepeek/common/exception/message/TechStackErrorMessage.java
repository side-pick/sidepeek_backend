package sixgaezzang.sidepeek.common.exception.message;

import static sixgaezzang.sidepeek.common.util.CommonConstant.MAX_TECH_STACK_COUNT;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TechStackErrorMessage {

    public static final String TECH_STACKS_IS_NULL = "기술 스택들을 입력해주세요.";
    public static final String TECH_STACKS_OVER_MAX_COUNT =
        "기술 스택은 " + MAX_TECH_STACK_COUNT + "개 미만이어야 합니다.";
}