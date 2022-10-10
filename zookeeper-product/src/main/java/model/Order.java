package model;

/**
 * @author YuMengMeng
 * @date 2022/10/8
 */
public class Order {
    private String id;
    private Integer pid;
    private Integer userid;

    public Order(String id, Integer pid, Integer userid) {
        this.id = id;
        this.pid = pid;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
