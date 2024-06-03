package com.kh.pheonix.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@ConfigurationProperties(prefix = "custom.file")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AttachProperties {

	private String path;
}
