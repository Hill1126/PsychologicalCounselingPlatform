package org.gdou.model.po.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentScheduleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppointmentScheduleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIsNull() {
            addCriterion("teacher_id is null");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIsNotNull() {
            addCriterion("teacher_id is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherIdEqualTo(Integer value) {
            addCriterion("teacher_id =", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotEqualTo(Integer value) {
            addCriterion("teacher_id <>", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdGreaterThan(Integer value) {
            addCriterion("teacher_id >", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("teacher_id >=", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLessThan(Integer value) {
            addCriterion("teacher_id <", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdLessThanOrEqualTo(Integer value) {
            addCriterion("teacher_id <=", value, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdIn(List<Integer> values) {
            addCriterion("teacher_id in", values, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotIn(List<Integer> values) {
            addCriterion("teacher_id not in", values, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdBetween(Integer value1, Integer value2) {
            addCriterion("teacher_id between", value1, value2, "teacherId");
            return (Criteria) this;
        }

        public Criteria andTeacherIdNotBetween(Integer value1, Integer value2) {
            addCriterion("teacher_id not between", value1, value2, "teacherId");
            return (Criteria) this;
        }

        public Criteria andNineAmIsNull() {
            addCriterion("nine_am is null");
            return (Criteria) this;
        }

        public Criteria andNineAmIsNotNull() {
            addCriterion("nine_am is not null");
            return (Criteria) this;
        }

        public Criteria andNineAmEqualTo(Integer value) {
            addCriterion("nine_am =", value, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmNotEqualTo(Integer value) {
            addCriterion("nine_am <>", value, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmGreaterThan(Integer value) {
            addCriterion("nine_am >", value, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmGreaterThanOrEqualTo(Integer value) {
            addCriterion("nine_am >=", value, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmLessThan(Integer value) {
            addCriterion("nine_am <", value, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmLessThanOrEqualTo(Integer value) {
            addCriterion("nine_am <=", value, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmIn(List<Integer> values) {
            addCriterion("nine_am in", values, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmNotIn(List<Integer> values) {
            addCriterion("nine_am not in", values, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmBetween(Integer value1, Integer value2) {
            addCriterion("nine_am between", value1, value2, "nineAm");
            return (Criteria) this;
        }

        public Criteria andNineAmNotBetween(Integer value1, Integer value2) {
            addCriterion("nine_am not between", value1, value2, "nineAm");
            return (Criteria) this;
        }

        public Criteria andTenAmIsNull() {
            addCriterion("ten_am is null");
            return (Criteria) this;
        }

        public Criteria andTenAmIsNotNull() {
            addCriterion("ten_am is not null");
            return (Criteria) this;
        }

        public Criteria andTenAmEqualTo(Integer value) {
            addCriterion("ten_am =", value, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmNotEqualTo(Integer value) {
            addCriterion("ten_am <>", value, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmGreaterThan(Integer value) {
            addCriterion("ten_am >", value, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmGreaterThanOrEqualTo(Integer value) {
            addCriterion("ten_am >=", value, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmLessThan(Integer value) {
            addCriterion("ten_am <", value, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmLessThanOrEqualTo(Integer value) {
            addCriterion("ten_am <=", value, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmIn(List<Integer> values) {
            addCriterion("ten_am in", values, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmNotIn(List<Integer> values) {
            addCriterion("ten_am not in", values, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmBetween(Integer value1, Integer value2) {
            addCriterion("ten_am between", value1, value2, "tenAm");
            return (Criteria) this;
        }

        public Criteria andTenAmNotBetween(Integer value1, Integer value2) {
            addCriterion("ten_am not between", value1, value2, "tenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmIsNull() {
            addCriterion("eleven_am is null");
            return (Criteria) this;
        }

        public Criteria andElevenAmIsNotNull() {
            addCriterion("eleven_am is not null");
            return (Criteria) this;
        }

        public Criteria andElevenAmEqualTo(Integer value) {
            addCriterion("eleven_am =", value, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmNotEqualTo(Integer value) {
            addCriterion("eleven_am <>", value, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmGreaterThan(Integer value) {
            addCriterion("eleven_am >", value, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmGreaterThanOrEqualTo(Integer value) {
            addCriterion("eleven_am >=", value, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmLessThan(Integer value) {
            addCriterion("eleven_am <", value, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmLessThanOrEqualTo(Integer value) {
            addCriterion("eleven_am <=", value, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmIn(List<Integer> values) {
            addCriterion("eleven_am in", values, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmNotIn(List<Integer> values) {
            addCriterion("eleven_am not in", values, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmBetween(Integer value1, Integer value2) {
            addCriterion("eleven_am between", value1, value2, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andElevenAmNotBetween(Integer value1, Integer value2) {
            addCriterion("eleven_am not between", value1, value2, "elevenAm");
            return (Criteria) this;
        }

        public Criteria andTwoPmIsNull() {
            addCriterion("two_pm is null");
            return (Criteria) this;
        }

        public Criteria andTwoPmIsNotNull() {
            addCriterion("two_pm is not null");
            return (Criteria) this;
        }

        public Criteria andTwoPmEqualTo(Integer value) {
            addCriterion("two_pm =", value, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmNotEqualTo(Integer value) {
            addCriterion("two_pm <>", value, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmGreaterThan(Integer value) {
            addCriterion("two_pm >", value, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmGreaterThanOrEqualTo(Integer value) {
            addCriterion("two_pm >=", value, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmLessThan(Integer value) {
            addCriterion("two_pm <", value, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmLessThanOrEqualTo(Integer value) {
            addCriterion("two_pm <=", value, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmIn(List<Integer> values) {
            addCriterion("two_pm in", values, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmNotIn(List<Integer> values) {
            addCriterion("two_pm not in", values, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmBetween(Integer value1, Integer value2) {
            addCriterion("two_pm between", value1, value2, "twoPm");
            return (Criteria) this;
        }

        public Criteria andTwoPmNotBetween(Integer value1, Integer value2) {
            addCriterion("two_pm not between", value1, value2, "twoPm");
            return (Criteria) this;
        }

        public Criteria andThreePmIsNull() {
            addCriterion("three_pm is null");
            return (Criteria) this;
        }

        public Criteria andThreePmIsNotNull() {
            addCriterion("three_pm is not null");
            return (Criteria) this;
        }

        public Criteria andThreePmEqualTo(Integer value) {
            addCriterion("three_pm =", value, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmNotEqualTo(Integer value) {
            addCriterion("three_pm <>", value, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmGreaterThan(Integer value) {
            addCriterion("three_pm >", value, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmGreaterThanOrEqualTo(Integer value) {
            addCriterion("three_pm >=", value, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmLessThan(Integer value) {
            addCriterion("three_pm <", value, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmLessThanOrEqualTo(Integer value) {
            addCriterion("three_pm <=", value, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmIn(List<Integer> values) {
            addCriterion("three_pm in", values, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmNotIn(List<Integer> values) {
            addCriterion("three_pm not in", values, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmBetween(Integer value1, Integer value2) {
            addCriterion("three_pm between", value1, value2, "threePm");
            return (Criteria) this;
        }

        public Criteria andThreePmNotBetween(Integer value1, Integer value2) {
            addCriterion("three_pm not between", value1, value2, "threePm");
            return (Criteria) this;
        }

        public Criteria andFourPmIsNull() {
            addCriterion("four_pm is null");
            return (Criteria) this;
        }

        public Criteria andFourPmIsNotNull() {
            addCriterion("four_pm is not null");
            return (Criteria) this;
        }

        public Criteria andFourPmEqualTo(Integer value) {
            addCriterion("four_pm =", value, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmNotEqualTo(Integer value) {
            addCriterion("four_pm <>", value, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmGreaterThan(Integer value) {
            addCriterion("four_pm >", value, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmGreaterThanOrEqualTo(Integer value) {
            addCriterion("four_pm >=", value, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmLessThan(Integer value) {
            addCriterion("four_pm <", value, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmLessThanOrEqualTo(Integer value) {
            addCriterion("four_pm <=", value, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmIn(List<Integer> values) {
            addCriterion("four_pm in", values, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmNotIn(List<Integer> values) {
            addCriterion("four_pm not in", values, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmBetween(Integer value1, Integer value2) {
            addCriterion("four_pm between", value1, value2, "fourPm");
            return (Criteria) this;
        }

        public Criteria andFourPmNotBetween(Integer value1, Integer value2) {
            addCriterion("four_pm not between", value1, value2, "fourPm");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateIsNull() {
            addCriterion("appointment_date is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateIsNotNull() {
            addCriterion("appointment_date is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateEqualTo(LocalDate value) {
            addCriterion("appointment_date =", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotEqualTo(LocalDate value) {
            addCriterion("appointment_date <>", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateGreaterThan(LocalDate value) {
            addCriterion("appointment_date >", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateGreaterThanOrEqualTo(LocalDate value) {
            addCriterion("appointment_date >=", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateLessThan(LocalDate value) {
            addCriterion("appointment_date <", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateLessThanOrEqualTo(LocalDate value) {
            addCriterion("appointment_date <=", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateIn(List<LocalDate> values) {
            addCriterion("appointment_date in", values, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotIn(List<LocalDate> values) {
            addCriterion("appointment_date not in", values, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateBetween(LocalDate value1, LocalDate value2) {
            addCriterion("appointment_date between", value1, value2, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotBetween(LocalDate value1, LocalDate value2) {
            addCriterion("appointment_date not between", value1, value2, "appointmentDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}