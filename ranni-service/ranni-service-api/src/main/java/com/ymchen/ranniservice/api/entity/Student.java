package com.ymchen.ranniservice.api.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Document(indexName = "student", createIndex = true)
public class Student {

    @Id
    @Field(type = FieldType.Auto)
    private String id;

    @Field(type = FieldType.Text)
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Min(value = 10)
    @Field(type = FieldType.Double)
    private Double weight;

    @Field(type = FieldType.Integer)
    private Integer sex;

    @NotBlank
    @Field(type = FieldType.Text)
    private String address;

//    @Field(type = FieldType.Date,format = DateFormat.basic_date)
//    private Date birthdate;
}
