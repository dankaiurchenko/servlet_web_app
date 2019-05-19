package com.danarossa.dto;

import com.danarossa.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithMark {
    private User student;
    private DtoMark dtoMark;
}
