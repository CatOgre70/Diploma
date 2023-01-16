package ru.diploma.project.jd6team5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.Advertisement;
import ru.diploma.project.jd6team5.dto.Comment;
import ru.diploma.project.jd6team5.dto.User;
import ru.diploma.project.jd6team5.model.CreateAds;
import ru.diploma.project.jd6team5.model.FullAds;
import ru.diploma.project.jd6team5.model.ResponseWrapperAds;
import ru.diploma.project.jd6team5.model.ResponseWrapperComment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/ads")
@CrossOrigin(value = "http://localhost:8624")
public class AdsController {
    private final Advertisement DEFAULT_ADS_ENTITY = new Advertisement();
    private final User DEFAULT_USER_ENTITY = new User();
    private final Comment DEFAULT_COMMENT_ENTITY = new Comment(1L, 1L, "Мой дефолтный коммент",
            LocalDateTime.now());
//    private final AdvertisementService adsService;

    @Operation(
            summary = "Вывод всех Объявлений",
            operationId = "getALLAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAds.class)
                            )
                    )
            }, tags = "Объявления"
    )
    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getALLAds() {
        ResponseWrapperAds result = new ResponseWrapperAds(1, List.of(DEFAULT_ADS_ENTITY));
        return ResponseEntity.ok(result);
    }
    @Operation(
            summary = "Вывод всех Комментариев указанного Объявления",
            operationId = "getComments",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperComment.class)
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
    public ResponseEntity<ResponseWrapperComment> getComments(@Parameter(description = "ИД номер Объявления") @PathVariable Long adsID) {
//        Advertisement resultEntity = adsService.findAdsByID(adsID);
        Advertisement resultEntity = DEFAULT_ADS_ENTITY;
        if (resultEntity != null) {
//            List<Comment> commentsList = adsService.getAllCommentsForAds(adsID);
            ResponseWrapperComment result = new ResponseWrapperComment(1, List.of(DEFAULT_COMMENT_ENTITY));
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Вывод информации по Объявлению",
            operationId = "getAds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Advertisement.class))
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
    public ResponseEntity<FullAds> getAds(@Parameter(description = "ИД номер Объявления") @PathVariable Long adsID) {
//        Advertisement resultEntity = adsService.findAdsByID(adsID);
        Advertisement resultEntity = DEFAULT_ADS_ENTITY;
        if (resultEntity != null) {
//            User adsUser = adsService.findUserAdsByID(adsID);
            FullAds result = new FullAds(DEFAULT_USER_ENTITY.getFirstName(),
                    DEFAULT_USER_ENTITY.getLastName(),
                    resultEntity.getDescription(),
                    DEFAULT_USER_ENTITY.getEmail(),
                    resultEntity.getImageListID(),
                    DEFAULT_USER_ENTITY.getPhone(),
                    resultEntity.getId(),
                    resultEntity.getPrice(),
                    resultEntity.getCurrency(),
                    resultEntity.getLabel(), resultEntity.getStatus());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Ввод данных Объявления",
            operationId = "addAds",
            /*requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CreateAds.class)
                    )
            ),*/
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Advertisement.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Advertisement.class)
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
    public ResponseEntity<Advertisement> addAds(@Parameter(description = "Первичные данные об Объявлении") @RequestBody CreateAds newAds,
                                                @Parameter(description = "Путь к файлу") @RequestBody MultipartFile inpPicture)  throws IOException {
        if (inpPicture.getSize() > 1024 * 1024 * 10) {
            return ResponseEntity.badRequest().build();
        }
//        Advertisement resultEntity = adsService.addNewAds(newAds, inpPicture);
        return ResponseEntity.ok(DEFAULT_ADS_ENTITY);
    }
    @Operation(
            summary = "Ввод Комментария в Объявлении",
            operationId = "addComments",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Comment.class)
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
    public ResponseEntity<Comment> addCommentToAds(@Parameter(description = "ИД номер Объявления") @PathVariable Long adsID,
                                                @Parameter(description = "Комментарий") @RequestBody Comment inpComment){
//        Advertisement resultEntity = adsService.findAdsByID(adsID);
        Advertisement resultEntity = DEFAULT_ADS_ENTITY;
        if (resultEntity != null) {
//            inpComment.setAdsID(adsID);
//            adsService.addCommentToAdsByID(inpComment);
            return ResponseEntity.ok(DEFAULT_COMMENT_ENTITY);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Boolean> removeAds(@Parameter(description = "ИД номер Объявления") @PathVariable Long adsID){
//        Advertisement resultEntity = adsService.findAdsByID(adsID);
        Advertisement resultEntity = DEFAULT_ADS_ENTITY;
        if (resultEntity != null) {
//            adsService.removeAds(adsID);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Advertisement> updateAds(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ads_id}/comment/{comment_id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long ads_id, @PathVariable Long comment_id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ads_id}/comment/{comment_id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long ads_id, @PathVariable Long comment_id) {
        return ResponseEntity.ok().build();
    }
    /*    @Operation(
                summary = "Изменение коммента",
                operationId = "patchComment",
                responses =

                        Дописать, как будет понятно, как этот патч сработает. Пока что как-то туманно.
        )*/

    @PatchMapping("/{ads_id}/comment/{comment_id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long ads_id, @PathVariable Long comment_id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me") //?????????????????????????????? Ничего не понял, что это. Надо посмотреть.
    public ResponseEntity<List<Advertisement>> getAdsMe() {
        return ResponseEntity.ok().build();
    }
}
