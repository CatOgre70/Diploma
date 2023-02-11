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
import ru.diploma.project.jd6team5.service.AdsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/image")
@CrossOrigin(value = "http://localhost:3000")
public class ImagesAdsController {
    private final AdsService adsService;

    public ImagesAdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @Operation(
            summary = "Обновление изоражений Объявления",
            operationId = "updateImage",
            /*requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),*/
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные записаны!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = String.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Изображения"
    )
    @PatchMapping(path = "/{adsID}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateAdsImages(@Parameter(description = "ИД номер Объявлении")
                                                  @PathVariable Long adsID,
                                                  @Parameter(description = "Путь к файлу")
                                                  @RequestPart MultipartFile inpPicture) throws IOException {
        return ResponseEntity.ok(adsService.updateAndGetImage(adsID, inpPicture));
    }
}
