package ru.diploma.project.jd6team5.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import liquibase.pro.packaged.A;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diploma.project.jd6team5.dto.Ads;
import ru.diploma.project.jd6team5.dto.Comment;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequestMapping(path = "/ads")
@CrossOrigin(value = "http://localhost:8624")
public class AdsController {

    //private final Comment comment;

    /*    @Operation(
                summary = "Изменение объявления",
                operationId = "patchAds",
                responses =

                        Дописать, как будет понятно, как этот патч сработает. Пока что как-то туманно.
        )*/
    @PatchMapping("/{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable Long id) {
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
    public ResponseEntity<List<Ads>> getAdsMe() {
        return ResponseEntity.ok().build();
    }

}
