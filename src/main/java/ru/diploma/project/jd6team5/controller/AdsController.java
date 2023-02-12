package ru.diploma.project.jd6team5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.*;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.Comment;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.service.AdsService;
import ru.diploma.project.jd6team5.service.CommentService;
import ru.diploma.project.jd6team5.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@RestController
@RequestMapping(path = "/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {
    private final AdsService adsService;
    private final CommentService commentService;
    private final UserService userService;

    public AdsController(AdsService adsService, CommentService commentService, UserService userService) {
        this.adsService = adsService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Operation(
            summary = "getALLAds - Все Объявления",
            operationId = "getALLAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            }, tags = "Объявления"
    )
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Operation(
            summary = "Все комментарии по Объявлению",
            operationId = "getAllCommentsByAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Ads.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/{adsID}/comments")
    public ResponseEntity<ResponseWrapperComments> getAllCommentsByAds(
            @Parameter(description = "ИД номер Объявлении") @PathVariable Long adsID) {
        return ResponseEntity.ok(commentService.getAllCommentsByAdsId(adsID));
    }

    @Operation(
            summary = "Вся инфа по объявлению",
            operationId = "getAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Ads.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/{adsID}")
    public ResponseEntity<FullAdsDto> getAds(@PathVariable Long adsID) {
        return ResponseEntity.ok(adsService.findFullAds(adsID));
    }

    @Operation(
            summary = "Ввод данных Объявления",
            operationId = "addAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Первичные данные об Объявлении"
            , schema = @Schema(implementation = CreateAds.class)) @RequestPart CreateAds properties,
            @Parameter(description = "Путь к файлу", allowEmptyValue = true) @RequestPart MultipartFile image,
            Authentication authentication) throws IOException {
        if (image != null && image.getSize() > 1024 * 1024 * 10) {
            return ResponseEntity.badRequest().build();
        }
        Long id = userService.getUserIdByName(authentication.getName());
        return ResponseEntity.ok(adsService.createAds(id, properties, image));
    }

    @Operation(
            summary = "addCommentToAds - Ввод Комментария в Объявлении",
            operationId = "addCommentToAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/{adsID}/comments")
    public ResponseEntity<CommentDto> addCommentToAds(@PathVariable Long adsID,
                                                      @RequestBody CommentDto inpComment,
                                                      Authentication authentication) {
        Long inAdsID = adsService.findFullAds(adsID).getPk();
        return ResponseEntity.ok(commentService.addComment(inAdsID, inpComment, authentication.getName()));
    }

    @Operation(
            summary = "Удаление Объявления",
            operationId = "removeAds",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/{adsID}")
    public ResponseEntity<?> removeAds(@PathVariable Long adsID, Authentication authentication) {
        Ads ads = adsService.findById(adsID).orElseThrow(AdsNotFoundException::new);
        User user = userService.getUserByName(authentication.getName());
        if(!Objects.equals(ads.getUserID(), user.getUserID()) && user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        adsService.deleteAds(adsID);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "updateAds - Изменение данных в Объявлении",
            operationId = "updateAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateAds.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Long id, @RequestBody CreateAds targetAds,
                                            Authentication authentication) {
        Ads ads = adsService.findById(id).orElseThrow(AdsNotFoundException::new);
        User user = userService.getUserByName(authentication.getName());
        if(!Objects.equals(ads.getUserID(), user.getUserID()) && user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(adsService.updateAds(id, targetAds));
    }

    @Operation(
            summary = "getComments - Вывод Комментариев у данного Объявления",
            operationId = "getComments",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long adsID, @PathVariable Long commentID) {
        return ResponseEntity.ok(commentService.findByIdAndAdsId(adsID, commentID));
    }

    @Operation(
            summary = "Удаление Комментариев у данного Объявления",
            operationId = "deleteComments",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable Long adsID,
                                                @PathVariable Long commentID, Authentication authentication) {
        CommentDto commentDto = commentService.findByIdAndAdsId(adsID, commentID);
        User user = userService.getUserByName(authentication.getName());
        if(!Objects.equals(commentDto.getAuthor(), user.getUserID()) && user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        commentService.deleteComment(adsID, commentID);
        return ResponseEntity.ok().body("Комментарий удалён");
    }

    @Operation(
            summary = "Обновление Комментария у данного Объявления",
            operationId = "updateComments",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long adsID,
                                                    @PathVariable Long commentID,
                                                    @RequestBody CommentDto dto,
                                                    Authentication authentication) {
        CommentDto commentDto = commentService.findByIdAndAdsId(adsID, commentID);
        User user = userService.getUserByName(authentication.getName());
        if(!Objects.equals(commentDto.getAuthor(), user.getUserID()) && user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(commentService.updateComment(adsID, commentID, dto));
    }

    @Operation(
            summary = "getALLAds - Все Объявления данного Пользователя",
            operationId = "getAdsMeUsingGET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperAds.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            }, tags = "Объявления"
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(Authentication authentication) {
        Long id = userService.getUserIdByName(authentication.getName());
        ResponseWrapperAds response = adsService.getAllAdsByUserId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "get Ads image - вывод картинки объявления",
            operationId = "getAdsImageUsingGET",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.IMAGE_PNG_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = byte[].class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            }, tags = "Объявления"
    )
    @GetMapping(value = "/{adsId}/getimage", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long adsId) throws IOException {
        Ads ads = adsService.findById(adsId).orElseThrow(AdsNotFoundException::new);
        if (ads.getImage() == null) {
            return ResponseEntity.ok(null);
        } else {
            Path imagePath = Path.of(ads.getImage());
            return ResponseEntity.ok(Files.readAllBytes(imagePath));
        }
    }

}
