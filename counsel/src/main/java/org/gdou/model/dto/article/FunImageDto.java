package org.gdou.model.dto.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/12
 **/
@Getter
@Setter
public class FunImageDto {
    @NotBlank
    String title;
    @NotBlank
    String imageAbstract;
    @NotNull
    @JsonIgnore
    MultipartFile img;
}
