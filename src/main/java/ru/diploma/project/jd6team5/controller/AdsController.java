package ru.diploma.project.jd6team5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.*;
import ru.diploma.project.jd6team5.exception.AdsNotFoundException;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.Comment;
import ru.diploma.project.jd6team5.service.AdsService;
import ru.diploma.project.jd6team5.service.CommentService;
import ru.diploma.project.jd6team5.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping(path = "/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
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
    public ResponseEntity<ResponseWrapperAds> getAllAds(Authentication authentication) {
        if(authentication != null) {
            logger.info(authentication.getName());
        } else {
            logger.info("anonymous user");
        }
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Первичные данные об Объявлении"
                                                     , schema = @Schema(implementation = CreateAds.class)
                                             ) @RequestPart CreateAds properties,
                                         @Parameter(description = "Путь к файлу"
                                                 , allowEmptyValue = true
                                         ) @RequestPart MultipartFile image
    ) throws IOException {
        if (image != null && image.getSize() > 1024 * 1024 * 10) {
            return ResponseEntity.badRequest().build();
        }

        // Вот здесь надо разобраться как взять из фронта userId!!!

        return ResponseEntity.ok(adsService.createAds(1L, properties, image));
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
    @PostMapping(path = "/{adsID}/comments")
    public ResponseEntity<CommentDto> addCommentToAds(@PathVariable Long adsID, @RequestBody CommentDto inpComment){
        Long inAdsID = adsService.findFullAds(adsID).getPk();
        return ResponseEntity.ok(commentService.addComment(inAdsID, inpComment));
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
    @DeleteMapping(path = "/{adsID}")
    public ResponseEntity<?> removeAds(@PathVariable Long adsID){
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
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Long id, @RequestBody CreateAds targetAds) {
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
    @DeleteMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable Long adsID,
                                           @PathVariable Long commentID) {
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
    @PatchMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long adsID,
                                                    @PathVariable Long commentID,
                                                    @RequestBody CommentDto dto) {
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
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        logger.info(authentication.getName());
        Long id = userService.getUserIdByName(authentication.getName());
        ResponseWrapperAds response = adsService.getAllAdsByUserId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{adsId}/getimage", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(Authentication authentication, @PathVariable Long adsId) throws IOException {
        Ads ads = adsService.findById(adsId).orElseThrow(AdsNotFoundException::new);
        Path imagePath = Path.of(ads.getImage());
        return Files.readAllBytes(imagePath);
    }


}
