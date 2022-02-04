package com.example.hogelist.dao;


import com.example.hogelist.entity.HogeTable;
import com.example.hogelist.form.HogeQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HogeDao {
    Page<HogeTable> findByCriteria(Pageable pageable, HogeQuery hogeQuery);
}