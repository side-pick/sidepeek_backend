package sixgaezzang.sidepeek.projects.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import sixgaezzang.sidepeek.projects.domain.Project;

public record ProjectSaveRequest(
    String name,
    String subName,
    String overview,
    String thumbnailUrl,
    String githubUrl,
    String deployUrl,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Long ownerId,
    String description,
    String troubleShooting,

    List<OverviewImageSaveRequest> overviewImageUrl,
    List<ProjectSkillSaveRequest> techStacks,
    List<MemberSaveRequest> members
) {
    public Project toEntity() {
        return Project.builder()
            .name(this.name)
            .subName(this.subName)
            .overview(this.overview)
            .thumbnailUrl(this.thumbnailUrl)
            .githubUrl(this.githubUrl)
            .deployUrl(this.deployUrl)
            .startDate(this.startDate)
            .endDate(this.endDate)
            .ownerId(this.ownerId)
            .description(this.description)
            .troubleshooting(this.troubleShooting)
            .build();
    }
}
