@Configuration
@Primary
public class WebConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}