package sixgaezzang.sidepeek.skill.util.validation;

import static sixgaezzang.sidepeek.common.util.ValidationUtils.validateMaxLength;
import static sixgaezzang.sidepeek.common.util.ValidationUtils.validateNotBlank;
import static sixgaezzang.sidepeek.projects.util.ProjectConstant.MAX_CATEGORY_LENGTH;
import static sixgaezzang.sidepeek.skill.util.validation.SkillErrorMessage.CATEGORY_IS_NULL;
import static sixgaezzang.sidepeek.skill.util.validation.SkillErrorMessage.CATEGORY_OVER_MAX_LENGTH;
import static sixgaezzang.sidepeek.skill.util.validation.SkillErrorMessage.SKILL_IS_NULL;

import io.jsonwebtoken.lang.Assert;
import sixgaezzang.sidepeek.skill.domain.Skill;

public class SkillValidator {

    public static void validateSkill(Skill skill) {
        Assert.notNull(skill, SKILL_IS_NULL);
    }

    public static void validateCategory(String category) {
        validateNotBlank(category, CATEGORY_IS_NULL);
        validateMaxLength(category, MAX_CATEGORY_LENGTH,
            CATEGORY_OVER_MAX_LENGTH);
    }

}