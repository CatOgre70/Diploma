package ru.diploma.project.jd6team5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.dto.User;
import ru.diploma.project.jd6team5.model.NewPassword;
import ru.diploma.project.jd6team5.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {

    private final User DEFAULT_USER_ENTITY = new User();
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Вывод данных о Пользователе",
            operationId = "getUser_1",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
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
            }, tags = "Пользователи"
    )
    @GetMapping("/{userID}")
    public ResponseEntity<User> getUser(@Parameter(description = "ИД номер Пользователя") @PathVariable Long userID) {
        return ResponseEntity.ok(userService.getUserByID(userID));
    }

    @Operation(
            summary = "Ввод нового пароля Пользователя",
            operationId = "setPassword",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewPassword.class),
                            examples = {@ExampleObject(name = "Передаваемое значение в JSON формате",
                                    value = """
                                            {"currentPassword": "OLDpassword",
                                            "newPassword": "NEWpassword"
                                            }"""
                            )}
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )},
            tags = "Пользователи"
    )
    @PostMapping("/{userID}/set_password")
    public ResponseEntity<User> setPassword(@Parameter(description = "Данные о пароле Пользователя") @RequestBody NewPassword inpPWD,
                                            @Parameter(description = "ИД номер Пользователя") @PathVariable Long userID) {
        User resultEntity = userService.updatePassword(userID, inpPWD);
        return ResponseEntity.ok(resultEntity);
    }

    @Operation(
            summary = "Обновление данных о Пользователе",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные записаны!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "203",
                            description = "No content",
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
            tags = "Пользователи"
    )
    @PatchMapping
    public ResponseEntity<User> updateUserData(@RequestBody User inpUser) {
        User resultEntity = userService.updateUser(inpUser);
        if (resultEntity != null) {
            return ResponseEntity.ok(DEFAULT_USER_ENTITY);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Обновление Аватарки Пользователя",
            /*requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.IMAGE_JPEG_VALUE,
                            schema = @Schema(implementation = User.class),
                            extensions = {@Extension(name = "*.jpeg", properties = {}),
                                    @Extension(name = "*.jpg", properties = {})
                            }
                    )
            ),*/
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Пользователи"
    )
    @PatchMapping(path = "{userID}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@Parameter(description = "ИД номер Пользователя") @PathVariable Long userID,
                                                @Parameter(description = "Путь к файлу") @RequestPart MultipartFile inpPicture) throws IOException {
        if (inpPicture.getSize() > 1024 * 1024 * 10) {
            return ResponseEntity.badRequest().body("File great than 10 Mb!");
        }
        userService.updateUserAvatar(userID, inpPicture);
        return ResponseEntity.ok().body("File Photo was uploaded successfully");
    }
}
