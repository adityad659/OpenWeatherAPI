package com.example.weatherinfo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:globalconfig.properties")
@ConfigurationProperties(prefix = "app")
public class GlobalConfiguration {

	private OpenWeather openWeather;

	public OpenWeather getOpenWeather() {
		return openWeather;
	}

	public void setOpenWeather(OpenWeather openWeather) {
		this.openWeather = openWeather;
	}

	public static class OpenWeather {

		private String appId;

		private String baseUrl;


		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getBaseUrl() {
			return baseUrl;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}
	}

}
