package com.coffee.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.List;

/**
 * url处理工具，统一封装，主要是用来处理url
 * @author rabit
 * @version v1.0
 * @date 2022/9/3 19:37
 */
public class UrlUtils {
    /**
     * 检查url是否在paths 中[带微服务名]
     * @param paths url的地址
     * @param url 带微服务的前缀
     * @return 是否匹配上
     */
    public static boolean isMatches(List<String> paths, String url){
        if (paths ==null || paths.isEmpty()){
            return false;
        }
       String subPath = removeServerNamePrefix(url);
        long count = paths.stream().filter(s -> PathPatternParser.defaultInstance.parse(s).matches(PathContainer.parsePath(subPath))).count();
        return count > 0;
    }

    /**
     * 去除url中的服务名
     * @param url 带微服务的前缀path
     * @return 去除微服务名后的path
     */
    public static String removeServerNamePrefix(String url){
        //获取链接中的分隔符
        char separator = PathContainer.Options.HTTP_PATH.separator();
        //去掉链接中的服务名
        return url.substring(url.indexOf(separator,1));
    }

    /**
     * 匹配两个路径
     * @param reqPath 请求路径
     * @param url 权限路径
     * @return 匹配成功true 失败false
     */
    public static boolean isMatchesNotMicoServer(String reqPath, String url){
        if (StringUtils.isEmpty(reqPath) || StringUtils.isEmpty(url)){
            return false;
        }
        return PathPatternParser.defaultInstance.parse(reqPath).matches(PathContainer.parsePath(url));
    }


}
