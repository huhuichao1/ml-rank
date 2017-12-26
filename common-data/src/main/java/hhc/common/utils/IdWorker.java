package hhc.common.utils;
/**
 * 来自于twitter项目<a
 * href="https://github.com/twitter/snowflake">snowflake</a>的id产生方案，全局唯一，时间有序
 *
 * from http://bucketli.iteye.com/blog/1057855
 *
 * @see https://github.com/twitter/snowflake
 * @author boyan
 * @Date 2011-4-27
 *
 *
 * •id is composed of: ◦time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years)
◦configured machine id - 10 bits - gives us up to 1024 machines
◦sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms)

 *
 */
public class IdWorker {
    private final long workerId;
    private static final long twepoch = 1496246400000L; //2017-06-01
    private long sequence = 0L;
    private static final long workerIdBits = 10L;
    private static final long maxWorkerId = -1L ^ -1L << workerIdBits;
    private static final long sequenceBits = 12L;

    private static final long workerIdShift = sequenceBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits;
    private static final long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    public IdWorker(long workerId) {
        super();
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }
        this.workerId = workerId;
    }


    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        }
        else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String
                    .format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        return timestamp - twepoch << timestampLeftShift | workerId << workerIdShift
                | this.sequence;
    }


    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }


    private long timeGen() {
        return System.currentTimeMillis();
    }

}