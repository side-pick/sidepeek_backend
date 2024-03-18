package sixgaezzang.sidepeek.common.doc;

import static sixgaezzang.sidepeek.common.doc.description.ResponseCodeDescription.BAD_REQUEST_400_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.ResponseCodeDescription.NOT_FOUND_404_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.ResponseCodeDescription.OK_200_DESCRIPTION;
import static sixgaezzang.sidepeek.common.doc.description.ResponseCodeDescription.UNAUTHORIZED_401_DESCRIPTION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sixgaezzang.sidepeek.auth.dto.request.LoginRequest;
import sixgaezzang.sidepeek.auth.dto.request.ReissueTokenRequest;
import sixgaezzang.sidepeek.auth.dto.response.LoginResponse;
import sixgaezzang.sidepeek.common.exception.ErrorResponse;
import sixgaezzang.sidepeek.users.dto.response.UserSummary;

@Tag(name = "Auth", description = "회원 인증 API")
public interface AuthControllerDoc {

    @Operation(summary = "이메일/비밀번호 로그인")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION, useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_400_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_404_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<LoginResponse> login(LoginRequest request);

    @Operation(summary = "Access Token 검증", description = "Access Token을 통해 현재 로그인 된 사용자의 정보 요청")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION, useReturnTypeSchema = true),
        @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_404_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<UserSummary> validateToken(@Parameter(hidden = true) Long loginId);

    @Operation(summary = "Access / Refresh Token 재발급", description = "Refresh Token을 통해 토큰 재발급")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = OK_200_DESCRIPTION, useReturnTypeSchema = true),
        @ApiResponse(responseCode = "401", description = UNAUTHORIZED_401_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_404_DESCRIPTION, content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<LoginResponse> reissue(ReissueTokenRequest request);
}
