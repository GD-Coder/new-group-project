package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.HashTagNoIdDto;
import com.cooksys.secondassessment.entity.HashTag;

@Mapper(componentModel = "spring")
public interface HashTagMapper {

	HashTagNoIdDto hashTagNoIdDto(HashTag t);
	HashTag toHashTag(HashTagNoIdDto t);
}
