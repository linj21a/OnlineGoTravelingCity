/**
 * @author 60417
 * @date 2021/9/20
 * @time 11:55
 * @todo
 */
package YUYEFANHUA.Domain;

import java.util.List;

/**
 * 分页bean，负责显示对应类型T的数据
 */
public class PageBean<T> {
    private int totalSize;//总的数据条数 需要从数据库里面查
    private int totalPage;///总的页码   =  totalSize%pageSize==0?totalSize/pageSize:totalSize/pageSize+1;
    private int currentPage;//当前页码  客户端提供 没有则默认
    private int pageSize;//当前每一个page显示的条数 自己设定的  客户端提供 没有则默认

    private List<T> list;//总的数据信息，我们每次应该只拉去一部分，不应该一次把数据库的信息都查出来。 需要从数据库里面查
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
