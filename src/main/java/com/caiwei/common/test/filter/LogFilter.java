package com.caiwei.common.test.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * TODO
 *
 * @auther caiwei
 * @date 2020-04-21
 */

@Slf4j
public class LogFilter extends OncePerRequestFilter {

    private static final String HTTP_METHOD_GET = "get";

    private static final String HTTP_METHOD_POST = "post";

    /**
     * 打印入参日志
     * @param request 请求对象
     * @param response 响应对象
     * @param filterChain 过滤器调用链
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull  HttpServletResponse response, @NonNull  FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        String contentString = getRequestPostString(request);
        if (HTTP_METHOD_GET.equalsIgnoreCase(method)) {
            log.info("method[{}],uri[{}],queryParam[{}]",method,uri,queryString);
        }
        if (HTTP_METHOD_POST.equalsIgnoreCase(method)) {
            log.info("method[{}],uri[{}],requestBody[{}]",method,uri,contentString);
        }
        filterChain.doFilter(request,response);
        //从response中很难获取响应数据，所以最好用拦截器做日志处理
    }

    /**
     * 获取 post 请求内容
     *
     * @param request 请求体
     * @return 请求转换后的json字符串
     * @throws IOException io异常
     */
    private static String getRequestPostString(HttpServletRequest request) throws IOException {

        String postString = "";

        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            byte[] buffer = new byte[contentLength];
            for (int i = 0; i < contentLength;) {
                //原来流只能读一次、 读了就没有了、为了后面的代码还能够取得流、 我们应该还需要将其写出去才行，、
                //这样只读不写，会导致之后的请求体为空
                int readLen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readLen == -1) {
                    break;
                }
                i += readLen;
            }
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = StandardCharsets.UTF_8.name();
            }
            postString = new String(buffer, charEncoding);
        }

        return postString;
    }

}
