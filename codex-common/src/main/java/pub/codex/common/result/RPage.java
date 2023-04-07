package pub.codex.common.result;


import java.util.List;

/**
 * 返回结果，带参数
 */
public class RPage<T> extends R {

    /**
     * data
     */
    private List<T> data;

    private Long pageNum;

    private Long pageSize;

    private Long totalCount;

    public RPage(List<T> value, Long pageNum, Long pageSize, Long totalCount) {
        this.data = value;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
