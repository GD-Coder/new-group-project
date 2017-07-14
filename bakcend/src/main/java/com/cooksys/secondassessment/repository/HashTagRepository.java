package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
	HashTag findByLabel(String label);
	HashTag findByLabelEquals(String label);
}
