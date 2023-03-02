package team.ojt7.recruitment.model.dto;

public class RecruitmentResourceSearch {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    private String keyword;
    private Integer page;
    private Integer size;
    private String entityType;

    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPage() {
        return page == null ? DEFAULT_PAGE : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size == null ? DEFAULT_SIZE : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    

    
}
