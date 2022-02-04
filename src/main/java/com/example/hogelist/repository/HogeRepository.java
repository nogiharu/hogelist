package com.example.hogelist.repository;




import com.example.hogelist.entity.HogeTable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HogeRepository extends JpaRepository<HogeTable,Integer>{
    Page<HogeTable> findAllByOrderByIdAsc(Pageable pageable);
}