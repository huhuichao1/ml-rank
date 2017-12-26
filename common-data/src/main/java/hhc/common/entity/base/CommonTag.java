package hhc.common.entity.base;

/**
 * Created by syp on 2017/6/30.
 */

public class CommonTag {

    public static Builder create() {
        return new Builder();
    }


    /**
     * tag的ID
     */
    private String id;


    /**
     * tag的内容
     */
    private String content;


    /**
     * tag的类型
     */
    private TagType type;


    /**
     * 置信度
     */
    private double confidence;

    /**
     * 权重
     */
    private double weight;


    /**
     * 周期
     */
    private double cycle;

    /**
     * 起始时间
     */
    private Long updateTime;


    private CommonTag(Builder builder) {
        id = builder.id;
        content = builder.content;
        type = builder.type;
        confidence = builder.confidence;
        weight = builder.weight;
        cycle = builder.cycle;
        updateTime = builder.updateTime;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCycle() {
        return cycle;
    }

    public void setCycle(double cycle) {
        this.cycle = cycle;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public static class Builder {
        public CommonTag build() {
            return new CommonTag(this);
        }

        /**
         * tag的ID
         */
        private String id;


        /**
         * tag的内容
         */
        private String content;


        /**
         * tag的类型
         */
        private TagType type;


        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setType(TagType type) {
            this.type = type;
            return this;
        }

        public Builder setConfidence(double confidence) {
            this.confidence = confidence;
            return this;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setCycle(double cycle) {
            this.cycle = cycle;
            return this;
        }

        /**
         * 置信度
         */

        private double confidence;

        /**
         * 权重
         */
        private double weight;


        /**
         * 周期
         */
        private double cycle;

        public Builder setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        private Long updateTime;


    }
}
