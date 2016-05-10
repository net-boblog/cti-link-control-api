package com.tinet.ctilink.control.entity;

/**
 * @author fengwei //
 * @date 16/4/25 16:07
 */
public class Action {
    public static final String LOGIN = "login"; // 登录
    public static final String LOGOUT = "logout"; // 登出
    public static final String PAUSE = "pause"; // 置忙
    public static final String UNPAUSE = "unpause"; // 置闲
    public static final String SET_PAUSE = "setPause"; // 监控置忙
    public static final String SET_UNPAUSE = "setUnpause"; // 监控置闲
    public static final String REFUSE = "refuse"; // 拒绝
    public static final String LINK = "link"; // 接听
    public static final String UNLINK = "unlink"; // 挂断或者取消呼叫
    public static final String HOLD = "hold"; // 保持
    public static final String UNHOLD = "unhold"; // 保持挂断
    public static final String CONSULT = "consult"; // 咨询 长动作
    public static final String CONSULT_CANCEL = "consultCancel";//咨询取消
    public static final String UNCONSULT = "unconsult"; // 咨询挂断
    public static final String CONSULT_TRANSFER = "consultTransfer"; // 咨询转接
    public static final String CONSULT_THREEWAY = "consultThreeway"; // 咨询三方
    public static final String TRANSFER = "transfer"; // 转移 长动作
    public static final String INVESTIGATION = "investigation"; // 满意度调查
    public static final String SPY = "spy"; // 监听 长动作
    public static final String UNSPY = "unspy"; // 监听挂断
    public static final String WHISPER = "whisper"; // 耳语 长动作
    public static final String UNWHISPER = "unwhisper"; // 耳语挂断
    public static final String THREEWAY = "threeway";
    public static final String UNTHREEWAY = "unthreeway";
    public static final String BARGE = "barge"; // 强插 长动作
    public static final String UNINSERT = "uninsert"; // 强插挂断
    public static final String DISCONNECT = "disconnect"; // 前拆
    public static final String GET_STATUS = "getStatus"; // 获取自己状态
    public static final String PING = "ping"; // 心跳
    public static final String NWAY_INIT = "threewayInit"; // 会议邀请
    public static final String NWAY_OK = "threewayOk"; // 会议进入
    public static final String PREVIEW_OUTCALL = "previewOutCall"; // 点击外呼
    public static final String PREVIEW_OUTCALL_CANCEL = "previewOutcallCancel"; // 点击取消
    public static final String QUEUE_STATUS = "queueStatus"; // 获取队列状态
    public static final String QUEUE_SHOW = "queueShow"; // 刷新队列状态
    public static final String CHANGE_BIND_TEL = "changeBindTel"; // 修改
    public static final String PICKUP = "pickup"; // 抢线
    public static final String CHANNEL_HANGUP = "channelHangup"; // 抢线
    public static final String CALL_LOCAL = "callLocal";
    public static final String IVR_CALLBACK = "ivrCallback";
    /** 聊天系统--web_chat--Action */
    public static final String WEBCHAT = "webchat";
    public static final String INTERACT = "interact";
    /** 坐席Debug信息采集--Action */
    public static final String MUTE = "mute";
    public static final String DIRECT_CALL_START = "directCallStart";
    public static final String WEBCALL = "webcall";
    public static final String SELF_RECORD = "selfRecord"; // 咨询 长动作

}
