package com.ymchen.ranniservice.api.dao;

import com.ymchen.ranniservice.api.entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface StudentESDao extends ElasticsearchRepository<Student,String> {

}
