package hhc.common.entity;

/**
 * api接口响应
 */
public enum ExceptionEnum {
    SUCCESS(200,"success"),
    FAILED(500,"failed"),
    PARAMTER_INVALID(4001,"paramter invalid"),
    THRIFT_SERVICE_NOT_EXIST(4002,"thrift service not exist"),
    USER_NOT_EXIST(4003,"用户不存在"),
    RANK_RESULT_PARSE_ERROR(4004,"二次排序结果解析错误"),
    REDIS_MASTER_INIT_ERROR(9000,"redis服务初始化失败"),
    REDIS_MASTER_CONFIG_ERROR(9001,"redis服务master节点配置错误"),
    REDIS_ZK_CONFIG_ERROR(9002,"redis哨兵注册中心配置错误"),
    REDIS_SENTINEL_CONNECT_ERROR(9001,"redis哨兵连接失败"),
    REDIS_OPERATE_ERROR(9003,"redis操作失败"),
    ;

    private int code;
    private String desc;

    ExceptionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
