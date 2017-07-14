package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.ProfileDto;
import com.cooksys.secondassessment.dto.ProfileEmailOnlyDto;
import com.cooksys.secondassessment.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	
	ProfileDto tProfileDto(Profile p);
	Profile toProfile(ProfileDto p);
	
	ProfileEmailOnlyDto tEmailOnlyDto(Profile p);
	Profile toProfile(ProfileEmailOnlyDto p);
	
}
