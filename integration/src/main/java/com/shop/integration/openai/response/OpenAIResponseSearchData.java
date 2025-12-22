package com.shop.integration.openai.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenAIResponseSearchData(
	@JsonProperty("file_id")
	String fileId,
	String filename,
	Double score,
	OpenAIResponseAttribute attribute,
	List<OpenAIResponseContent> contents
) {
}
