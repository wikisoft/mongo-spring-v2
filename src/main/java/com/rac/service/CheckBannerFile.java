package com.rac.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class CheckBannerFile implements HealthIndicator {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public Health health() {
	if (!checkFile()) {
	    return Health.down().withDetail("Error Code ", -1).build();
	}
	return Health.up().build();
    }

    private boolean checkFile() {

	try {
	    Resource resource = resourceLoader
		    .getResource("classpath:banner.txt");
	    File banner = resource.getFile();

	    if (banner.exists()) {
		return true;
	    }
	    return false;
	} catch (IOException e) {
	    return false;
	}

    }

}
