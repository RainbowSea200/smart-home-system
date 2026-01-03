package com.yaosheng.assistant.util;

import cn.hutool.json.JSONUtil;
import lombok.Data;

public class SseNotifyUtil {

    // 构建 SSE 格式消息
    public static String buildSseMessage(String type, String content) {
        SseNotifyMessage notifyMessage = new SseNotifyMessage();
        notifyMessage.setType(type);
        notifyMessage.setContent(content);
        // SSE 消息格式：data: + 消息内容 + \n\n（必须以 \n\n 结尾，前端才能识别）
        return "data: " + JSONUtil.toJsonStr(notifyMessage) + "\n\n";
    }

    // SSE 通知消息实体
    @Data
    public static class SseNotifyMessage {
        // getter 和 setter 方法
        private String type;
        private String content;

    }
}
