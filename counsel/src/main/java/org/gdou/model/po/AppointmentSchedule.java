package org.gdou.model.po;

import java.time.LocalDate;

public class AppointmentSchedule {
    private Integer id;

    private Integer teacherId;

    private Integer nineAm;

    private Integer tenAm;

    private Integer elevenAm;

    private Integer twoPm;

    private Integer threePm;

    private Integer fourPm;

    private LocalDate appointmentDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getNineAm() {
        return nineAm;
    }

    public void setNineAm(Integer nineAm) {
        this.nineAm = nineAm;
    }

    public Integer getTenAm() {
        return tenAm;
    }

    public void setTenAm(Integer tenAm) {
        this.tenAm = tenAm;
    }

    public Integer getElevenAm() {
        return elevenAm;
    }

    public void setElevenAm(Integer elevenAm) {
        this.elevenAm = elevenAm;
    }

    public Integer getTwoPm() {
        return twoPm;
    }

    public void setTwoPm(Integer twoPm) {
        this.twoPm = twoPm;
    }

    public Integer getThreePm() {
        return threePm;
    }

    public void setThreePm(Integer threePm) {
        this.threePm = threePm;
    }

    public Integer getFourPm() {
        return fourPm;
    }

    public void setFourPm(Integer fourPm) {
        this.fourPm = fourPm;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}