package com.pg.screen.mapper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@JsonInclude(value = JsonInclude.Include.NON_NULL)

	private Long id;

}
