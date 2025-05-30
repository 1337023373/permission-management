package com.hengheng.controller;

import com.google.code.kaptcha.Producer;
import com.hengheng.common.annotation.rest.AnonymousGetMapping;
import com.hengheng.common.utils.AjaxResult;
import com.hengheng.common.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author lkj
 * @Date 2025/5/22 15:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/captcha")
@Api(tags = "验证码接口")
public class CaptchaController {
    @Resource
    private Producer captchaProducer;
    @Resource
    private RedisCache redisCache;

    @AnonymousGetMapping("/image")
    @ApiOperation("生成验证码")
    public AjaxResult getCaptchaImage() throws IOException {
        String captchaText = captchaProducer.createText();
        String uuid = UUID.randomUUID().toString();

    //    把验证码存入redis中，设置有效期5分钟
        redisCache.setCacheObject("captcha:" + uuid, captchaText,5, TimeUnit.MINUTES);
        System.out.println("Redis 查询 key：" + "captcha:" + uuid);
        System.out.println("Redis 查询值：" + redisCache.getCacheObject("captcha:" + uuid));
    //    生成图片
        BufferedImage image = captchaProducer.createImage(captchaText);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        String base64Img = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("uuid", uuid);
        resultMap.put("image", base64Img);
        return AjaxResult.success(resultMap);
    }
}
