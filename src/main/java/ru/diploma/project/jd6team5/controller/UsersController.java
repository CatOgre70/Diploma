package ru.diploma.project.jd6team5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.diploma.project.jd6team5.constants.UserRole;
import ru.diploma.project.jd6team5.dto.NewPassword;
import ru.diploma.project.jd6team5.dto.UserDto;
import ru.diploma.project.jd6team5.model.User;
import ru.diploma.project.jd6team5.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(value = "http://localhost:3000")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "getUser - Вывод данных о Пользователе",
            operationId = "getUser_1",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
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
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = userService.getUserIdByName(authentication.getName());
        UserDto instUserDto = userService.getUserDto(userService.getUserByID(id));
        return ResponseEntity.ok(instUserDto);
    }

    @Operation(
            summary = "setPassword - Ввод нового пароля Пользователя",
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
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )},
            tags = "Пользователи"
    )
    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@Parameter(description = "Данные о пароле Пользователя") @RequestBody NewPassword inpPWD,
                                               Authentication authentication
    ) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = userService.getUserIdByName(authentication.getName());
        UserDto resultEntity = userService.updatePassword(id, inpPWD);
        return ResponseEntity.ok(resultEntity);
    }

    @Operation(
            summary = "Обновление данных о Пользователе",
            operationId = "updateUser",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные записаны!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
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
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUserData(@RequestBody UserDto inpUser,
                                                  Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = userService.getUserIdByName(authentication.getName());
        User currUser = userService.getUserByID(id);
        if (currUser.getRole().equals(UserRole.USER) &&
                !currUser.getEmail().equals(inpUser.getEmail())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserDto resultEntity = userService.updateUser(inpUser);
        if (resultEntity != null) {
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Обновление Аватарки Пользователя",
            operationId = "updateUserImage",
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
    @PatchMapping(path = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@Parameter(description = "Путь к файлу") @RequestPart(name = "image") MultipartFile inpPicture,
                                                  Authentication authentication) throws IOException {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (inpPicture.getSize() > 1024 * 1024 * 10) {
            return ResponseEntity.badRequest().body("File great than 10 Mb!");
        }
        Long id = userService.getUserIdByName(authentication.getName());
        userService.updateUserAvatar(id, inpPicture);
        return ResponseEntity.ok().body("File Photo was uploaded successfully");
    }

    @Operation(
            summary = "getUserImage - Вывод аватара Пользователя",
            operationId = "getUserImage",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
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
    @GetMapping("/me/image")
    public ResponseEntity<byte[]> getUserAvatar(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = userService.getUserIdByName(authentication.getName());
        User instUser = userService.getUserByID(id);
        int contentLen = instUser.getAvatarPath() == null ? 0:instUser.getAvatarPath().length;
        HttpHeaders headersHTTP = new HttpHeaders();
        headersHTTP.setContentLength(contentLen);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headersHTTP)
                .body(instUser.getAvatarPath());
    }
}