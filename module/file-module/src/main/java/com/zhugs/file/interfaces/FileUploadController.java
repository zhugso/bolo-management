package com.zhugs.file.interfaces;

import com.zhugs.common.core.util.R;
import com.zhugs.file.application.FileApplicationService;
import com.zhugs.file.application.UploadApplicationService;
import com.zhugs.file.interfaces.dto.UploadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FileUploadController {

    private final UploadApplicationService uploadApplicationService;
    private final FileApplicationService fileApplicationService;

    public FileUploadController(UploadApplicationService uploadApplicationService, FileApplicationService fileApplicationService) {
        this.uploadApplicationService = uploadApplicationService;
        this.fileApplicationService = fileApplicationService;
    }

    /**
     * 创建上传任务
     */
    @PostMapping("/upload")
    public R<Map<String, Object>> upload(@RequestBody UploadRequest uploadRequest) {
        Map<String, Object> map = uploadApplicationService.createUploadTask(uploadRequest);
        return R.ok(map);
    }

    /**
     * 获取上传任务信息
     */
    @GetMapping("/upload/{uploadTaskId}")
    public R<Map<String, Object>> getUploadInfo(@PathVariable Long uploadTaskId) {
        Map<String, Object> resp = uploadApplicationService.getUploadInfo(uploadTaskId);
        return R.ok(resp);
    }

    /**
     * 获取预签名URL
     */
    @PostMapping("/upload/{uploadTaskId}/presign")
    public R<Map<String, Object>> presign(@PathVariable Long uploadTaskId,
                                          @RequestBody(required = false) List<Integer> partNums) {
        Map<String, Object> list = uploadApplicationService.getPresign(uploadTaskId, partNums);
        return R.ok(list);
    }

    /**
     * 完成上传
     */
    @PostMapping("/upload/{uploadTaskId}/complete")
    public R<?> complete(
            @PathVariable Long uploadTaskId,
            @RequestBody(required = false) List<String> partETags) {
        uploadApplicationService.completeUpload(uploadTaskId, partETags);
        return R.ok(null);
    }

    /**
     * 中止上传
     */
    @PostMapping("/upload/{uploadTaskId}/abort")
    public R<?> abort(@PathVariable Long uploadTaskId) {
        uploadApplicationService.abortUpload(uploadTaskId);
        return R.ok(null);
    }

    /**
     * 获取文件
     */
    @GetMapping("/{fileId}")
    public R<Map<String, Object>> getFile(@PathVariable Long fileId) {
        Map<String, Object> resp = fileApplicationService.getFile(fileId);
        return R.ok(resp);
    }

    /**
     * 获取文件url
     */
    @GetMapping("/{fileId}/url")
    public R<String> getFileUrl(@PathVariable Long fileId) {
        String resp = fileApplicationService.getFileUrl(fileId);
        return R.ok(resp);
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/{fileId}")
    public R<?> deleteFile(@PathVariable Long fileId) {
        fileApplicationService.deleteFile(fileId);
        return R.ok(null);
    }

}
