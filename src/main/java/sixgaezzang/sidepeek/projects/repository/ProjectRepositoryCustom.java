package sixgaezzang.sidepeek.projects.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sixgaezzang.sidepeek.projects.dto.response.ProjectBannerResponse;
import sixgaezzang.sidepeek.projects.dto.response.ProjectListResponse;
import sixgaezzang.sidepeek.users.domain.User;

public interface ProjectRepositoryCustom {

    List<ProjectListResponse> findAllBySortAndStatus(List<Long> likedProjectIds, String sort,
        boolean isReleased);

    List<ProjectBannerResponse> findAllPopularOfPeriod(LocalDate startDate, LocalDate endDate, int count);

    Page<ProjectListResponse> findAllByUserJoined(List<Long> likedProjectIds, User user,
                                                  Pageable pageable);

    Page<ProjectListResponse> findAllByUserLiked(List<Long> likedProjectIds, User user,
                                                 Pageable pageable);

    Page<ProjectListResponse> findAllByUserCommented(List<Long> likedProjectIds, User user,
                                                     Pageable pageable);
}
