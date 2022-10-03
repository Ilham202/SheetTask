package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.model.CompareModel;
@Repository
public interface CompareRepository extends JpaRepository<CompareModel, Integer> {

}
