package org.gdou.service.impl;

import com.baidubce.services.bos.BosClient;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.config.BaiDuBosConfig;
import org.gdou.model.po.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

/**
 * 关于百度Bos的service操作
 * @author HILL
 * @version V1.0
 * @date 2020/3/11
 **/
@Service
@Slf4j
public class BosService {

    private BosClient bosClient;
    private HashSet<String> imgSuffixSet;

    public BosService(BosClient bosClient) {
        this.bosClient = bosClient;
        this.imgSuffixSet = new HashSet<String>();
        imgSuffixSet.add("jpg");
        imgSuffixSet.add("png");
    }

    /**
     * 采用异步方法上传图片到百度BOS
     * @Author: HILL
     * @date: 2020/3/9 16:47
     *
     * @param avatar
     * @return: void
     **/
    @Async("bosUploadExecutor")
    public Result uploadAvatar(MultipartFile avatar, User user) throws IOException {
        //校验文件的后缀名是否为jpg、png
        var originalFileName = avatar.getOriginalFilename();
        var index = originalFileName.lastIndexOf(".");
        String suffixName = originalFileName.substring(index);

        if (!imgSuffixSet.contains(suffixName) ){
            return ResultGenerator.genFailResult("上传的文件必须为png、jpg文件");
        }

        //保存文件到百度的文件管理库
        var fileName = UUID.randomUUID().toString()+suffixName;
        var putObjectResponse = bosClient.putObject(ProjectConstant.AVATAR_BUCKET_NAME, fileName, avatar.getInputStream());
        log.info("插入图片{}到BOS 成功返回信息{} ",fileName,putObjectResponse.getETag());
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 构建图片的url
     * @Author: HILL
     * @date: 2020/3/10 21:26
     *
     * @param fileName 图片文件名
     * @return: java.lang.String 完成的http访问路径
     **/
    @NotNull
    private String buildAvatarUrl(String fileName) {
        //https://avatar-img.gz.bcebos.com/test-img
        String url = "https://" +
                ProjectConstant.AVATAR_BUCKET_NAME + "." +
                BaiDuBosConfig.GZ_ENDPOINT + "/" + fileName;
        return url;
    }

}
