package com.cg.model.dto.category;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String title;

    public CategoryDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

}
