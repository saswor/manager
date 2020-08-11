package com.manage.project.system.supply.vo;

/**
 * @ClassName WarnLineVo
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 18:00:00$ 2018-10-10$
 **/
public class WarnLineVo {
    private String configId;
    private String lineId;
    private String lineName;
    private String districtName;
    private Integer storyLevel;
    private String wmName;
    private Integer waitSPNum;
    private Integer waitSVNum;
    private String lastSTime;
    /**当前库存占最大百分比*/
    private int storyPercent;

    public int getStoryPercent() {
		return storyPercent;
	}

	public void setStoryPercent(int storyPercent) {
		this.storyPercent = storyPercent;
	}

	public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getStoryLevel() {
        return storyLevel;
    }

    public void setStoryLevel(Integer storyLevel) {
        this.storyLevel = storyLevel;
    }

    public String getWmName() {
        return wmName;
    }

    public void setWmName(String wmName) {
        this.wmName = wmName;
    }

    public Integer getWaitSPNum() {
        return waitSPNum;
    }

    public void setWaitSPNum(Integer waitSPNum) {
        this.waitSPNum = waitSPNum;
    }

    public Integer getWaitSVNum() {
        return waitSVNum;
    }

    public void setWaitSVNum(Integer waitSVNum) {
        this.waitSVNum = waitSVNum;
    }

    public String getLastSTime() {
        return lastSTime;
    }

    public void setLastSTime(String lastSTime) {
        this.lastSTime = lastSTime;
    }
}
