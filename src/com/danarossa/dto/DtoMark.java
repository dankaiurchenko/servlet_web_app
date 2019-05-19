package com.danarossa.dto;

import com.danarossa.entities.StudentMark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoMark {
    private Integer id;
    private Integer studentId;
    private Integer realizedCourseId;
    private Double mark;

    public DtoMark(StudentMark studentMark) {
        this.id = studentMark.getId();
        this.studentId = studentMark.getStudent().getId();
        this.realizedCourseId = studentMark.getRealizedCourseId();
        this.mark = studentMark.getMark();
    }
}
