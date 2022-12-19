
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import com.sinotrans.gd.sscsi.auth.common.vo.ApiUserInfo;
import com.sinotrans.gd.sscsi.common.service.redis.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String url = requestTemplate.url();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                Object apiUserInfo = attributes.getAttribute(Constants.API_USER_INFO_KEY, RequestAttributes.SCOPE_REQUEST);
                if (apiUserInfo != null) {
                    ApiUserInfo userInfo = (ApiUserInfo) apiUserInfo;
                    String value = objectMapper.writeValueAsString(userInfo);
                    String headerUserInfo = BaseEncoding.base64().encode(value.getBytes(StandardCharsets.UTF_8));
                    requestTemplate.header(Constants.API_USER_INFO_KEY, headerUserInfo);
                    log.debug("传递用户信息到远程调用url[{}]成功!userCode[{}]", url, userInfo.getUserCode());
                }
            }
        } catch (Throwable throwable) {
            log.error(String.format("传递用户信息到远程调用url[%s]失败!", url), throwable);
        }
    }
}
