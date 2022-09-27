package com.ymchen.ranniservice.api.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "student", createIndex = true)
public class Student {

    @Id
    @Field(type = FieldType.Auto)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Double)
    private Double weight;

    @Field(type = FieldType.Integer)
    private Integer sex;

    @Field(type = FieldType.Text)
    private String address;

//    @Field(type = FieldType.Date,format = DateFormat.basic_date)
//    private Date birthdate;
}
