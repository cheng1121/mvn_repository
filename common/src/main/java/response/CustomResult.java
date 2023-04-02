package response;

/**
 * @time: 2022/11/17 11:22
 * @author: licheng
 * @desc: 自定义结果
 */
public class CustomResult<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
