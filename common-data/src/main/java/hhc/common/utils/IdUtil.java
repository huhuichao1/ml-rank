package hhc.common.utils;

/**
 * Created by liusenhua on 2017/7/13.
 */
public class IdUtil {
    private static IdWorker idWorker;

    static {
        idWorker = new IdWorker(0);
    }

    public static long nextEntityId() {
        return idWorker.nextId();
    }
}
