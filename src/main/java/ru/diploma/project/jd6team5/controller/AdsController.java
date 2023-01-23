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
import ru.diploma.project.jd6team5.dto.*;
import ru.diploma.project.jd6team5.model.Ads;
import ru.diploma.project.jd6team5.model.Comment;
import ru.diploma.project.jd6team5.service.AdsService;
import ru.diploma.project.jd6team5.service.CommentService;


import java.io.IOException;

@RestController
@RequestMapping(path = "/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {
    private final AdsService adsService;
    private final CommentService commentService;

    public AdsController(AdsService adsService, CommentService commentService) {
        this.adsService = adsService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @GetMapping("/{adsID}/comments")
    public ResponseEntity<ResponseWrapperComments> getAllCommentsByAds(@PathVariable Long adsID) {
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



    @Operation(            summary = "Ввод данных Объявления",
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
                                    schema = @Schema(implementation = Ads.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Первичные данные об Объявлении") @RequestPart CreateAds newAds,
                                             @Parameter(description = "Путь к файлу") @RequestPart MultipartFile inpPicture)  throws IOException {
        if (inpPicture.getSize() > 1024 * 1024 * 10) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adsService.createAds(newAds));
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
    public ResponseEntity<CommentDto> addCommentToAds(@RequestBody CommentDto inpComment){
        return ResponseEntity.ok(commentService.addComment(inpComment));
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
            summary = "Изменение данных в Объявлении",
            operationId = "addComments",
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
    public ResponseEntity<AdsDto> updateAds(@PathVariable long id, @RequestBody CreateAds targetAds) {
        return ResponseEntity.ok(adsService.updateAds(id, targetAds));
    }

    @Operation(
            summary = "Вывод Комментариев у данного Объявления",
            operationId = "getComments",
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
                                    schema = @Schema(implementation = Ads.class)
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
    public ResponseEntity<CommentDto> getComment(@PathVariable Long commentID) {
        return ResponseEntity.ok(commentService.findById(commentID));
    }

    @Operation(
            summary = "Удаление Комментариев у данного Объявления",
            operationId = "deleteComments",
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
    @DeleteMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentID) {
        commentService.deleteComment(commentID);
        return ResponseEntity.ok().build();
    }
    @Operation(
            summary = "Обновление Комментария у данного Объявления",
            operationId = "updateComments",
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
    @PatchMapping("/{adsID}/comment/{commentID}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentID, @RequestBody CommentDto dto) {
        return ResponseEntity.ok(commentService.updateComment(dto));
    }

    /*@GetMapping("/me")   Тут надо достать id'шник свой из сессии. Я пока не умею))
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {
        return ResponseEntity.ok();
    }*/
}
