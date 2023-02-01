
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tangzhijie
 * @since 2021-04-09 21:33
 */
public abstract class AbstractContentCachingWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isFirstRequest && !(request instanceof MyContentCachingRequestWrapper)) {
            requestToUse = new MyContentCachingRequestWrapper(request);
        }

        HttpServletResponse responseToUse = response;
        if (isFirstRequest && !(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }
        try {
            filterChain.doFilter(requestToUse, responseToUse);
            if (isFirstRequest) {
                ContentCachingResponseWrapper responseWrapper =
                        WebUtils.getNativeResponse(responseToUse, ContentCachingResponseWrapper.class);
                Assert.notNull(responseWrapper, "ContentCachingResponseWrapper not found");
                responseWrapper.copyBodyToResponse();
            }
        } finally {
            afterRequest(requestToUse, responseToUse);
        }
    }
    /**
     * 在请求后做一些操作
     *
     * @param request  请求
     * @param response 响应
     */
    protected abstract void afterRequest(HttpServletRequest request, HttpServletResponse response);
}
