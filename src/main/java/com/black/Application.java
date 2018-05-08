package com.black;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.black.collect.schedule.FetchScheduler;
import com.black.collect.schedule.Scheduler;
import com.black.collect.schedule.VerifyScheduler;
import com.black.web.base.utils.CommonUtil;

@EnableTransactionManagement 
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})
public class Application {
	
	private static final List<Scheduler> schedules = Arrays.asList(
            new FetchScheduler(30, TimeUnit.MINUTES),
            new VerifyScheduler(10, TimeUnit.MINUTES)
    );
	
	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(Application.class, args); 
		CommonUtil.setApplicationContext(app);
		
		/*for(Scheduler schedule : schedules)
            schedule.schedule();*/
	}
	private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
