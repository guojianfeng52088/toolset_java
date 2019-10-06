package mapandlist;

import java.util.List;

/**
 * @Description list工具
 * @Author pc
 * @Date 2019/10/05 16:28
 */
public class ListUtil {

    /**
     * @param list 需要拆分的list对象
     * @param size 需要拆分成几段
     * @Description list拆分
     * @Author guojianfeng
     * @Date 2019/10/05 16:45
     * @Return
     */
    public static List<?>[] splitList(List<?> list, int size) {
        int total = list.size();
        int count = total % size == 0 ? total / size : total / size + 1;
        List<?>[] result = new List[count];
        for (int i = 0; i < count; i++) {
            int start = i * size;
            int end = start + size > total ? total : start + size;
            List<?> subList = list.subList(start, end);
            result[i] = subList;
        }
        return result;
    }
}
