package ru.diploma.project.jd6team5.dto;

import lombok.*;
import ru.diploma.project.jd6team5.model.Ads;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseWrapperAds {
    private long count;
    private List<Ads> results;
}
