/**
 * @author 60417
 * @date 2021/9/10
 * @time 21:13
 * @todo
 */
package YUYEFANHUA.Domain;

import java.io.Serializable;

public class Seller implements Serializable {
    private int sid;//商家id
    private String sname;//商家名称
    private String sphone;//商家电话
    private String address;//商家地址
    /**
     * 无参构造方法
     */
    public Seller(){}

    public Seller(int sid, String sname, String sphone, String address) {
        this.sid = sid;
        this.sname = sname;
        this.sphone = sphone;
        this.address = address;
    }
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
