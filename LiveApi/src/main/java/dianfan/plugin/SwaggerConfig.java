package dianfan.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * 接口插件配置 http://localhost:8080/LiveApi/plugins/swagger/index.html
 * 
 * @author Administrator
 *
 */
@EnableSwagger
public class SwaggerConfig {
	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*?");
	}

	
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("api接口一览", "interface", "API terms of service", "huangjun@dainfankeji.cn",
				"web app", "点番科技app License URL");
		return apiInfo;
	}
}
