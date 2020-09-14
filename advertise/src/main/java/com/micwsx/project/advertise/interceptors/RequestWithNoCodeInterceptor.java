package com.micwsx.project.advertise.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器也可以通过实现WebMvcConfigurer接口
public class RequestWithNoCodeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
//        System.out.println("interceptor: "+request.getRequestURI());
//         //判断session中是否有值
//        HttpSession session = request.getSession();
//        Optional<Object> optionalId = Optional.ofNullable(session.getAttribute("id"));
//        // 已经认证
//        if (optionalId.isPresent())
//            return true;
//
//        String relativePath = request.getRequestURI();
//        String code = request.getParameter("code");
//        String state=request.getParameter("state");
//        boolean isGetOpenIdRequest = relativePath.equals("/menu/getOpenId");
//        if (StringUtils.isEmpty(code) && (!isGetOpenIdRequest)) {
//            // 未认证, 跳转到微信认证页面
//            String encodedRelativePath = URLEncoder.encode(relativePath, "utf-8");//记录用户请求相对路径
//            String wechatCallBackUrl = request.getScheme() + "://" + request.getRemoteHost() + "/menu/getOpenId";
//            String authorizeUrl = WechatContext.getInstance().authorizedUrl(wechatCallBackUrl, encodedRelativePath);
//            response.sendRedirect(authorizeUrl);
//        } else if (!StringUtils.isEmpty(code) && (!isGetOpenIdRequest)) {
//            // code不为空且不是/menu/getOpenId则不响应请求，跳转到二维码关注页面
//            request.getRequestDispatcher("/menu/qrcode").forward(request, response);
//        } else if (!StringUtils.isEmpty(code) && isGetOpenIdRequest&&!StringUtils.isEmpty(state)) {
//            return true;
//        } else if (StringUtils.isEmpty(code) && isGetOpenIdRequest) {
//            // code为空且是/menu/getOpenId则不响应请求，跳转到二维码关注页面
//            request.getRequestDispatcher("/menu/qrcode").forward(request, response);
//        }
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
